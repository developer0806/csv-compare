package com.snippets.csvcompare;

import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class IdentifierFetcherImpl implements IdentifierFetcher {
    @Override
    public String fetch(Map<String, String> fieldMap) {
        return fieldMap.get("Column1");
    }
}
