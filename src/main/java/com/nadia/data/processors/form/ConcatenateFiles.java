package com.nadia.data.processors.form;

import com.nadia.data.processors.AbstractProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

@Component
public class ConcatenateFiles extends AbstractProcessor {

    Logger logger = LoggerFactory.getLogger(ConcatenateFiles.class);


    WritableByteChannel outputChannel;
    File targetFile = null;


    @Override
    public void setup() throws FileNotFoundException {
        targetFile = new File(params.getTargetFileName());
        setupOutputChannel(targetFile);
        writeHeader(params.getHeader());
    }


    private void writeHeader(String header) {
        ByteBuffer bb = ByteBuffer.wrap((header + "\r").getBytes());
        try {
            outputChannel.write(bb);
        } catch (IOException e) {
            logger.error("Could not write the file header", e.getStackTrace());
        }
    }

    private void setupOutputChannel(File targetFile) {
        try {
            if (targetFile.exists()) {
                targetFile.delete();
            }

            FileOutputStream fos = new FileOutputStream(targetFile);
            outputChannel = fos.getChannel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void process(String inFileName){

        try {
            //don't process the output file iteself
            if (targetFile.getAbsolutePath().equals(new File(inFileName).getAbsolutePath()))
                return;

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
