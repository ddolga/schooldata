package com.nadia.data;

import com.nadia.data.api.ICleanUp;
import com.nadia.data.api.IProcessFile;
import com.nadia.data.api.ImporterInterface;
import com.nadia.data.api.ParametersInterface;

import java.io.File;

public class AbstractImporter implements ImporterInterface {


    IProcessFile processFile;
    ICleanUp cleanUp;

    public AbstractImporter(IProcessFile processFile, ICleanUp cleanUp) {
        this.processFile = processFile;
        this.cleanUp = cleanUp;
    }


    @Override
    public void iterateOverFiles(String[] fs) {

        File[] fa = new File[fs.length];
        for (int i = 0; i < fs.length; i++) {
            fa[i] = new File(fs[i]);
        }

        iterateOverFiles(fa);
    }

    protected void iterateOverFiles(File[] fa) {
        _iterateOverFiles(fa);
        cleanUp.clean();
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
