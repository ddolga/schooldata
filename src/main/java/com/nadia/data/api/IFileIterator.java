package com.nadia.data.api;

import java.io.FileNotFoundException;

public interface IFileIterator {

    void iterateOverFiles(String[] fs) throws FileNotFoundException;

    void setProcessFile(IProcessFile processFile);

}
