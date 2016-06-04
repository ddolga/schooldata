package com.nadia.data;

import com.nadia.data.processors.AbstractProcessor;
import com.nadia.data.util.Parameters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class MainApp {

    static public void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(MainApp.class,args);

        Parameters params = new Parameters(args);
        String importerType = params.getImporterType();
        AbstractProcessor processor = (AbstractProcessor) ctx.getBean(importerType);
        processor.doYourThing(new FileIterator(processor),   params);
    }
}


