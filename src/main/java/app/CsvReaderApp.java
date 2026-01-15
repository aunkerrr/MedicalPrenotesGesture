package app;

import model.PrenoteObject;
import service.CsvReader;

import java.nio.file.Paths;
import java.util.List;

public class CsvReaderApp {
    public static void main(String[] args) {
        String path = "dataSheet/prestazioni.csv";
        if (args.length > 0) path = args[0];
        CsvReader reader = new CsvReader();
        List<PrenoteObject> items = reader.readCsv(Paths.get(path).toAbsolutePath());
        System.out.println("Loaded " + items.size() + " valid records from: " + path);
        items.forEach(System.out::println);
    }
}

