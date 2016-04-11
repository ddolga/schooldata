package com.nadia.data.processors;

import com.nadia.data.Parameters;
import com.nadia.data.api.FormProcessInterface;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class ConcatenateFiles implements FormProcessInterface {

    WritableByteChannel outputChannel;

    public ConcatenateFiles(Parameters params) {
        setupOutputChannel(params.getTargetFileName());
    }

    private void setupOutputChannel(String outFileName) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(outFileName));
            outputChannel = fos.getChannel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(String inFileName, int limit) {

        try {
            FileInputStream fis = new FileInputStream(new File(inFileName));
            FileChannel inputChannel = fis.getChannel();
            inputChannel.transferTo(0, inputChannel.size(), outputChannel);
            inputChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void cleanUp() {

    }
}
