package com.snippets.csvcompare;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.ConcurrentMap;

@SpringBootApplication
public class CsvCompareApplication {

	@Value("app.input.old.file")
	private String oldFilename;

	@Value("app.input.new.file")
	private String newFilename;

	private FileToMapConverter fileToMapConverter ;
	private IdentifierFetcher identifierFetcher;

	public static void main(String[] args) throws IOException {
		CsvCompareApplication app = new CsvCompareApplication();
		app.compareFiles();
//		SpringApplication.run(CsvCompareApplication.class, args);
	}

	private void compareFiles() throws IOException {

		ConcurrentMap<String, RowElement> dataFromOldFile = fileToMapConverter.getFromFile(oldFilename);
		ConcurrentMap<String, RowElement> dataFromNewFile = fileToMapConverter.getFromFile(newFilename);

	}

	private ConcurrentMap<String, RowElement> getFromFile(String filename) throws IOException {
		return fileToMapConverter.getFromFile(filename);
	}


}
