package com.nadia.data;

import com.nadia.data.api.IFileProcessor;
import com.nadia.data.api.IFileIterator;

import java.io.File;
import java.io.FileNotFoundException;

public class FileIterator implements IFileIterator {


    @Override
    public void iterateOverFiles(String[] fs, IFileProcessor fileProcessor) throws FileNotFoundException {

        File[] fa = new File[fs.length];
        for (int i = 0; i < fs.length; i++) {
            fa[i] = new File(fs[i]);
        }

        iterateOverFiles(fa,fileProcessor);
    }

    protected void iterateOverFiles(File[] fa, IFileProcessor fileProcessor) throws FileNotFoundException {
        fileProcessor.setup();
        _iterateOverFiles(fa, fileProcessor);
        fileProcessor.cleanUp();
    }


    private void _iterateOverFiles(File[] fa, IFileProcessor fileProcessor) {
        for (File file : fa) {
            if (!file.getName().startsWith(".")) {
                if (file.isDirectory()) {
                    _iterateOverFiles(file.listFiles(), fileProcessor);
                } else {
                    fileProcessor.process(file.getAbsolutePath());

                }
            }
        }
    }

}
