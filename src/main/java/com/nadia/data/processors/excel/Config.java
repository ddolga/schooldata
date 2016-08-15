package com.nadia.data.processors.excel;

import com.nadia.data.FileIterator;
import com.nadia.data.api.IFileIterator;
import com.nadia.data.translators.Transliterator;
import com.nadia.data.translators.TransliteratorForCombine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public IFileIterator fileIterator() {
        return new FileIterator();
    }

    @Bean
    public Transliterator transliterator() {
        return new Transliterator();
    }

    @Bean
    public TransliteratorForCombine transliteratorForCombine() {
        return new TransliteratorForCombine();
    }

    @Bean
    public CombineToCSV combineToCSV() {
        return new CombineToCSV(fileIterator(), transliteratorForCombine());
    }

    @Bean
    public ProcessAllCells processAllCells() {
        return new ProcessAllCells(fileIterator(), transliterator());
    }

    @Bean
    public CleanAvailableLabor cleanAvailableLabor() {
        return new CleanAvailableLabor(fileIterator());
    }

    @Bean
    public SAXProcessor saxProcessor() {
        return new SAXProcessor(fileIterator());
    }
}
