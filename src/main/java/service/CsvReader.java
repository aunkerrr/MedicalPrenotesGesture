package service;

import model.PrenoteObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {
    private static final Logger logger = LoggerFactory.getLogger(CsvReader.class);

    public List<PrenoteObject> readCsv(Path filePath) {
        logger.info("Starting to read CSV file: {}", filePath);
        List<PrenoteObject> result = new ArrayList<>();
        int lineNumber = 0;
        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath)){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lineNumber++;
                // Skip header if present
                if (lineNumber == 1 && line.toLowerCase().startsWith("record_id")) {
                    logger.debug("Skipping header at line 1");
                    continue;
                }
                String[] data = line.split(",", -1);
                // Expecting 10 columns
                if (data.length < 10) {
                    logger.warn("Line {}: wrong number of columns (got {}) - skipping", lineNumber, data.length);
                    continue;
                }

                try {
                    String record_id = data[0].trim();
                    LocalDateTime date_time = Validator.parseDateTime(data[1].trim());
                    String patient_id = data[2].trim();
                    String department = data[3].trim();
                    String procedure = data[4].trim();
                    int priority = Validator.parsePriority(data[5].trim());
                    int wait_min = Validator.parseIntField(data[6].trim(),
                            "wait_min",
                            0, 10000);
                    int duration_min = Validator.parseIntField(data[7].trim(),
                            "duration_min",
                            0, 10000);
                    int outcome_score = Validator.parseIntField(data[8].trim(),
                            "outcome_score",
                            0, 100);
                    double cost_eur = Validator.parseDoubleField(data[9].trim(),
                            "cost_eur",
                            0.0, 1e9);

                    PrenoteObject obj = new PrenoteObject(record_id, date_time, patient_id, department, procedure, priority, wait_min, duration_min, outcome_score, cost_eur);
                    result.add(obj);
                } catch (Exception e) {
                    logger.error("Line {}: error parsing record - {} - skipping", lineNumber, e.getMessage(), e);
                }
            }
            logger.info("Finished reading CSV file. Total records parsed: {}", result.size());

        } catch (IOException e) {
            logger.error("Critical Error: file not found or cannot be read: {}", filePath, e);
        }
        return result;
    }
}
