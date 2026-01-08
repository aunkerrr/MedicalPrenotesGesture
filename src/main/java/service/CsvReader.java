package service;

import model.PrenoteObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CsvReader {
    public List<PrenoteObject> readCsv(String filePath) {
        List<PrenoteObject> result = new ArrayList<>();
        int lineNumber = 0;
        try (FileReader fileReader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lineNumber++;
                // Skip header if present
                if (lineNumber == 1 && line.toLowerCase().startsWith("record_id")) {
                    continue;
                }
                String[] data = line.split(",", -1);
                // Expecting 10 columns
                if (data.length < 10) {
                    System.err.println("Line " + lineNumber + ": wrong number of columns (got " + data.length + ") - skipping");
                    continue;
                }

                try {
                    String record_id = data[0].trim();
                    java.time.LocalDateTime date_time = Validator.parseDateTime(data[1].trim());
                    String patient_id = data[2].trim();
                    String department = data[3].trim();
                    String procedure = data[4].trim();
                    int priority = Validator.parsePriority(data[5].trim());
                    int wait_min = Validator.parseIntField(data[6].trim(), "wait_min", 0, 10000);
                    int duration_min = Validator.parseIntField(data[7].trim(), "duration_min", 0, 10000);
                    int outcome_score = Validator.parseIntField(data[8].trim(), "outcome_score", 0, 100);
                    double cost_eur = Validator.parseDoubleField(data[9].trim(), "cost_eur", 0.0, 1e9);

                    PrenoteObject obj = new PrenoteObject(record_id, date_time, patient_id, department, procedure, priority, wait_min, duration_min, outcome_score, cost_eur);
                    result.add(obj);
                } catch (IllegalArgumentException e) {
                    System.err.println("Line " + lineNumber + ": validation failed - " + e.getMessage() + " - skipping");
                } catch (Exception e) {
                    System.err.println("Line " + lineNumber + ": unexpected error - " + e.getMessage() + " - skipping");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
