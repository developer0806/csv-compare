package com.snippets.csvcompare;

import java.util.concurrent.ConcurrentMap;

public interface FileToMapConverter {
    ConcurrentMap<String, RowElement> convert(String filename);
}
