package app;

import model.PrenoteObject;

import java.nio.file.Paths;
import java.util.List;

public class CsvReader {
    public static void main(String[] args) {
        String path = "dataSheet/prestazioni.csv";
        if (args.length > 0) path = args[0];
        service.CsvReader reader = new service.CsvReader();
        List<PrenoteObject> items = reader.readCsv(Paths.get(path).toAbsolutePath().toString());
        System.out.println("Loaded " + items.size() + " valid records from: " + path);
        for (int i = 0; i < Math.min(items.size(), 10); i++) {
            System.out.println(items.get(i));
        }
    }
}

