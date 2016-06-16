package com.nadia.data.processors.excel;

import com.nadia.data.FileIterator;
import com.nadia.data.api.IFileIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CompareCityName extends Cellabrate {

    Logger logger = LoggerFactory.getLogger(CompareCityName.class);

    @Autowired
    public CompareCityName(IFileIterator fileIterator) {
        super(new FileIterator());
    }


    interface ITransformForCompare {
        String transform(String str);
    }

    abstract class TransformForCompare implements ITransformForCompare {

        protected Pattern pattern;

        public TransformForCompare(String regEx) {
            this.pattern = Pattern.compile(regEx);
        }

    }


    private static final String HYPEHN_SEARCH_STR = "[-'\\s]+";
    //    private static final String DASH_SEARCH_STR = "(-+)";
    private static final String GH_SEARCH_STR = "gh";


    private ITransformForCompare removeHyphen = new TransformForCompare(HYPEHN_SEARCH_STR) {
        @Override
        public String transform(String str) {
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                return matcher.replaceAll("");
            }

            return str;
        }
    };

//    private ITransformForCompare removeDash = new TransformForCompare(DASH_SEARCH_STR) {
//        @Override
//        public String transform(String str) {
//            Matcher matcher = pattern.matcher(str);
//            if (matcher.find()) {
//                return matcher.replaceAll(" ");
//            }
//
//            return str;
//        }
//    };


    private ITransformForCompare removeGh = new TransformForCompare(GH_SEARCH_STR) {
        @Override
        public String transform(String str) {
            Matcher matcher = pattern.matcher(str);
            if (matcher.find()) {
                return matcher.replaceAll("g");
            }

            return str;
        }
    };


    private boolean isGhDifferent(String a, String b) {

        String strLong = a.length() > b.length() ? a : b;
        String strShort = a.length() > b.length() ? b : a;

        strLong = removeGh.transform(strLong);
        return strLong.equals(strShort);
    }


    @Override
    public void process(String inFileName) {

        final int DISTANCE_IDX = 6;

        process(inFileName, "distance",
                (sheet, row) -> {
                    String s1 = row.getCell(1).getStringCellValue();
                    String s2 = row.getCell(3).getStringCellValue();

//                    String c1 = removeHyphen.transform(removeDash.transform(s1));
//                    String c2 = removeHyphen.transform(removeDash.transform(s2));
                    String c1 = removeHyphen.transform(s1.toLowerCase());
                    String c2 = removeHyphen.transform(s2.toLowerCase());

                    double distance = c1.equals(c2) ? 1 : isGhDifferent(c1, c2) ? 1 : StringUtils.getJaroWinklerDistance(c1, c2);

                    Cell cellDistance = row.createCell(DISTANCE_IDX);
                    cellDistance.setCellValue(distance);

                    Cell cellToDelete = row.createCell(DISTANCE_IDX + 1);
                    if (distance == 1) {
                        cellToDelete.setCellValue("delete");
                    }

                    if (distance == 1 && !s1.equalsIgnoreCase(s2)) {
                        Cell cellCorrection = row.createCell(DISTANCE_IDX + 2);
                        cellCorrection.setCellValue(s1.length() > s2.length() ? s2 : s1);
                    }

//                    logger.info(c1 + " --> " + c2 + "= " + distance);
                }
        );
    }

    @Override
    public void setup() throws FileNotFoundException {

    }


    @Override
    public void cleanUp() {

    }
}


