package com.snippets.csvcompare;

import java.util.Map;

public interface CSVRowMapper {

    RowElement map(String line);

}
