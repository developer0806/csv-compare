package com.snippets.csvcompare;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RowComparisonRequest {
    public static final String KEY_OLD = "OLD";
    public static final String KEY_NEW = "NEW";
    private String key;
    private RowElement oldData;
    private RowElement newData;

}
