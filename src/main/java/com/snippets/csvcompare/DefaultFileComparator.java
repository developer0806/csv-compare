package com.snippets.csvcompare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

@Component
public class DefaultFileComparator implements FileComparator {

    @Value("app.input.old.file")
    private String oldFilename;

    @Value("app.input.new.file")
    private String newFilename;

    private FileToMapConverter fileToMapConverter ;
    private IdentifierFetcher identifierFetcher;

    @Autowired
    public DefaultFileComparator(FileToMapConverter fileToMapConverter, IdentifierFetcher identifierFetcher) {
        this.fileToMapConverter = fileToMapConverter;
        this.identifierFetcher = identifierFetcher;
    }

    @Override
    public List<ComparisonResult> compare() {
        ConcurrentMap<String, RowElement> dataFromOldFile = fileToMapConverter.convert(oldFilename);
        ConcurrentMap<String, RowElement> dataFromNewFile = fileToMapConverter.convert(newFilename);


        return null;
    }
}
