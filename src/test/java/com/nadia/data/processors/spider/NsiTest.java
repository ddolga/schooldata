package com.nadia.data.processors.spider;

import org.junit.Test;
import org.mockito.internal.exceptions.ExceptionIncludingMockitoWarnings;

import java.io.File;

import static org.junit.Assert.*;

public class NsiTest {


    @Test
    public void processRegions() throws Exception {
        Nsi nsi = new Nsi();
        Node node = new Node("BLG", "Bulgaria", "http://www.nsi.bg/nrnm/index.php?ezik=en&f=3&date=09.06.2016&hierarchy=1");
        nsi.regionProcessor.processRow(node);
        assertEquals(28, node.getAllChildren().size());
    }

    @Test
    public void processMunies() throws Exception {
        Nsi nsi = new Nsi();
        Node node = new Node("BGS", "Burgas", "http://www.nsi.bg/nrnm/index.php?ezik=en&f=3&date=11.06.2016&level=2&hierarchy=1&sap=1000003");
        nsi.muniProcessor.processRow(node);
        assertEquals(13, node.getAllChildren().size());
    }


    @Test
    public void processCities() throws Exception {
        Nsi nsi = new Nsi();
        Node node = new Node("BLG13", "Kresna", "http://www.nsi.bg/nrnm/index.php?ezik=en&f=3&date=09.06.2016&level=3&hierarchy=1&sap=1000035");
        nsi.townProcessor.processRow(node);
        assertEquals(7, node.getAllChildren().size());
    }


    private Node buildFakeNodeTree() {

        Node root = new Node("Root", "Root Node", null);
        for (int i = 0; i < 10; i++) {

            Node muni = new Node("Child_" + i, "Child Node - " + i, null);
            root.addChild(muni);

            for (int j = 0; j < 5; j++) {
                Node town = new Node("GrandChild_" + j, "Grand Child Node -  " + j, null);
                muni.addChild(town);
            }
        }
        return root;
    }


    @Test
    public void listAll() throws Exception {
        Node root = buildFakeNodeTree();
        Nsi nsi = new Nsi();
        nsi.listAll(root);
    }


    @Test
    public void writeTheWholeShebangToFile() throws Exception {

        final String fileName = "AllTheYoungDudes.csv";

        Nsi nsi = new Nsi();
        Node root = buildFakeNodeTree();
        nsi.writeTheWoleShebangToFile(fileName, root);

        File file = new File(fileName);
        assertTrue(file.exists());
    }

}