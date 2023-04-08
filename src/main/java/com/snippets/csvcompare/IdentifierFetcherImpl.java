package com.snippets.csvcompare;

import java.util.Map;

public class IdentifierFetcherImpl implements IdentifierFetcher {
    @Override
    public String fetch(Map<String, String> fieldMap) {
        return fieldMap.get("Column1");
    }
}
