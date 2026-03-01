package app;

import service.GestionePrestazioni;

public class CsvReaderApp {
    public static void main(String[] args) {
        String path = "dataSheet/prestazioni.csv";
        if (args.length > 0) path = args[0];
        
        GestionePrestazioni gestione = new GestionePrestazioni();
        gestione.leggiFile(path);
        gestione.stampaReport();
    }
}
