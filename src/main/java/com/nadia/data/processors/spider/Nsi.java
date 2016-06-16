package com.nadia.data.processors.spider;

import com.nadia.data.api.IProcessor;
import com.nadia.data.util.Parameters;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Nsi implements IProcessor {


    private final Logger logger = LoggerFactory.getLogger(Nsi.class);

//    private final static String NSI_URL = "http://www.nsi.bg/nrnm/index.php?ezik=en&f=3&date=09.06.2016&hierarchy=1";
    private final static String NSI_URL = "http://www.nsi.bg/nrnm/index.php?ezik=en&f=3&date=09.06.2006&hierarchy=1";
    public final static String DOCUMENT_PATH = "body > form > table > tbody > tr:nth-child(3) > td:nth-child(2) > table:nth-child(5) > tbody > tr";

    @FunctionalInterface
    interface IProcessRow {
        void processRow(Node node) throws IOException;
    }


    private NameCleaner townCleaner = new NameCleaner("(.+(?= village)|(?<=town of )[\\w\\s]+)");
    private NameCleaner muniCleaner = new NameCleaner("(.+(?= municipality))");
    private NameCleaner regionCleaner = new NameCleaner("(.+(?= district))");


    IProcessRow regionProcessor = (Node node) -> {
        processRows(node, DOCUMENT_PATH, 2, 4, regionCleaner);
    };

    IProcessRow muniProcessor = (Node node) -> {
        processRows(node, DOCUMENT_PATH, 2, 4, muniCleaner);
    };

    IProcessRow townProcessor = (Node node) -> {
        processRows(node, DOCUMENT_PATH, 2, 3, townCleaner);
    };


    @Override
    public void doYaThing(Parameters params) {


        try {
            String fileName = params.getTargetFileName();


            Node root = new Node("root", "Bulgaria", NSI_URL);
            logger.info("Retrieving list of regions...");
            regionProcessor.processRow(root);

            logger.info("Retrieving all municipalities");
            for (Node regions : root.getAllChildren()) {
                muniProcessor.processRow(regions);
            }

            logger.info("Retrieving all cities");
            for (Node regions : root.getAllChildren()) {
                for (Node municipalities : regions.getAllChildren()) {
                    townProcessor.processRow(municipalities);
                }
            }

//            listAll(root);
            writeTheWoleShebangToFile(params.getTargetFileName(), root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected void processRows(Node node, String selector, int codeIdx, int linkIdx, NameCleaner cleaner) throws IOException {
        Document document = Jsoup.connect(node.getUrl()).get();
        Elements rows = document.select(selector);
        for (int i = 2; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements children = row.getAllElements();
            String code = children.get(codeIdx).text();
            Elements links = children.get(linkIdx).getElementsByTag("a");
            Node child;
            String name;
            if (links.size() > 0) {
                Element link = links.get(0);
                String href = link.attr("abs:href");
                name = cleaner.cleanName(link.text());
                child = new Node(code, name, href);
            } else {
                name = cleaner.cleanName(children.get(linkIdx).text());
                child = new Node(code, name, null);
            }
            node.addChild(child);
            logger.info("Code:" + code + ", Name:" + name);
        }
    }


    protected void writeTheWoleShebangToFile(String fileName, Node root) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        _writeTheWoleShebangToFile(bw, new ArrayList<>(), root);
        bw.close();
    }

    private void _writeTheWoleShebangToFile(BufferedWriter bw, List<String> list, Node node) throws IOException {
        List<String> ll = new ArrayList<>();
        ll.addAll(list);

        ll.add(node.getCode());
        ll.add(node.getName());

        if (node.getAllChildren().size() > 0) {
            for (Node child : node.getAllChildren()) {
                _writeTheWoleShebangToFile(bw, ll, child);
            }
        } else {
            writeLineToFile(bw, ll);
        }
    }

    private void writeLineToFile(BufferedWriter bw, List<String> list) throws IOException {
        String line = String.join(",", list);
        bw.write(line);
        bw.newLine();
    }


    protected void listAll(Node node) {
        logger.debug(node.toString());
        for (Node node1 : node.getAllChildren()) {
            listAll(node1);
        }
    }

}


class Node {
    private String code;
    private String name;
    private String url;

    private List<Node> children;

    public Node(String code, String name, String url) {
        this.code = code;
        this.name = name;
        this.url = url;
        children = new ArrayList<>();
    }

    public String getUrl() {
        return url;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void addChild(Node node) {
        this.children.add(node);
    }

    public List<Node> getAllChildren() {
        return children;
    }

    @Override
    public String toString() {
        return code + " : " + name;
    }
}


class NameCleaner {

    private Pattern pattern;

    public NameCleaner(String regEx) {
        this.pattern = Pattern.compile(regEx);
    }

    public String cleanName(String name) {

        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return name;
    }

}
