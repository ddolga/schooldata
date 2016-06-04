package com.nadia.data.processors.excel;

import com.nadia.data.translators.Transliterator;
import com.nadia.data.translators.TransliteratorForCombine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {


    @Bean
    Transliterator transliterator(){
        return new Transliterator();
    }

    @Bean
    TransliteratorForCombine transliteratorForCombine(){
        return new TransliteratorForCombine();
    }

    @Bean
    CombineToCSV combineToCSV(){
        return new CombineToCSV(transliteratorForCombine());
    }

    @Bean
    ProcessAllCells processAllCells(){
        return new ProcessAllCells(transliterator());
    }
}
