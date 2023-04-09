package com.snippets.csvcompare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
public class DefaultFileToMapConverter implements FileToMapConverter {
    private final CSVRowMapper csvRowMapper;

    @Autowired
    public DefaultFileToMapConverter(CSVRowMapper csvRowMapper) {
        this.csvRowMapper = csvRowMapper;
    }

    @Override
    public ConcurrentMap<String, RowElement> convert(String filename) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filename))) {
            return bufferedReader.lines().parallel()
                    .map(csvRowMapper::map)
                    .collect(Collectors.toConcurrentMap(RowElement::getIdentifier, rowElement -> rowElement));
        } catch (IOException e) {
            throw new IllegalStateException("Error reading file [" + filename + "]", e);
        }
    }
}