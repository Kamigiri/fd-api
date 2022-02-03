package com.keraisoft.fd.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FdCsvReader {
    private static final Character COMMA_DELIMITER = ',';

    public List<List<String>> getRecords() throws IOException {
        this.readFile();
        return records;
    }

    public void setRecords(List<List<String>> records) {
        this.records = records;
    }

    List<List<String>> records = new ArrayList<>();

    private void readFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("Z:\\Dev\\Java\\fd\\src\\main\\resources\\static\\results.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(String.valueOf(COMMA_DELIMITER));
                records.add(Arrays.asList(values));
            }
        }
    }

}
