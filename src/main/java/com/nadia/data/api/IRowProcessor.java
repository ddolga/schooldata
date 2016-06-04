package com.nadia.data.api;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

@FunctionalInterface
public interface IRowProcessor {
    void doRow(Sheet sheet, Row row);
}
