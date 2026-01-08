package app;

import service.CsvReader;
import model.PrenoteObject;

import java.nio.file.Paths;
import java.util.List;

public class ReaderSmoke {
    public static void main(String[] args) {
        String path = "dataSheet/prestazioni.csv";
        if (args.length > 0) path = args[0];
        CsvReader reader = new CsvReader();
        List<PrenoteObject> items = reader.readCsv(Paths.get(path).toAbsolutePath().toString());
        System.out.println("Loaded " + items.size() + " valid records from: " + path);
        for (int i = 0; i < Math.min(items.size(), 10); i++) {
            System.out.println(items.get(i));
        }
    }
}

