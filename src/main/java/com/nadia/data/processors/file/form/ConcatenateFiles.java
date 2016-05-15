package com.nadia.data.processors.file.form;

import com.nadia.data.processors.util.Parameters;
import com.nadia.data.api.FormProcessInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class ConcatenateFiles implements FormProcessInterface {

    Logger logger = LoggerFactory.getLogger(ConcatenateFiles.class);


    WritableByteChannel outputChannel;
    File targetFile = null;

    public ConcatenateFiles(Parameters params) {
        targetFile = new File(params.getTargetFileName());
        setupOutputChannel(targetFile);
        writeHeader(params.getHeader());
    }

    private void writeHeader(String header){
        ByteBuffer bb = ByteBuffer.wrap((header + "\r").getBytes());
        try {
            outputChannel.write(bb);
        } catch (IOException e) {
            logger.error("Could not write the file header",e.getStackTrace());
        }
    }

    private void setupOutputChannel(File targetFile) {
        try {
            if(targetFile.exists()){
                targetFile.delete();
            }


            FileOutputStream fos = new FileOutputStream(targetFile);
            outputChannel = fos.getChannel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(String inFileName, int limit) {

        try {
            //don't process the output file iteself
            if(targetFile.getAbsolutePath().equals(new File(inFileName).getAbsolutePath()))
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
