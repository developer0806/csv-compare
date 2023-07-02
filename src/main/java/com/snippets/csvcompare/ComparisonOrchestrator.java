package com.snippets.csvcompare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.snippets.csvcompare.CSVRowMapperImpl.COMMA;

@Component
public class ComparisonOrchestrator {


    public static final String KEY_DELIMITER = ":";
    @Value("app.input.old.file")
    private String oldFilename;

    @Value("app.input.new.file")
    private String newFilename;

    private final FileComparator comparator;
    private final FileToMapConverter fileToMapConverter;

    @Autowired
    public ComparisonOrchestrator(FileComparator comparator, FileToMapConverter fileToMapConverter) {
        this.comparator = comparator;
        this.fileToMapConverter = fileToMapConverter;
    }

    public void start() {
        AtomicLong counter = new AtomicLong(0);
        comparator.compare(
                        fileToMapConverter.convert(oldFilename),
                        fileToMapConverter.convert(newFilename))
                .filter(RowComparisonResult::hasNoDifference)
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / 1000))
                .values().parallelStream().map(this::transformResult).collect(Collectors.);

    }

    private List<String> transformResult(List<RowComparisonResult> rowComparisonResults) {
        return rowComparisonResults.parallelStream()
                .map(rowComparisonResult -> rowComparisonResult.getKey() + COMMA
                        + rowComparisonResult.getCellComparisonResults().parallelStream()
                        .filter(CellComparisonResult::isNotDifferent)
                        .map(comparisonResult -> comparisonResult.getKey() + KEY_DELIMITER
                                + "OldValue: " + comparisonResult.getOldValue()
                                + " NewValue: " + comparisonResult.getNewValue()))
                .collect(Collectors.toList());

    }

}
