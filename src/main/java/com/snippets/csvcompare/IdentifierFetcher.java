package com.snippets.csvcompare;

import java.util.Map;

public interface IdentifierFetcher {

    String fetch(Map<String, String> fieldMap);
}

