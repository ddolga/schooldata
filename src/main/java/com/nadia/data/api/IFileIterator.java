package com.nadia.data.api;

import java.io.FileNotFoundException;

/**
 * Created by Denis on 5/22/16.
 */
public interface IFileIterator {
    void iterateOverFiles(String[] fs) throws FileNotFoundException;
}
