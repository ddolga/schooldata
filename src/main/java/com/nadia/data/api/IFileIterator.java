package com.nadia.data.api;

import java.io.FileNotFoundException;

public interface IFileIterator {

    void iterateOverFiles(String[] fs,IFileProcessor fileProcessor) throws FileNotFoundException;
}
