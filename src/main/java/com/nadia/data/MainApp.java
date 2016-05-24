package com.nadia.data;

import com.nadia.data.util.Parameters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public abstract class MainApp {



    static public void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(MainApp.class,args);
        Launcher launcher = ctx.getBean(Launcher.class);
        launcher.importData();
    }


    @Bean
    public Parameters getParameters() {
        return new Parameters();
    }

}


