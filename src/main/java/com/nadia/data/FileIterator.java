package com.nadia.data;

import com.nadia.data.api.IProcessFile;
import com.nadia.data.api.IFileIterator;

import java.io.File;
import java.io.FileNotFoundException;

public class FileIterator implements IFileIterator {


    IProcessFile processFile;

    public FileIterator(IProcessFile processFile) {
        this.processFile = processFile;
    }


    @Override
    public void iterateOverFiles(String[] fs) throws FileNotFoundException {

        File[] fa = new File[fs.length];
        for (int i = 0; i < fs.length; i++) {
            fa[i] = new File(fs[i]);
        }

        iterateOverFiles(fa);
    }

    protected void iterateOverFiles(File[] fa) throws FileNotFoundException {
        processFile.setup();
        _iterateOverFiles(fa);
        processFile.cleanUp();
    }


    private void _iterateOverFiles(File[] fa) {
        for (File file : fa) {
            if (!file.getName().startsWith(".")) {
                if (file.isDirectory()) {
                    _iterateOverFiles(file.listFiles());
                } else {
                    processFile.process(file.getAbsolutePath());

                }
            }
        }
    }

}
