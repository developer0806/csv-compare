package com.snippets.csvcompare;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentMap;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class DefaultFileToMapConverterTest {

    public static final String LINE_01 = "Row1Column1,Row1Column2,Row1Column3";
    public static final String LINE_02 = "Row2Column1,Row2Column2,Row2Column3";
    public static final String LINE_03 = "Row3Column1,Row3Column2,Row3Column3";

    private DefaultFileToMapConverter converter;

    @Mock
    ColumnPositionToNameConverter columnPositionToNameConverter;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        converter = new DefaultFileToMapConverter(new CSVRowMapperImpl(columnPositionToNameConverter));
    }
    @Test
    public void convert_whenFileHasThreeColumnsThreeRows_thenReturnCorrespondingMap() throws IOException {
        Path tempFile = Files.createTempFile(Paths.get("."),"prefix", "old-suffix");
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            writeLine(writer, LINE_01);
//            writeLine(writer, LINE_02);
//            writeLine(writer, LINE_03);
        }
        when(columnPositionToNameConverter.convert(anyInt()))
                .thenReturn("Str11").thenReturn("Str12").thenReturn("Str13")
                .thenReturn("Str21").thenReturn("Str22").thenReturn("Str33")
                .thenReturn("Str31").thenReturn("Str32").thenReturn("Str33");
        ConcurrentMap<String, RowElement> result = converter.convert(tempFile.getFileName().toString());

        System.out.println(result);
    }

    private static void writeLine(BufferedWriter writer, String line01) throws IOException {
        writer.write(line01);
        writer.newLine();
    }

}