package com.nadia.data;

import java.io.File;

public abstract class MainApp {

    protected int type;
    protected int limit;

    public MainApp(Parameters params, int limit) {
        this.type = params.getType();
        this.limit = limit;
    }


    protected void iterateOverFiles(String[] fs) {

        File[] fa = new File[fs.length];
        for (int i = 0; i < fs.length; i++) {
            fa[i] = new File(fs[i]);
        }

        iterateOverFiles(fa);
    }

    protected void iterateOverFiles(File[] fa) {
        _iterateOverFiles(fa);
        cleanUp();
    }


    private void _iterateOverFiles(File[] fa) {
        for (File file : fa) {
            if(!file.getName().startsWith(".")){
                if (file.isDirectory()) {
                    _iterateOverFiles(file.listFiles());
                } else {
                    processFile(file.getAbsolutePath());
                }
            }
        }
    }

    protected abstract void processFile(String path);

    protected abstract void cleanUp();

}


