package com.snippets.csvcompare;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static com.snippets.csvcompare.CSVRowMapperImpl.COMMA;

@Component
public class ComparisonOrchestrator {


    public static final String KEY_DELIMITER = ":";
    public static final String LINE_BREAK = "\n";
    @Value("app.input.old.file")
    private String oldFilename;

    @Value("app.input.new.file")
    private String newFilename;
    @Value("app.output.file")
    private String outputFileName;

    private final FileComparator comparator;
    private final FileToMapConverter fileToMapConverter;

    @Autowired
    public ComparisonOrchestrator(FileComparator comparator, FileToMapConverter fileToMapConverter) {
        this.comparator = comparator;
        this.fileToMapConverter = fileToMapConverter;
    }

    public void start() {
        outputFileName+= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmss"));
        AtomicLong counter = new AtomicLong(0);
        comparator.compare(
                        fileToMapConverter.convert(oldFilename),
                        fileToMapConverter.convert(newFilename))
                .filter(RowComparisonResult::hasNoDifference)
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / 1000))
                .values().stream().map(this::transformResult)
                .flatMap(strings -> strings.parallelStream().map(String::getBytes))
                .forEach(this::writeToOutputFile);
    }

    private void writeToOutputFile(byte[] bytes) {
        try {
            Files.write(new File(outputFileName).toPath(),bytes);
        } catch (IOException e) {
            throw new IllegalStateException("Error while writing file [" + outputFileName + "]", e);
        }
    }

    private List<String> transformResult(List<RowComparisonResult> rowComparisonResults) {
        return rowComparisonResults.parallelStream()
                .map(rowComparisonResult -> rowComparisonResult.getKey() + COMMA
                        + rowComparisonResult.getCellComparisonResults().parallelStream()
                        .filter(CellComparisonResult::isNotDifferent)
                        .map(comparisonResult -> comparisonResult.getKey() + KEY_DELIMITER
                                + "OldValue: " + comparisonResult.getOldValue()
                                + " NewValue: " + comparisonResult.getNewValue() + LINE_BREAK))
                .collect(Collectors.toList());

    }

}
