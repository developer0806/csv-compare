package com.snippets.csvcompare;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class FileToMapConverter {
    private CSVRowMapper csvRowMapper;


    public ConcurrentMap<String, RowElement> getFromFile(String filename) throws IOException {
        return Files.lines(Paths.get(filename)).parallel()
                .map(line -> csvRowMapper.map(line))
                .collect(Collectors.toConcurrentMap(RowElement::getIdentifier, rowElement -> rowElement));
    }
}