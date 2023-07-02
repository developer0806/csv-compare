package com.snippets.csvcompare;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class RowElement {

    private final ImmutableMap<String, String> map;

    public RowElement(Map<String, String> map) {
        this.map = ImmutableMap.copyOf(map);
    }

    String getIdentifier() {
        return map.get("Column1")+"_" + map.get("Column2");
    }

    public ImmutableMap<String, String> getMap() {
        return map;
    }
}
