package com.snippets.csvcompare;

import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

public interface FileComparator {

    Stream<RowComparisonResult> compare(ConcurrentMap<String, RowElement> convert, ConcurrentMap<String, RowElement> converted);

}
