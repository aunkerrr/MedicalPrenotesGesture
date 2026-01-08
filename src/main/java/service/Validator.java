package service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Validator {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Parses date/time in format "yyyy-MM-dd HH:mm" and throws IllegalArgumentException on failure
    public static LocalDateTime parseDateTime(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("date_time is empty");
        }
        try {
            return LocalDateTime.parse(s, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("invalid date_time: " + s);
        }
    }

    // Maps priority strings to numeric values. Assumption: LOW=1, MED=2, HIGH=3, URGENT=4.
    // If a numeric string is provided, it will be parsed as-is.
    public static int parsePriority(String s) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException("priority is empty");
        }
        switch (s.trim().toUpperCase()) {
            case "LOW":
                return 1;
            case "MED":
            case "MEDIUM":
                return 2;
            case "HIGH":
                return 3;
            case "URGENT":
                return 4;
            default:
                try {
                    return Integer.parseInt(s);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("unknown priority: " + s);
                }
        }
    }

    // Parses an integer field and enforces min/max (inclusive). Throws IllegalArgumentException on failure.
    public static int parseIntField(String s, String fieldName, int min, int max) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is empty");
        }
        try {
            int v = Integer.parseInt(s);
            if (v < min || v > max) {
                throw new IllegalArgumentException(fieldName + " out of range: " + v);
            }
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " is not an integer: " + s);
        }
    }

    // Parses a double field and enforces min/max (inclusive). Throws IllegalArgumentException on failure.
    public static double parseDoubleField(String s, String fieldName, double min, double max) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is empty");
        }
        try {
            double v = Double.parseDouble(s);
            if (v < min || v > max) {
                throw new IllegalArgumentException(fieldName + " out of range: " + v);
            }
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " is not a number: " + s);
        }
    }
}
