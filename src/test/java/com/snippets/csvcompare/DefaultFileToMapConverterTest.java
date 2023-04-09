package com.snippets.csvcompare;

import com.google.common.collect.Maps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentMap;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class DefaultFileToMapConverterTest {

    public static final String LINE_01 = "Row1Column1,Row1Column2,Row1Column3";
    public static final String LINE_02 = "Row2Column1,Row2Column2,Row2Column3";
    public static final String LINE_03 = "Row3Column1,Row3Column2,Row3Column3";
    @InjectMocks
    private FileToMapConverter converter;
    @Mock
    private CSVRowMapper csvRowMapper;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void convert_whenFileHasThreeColumnsThreeRows_thenReturnCorrespondingMap() throws IOException {
        Path tempFile = Files.createTempFile("prefix", "old-suffix");
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            writer.write(LINE_01); writer.newLine();
            writer.write(LINE_02); writer.newLine();
            writer.write(LINE_03); writer.newLine();
        }

        when(csvRowMapper.map(anyString())).thenReturn(new RowElement(Maps.newHashMap()));

        ConcurrentMap<String, RowElement> result = converter.convert(tempFile.getFileName().toString());

        verify(csvRowMapper, times(1)).map(LINE_01);
        verify(csvRowMapper, times(1)).map(LINE_02);
        verify(csvRowMapper, times(1)).map(LINE_03);

    }

}