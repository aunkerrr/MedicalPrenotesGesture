package service;

import model.Department;
import model.PrestazioneMedica;
import model.Priority;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GestionePrestazioni {
    private List<PrestazioneMedica> prestazioniValide = new ArrayList<>();
    private List<String> scarti = new ArrayList<>();

    public void leggiFile(String filePath) {
        try {
            List<String> righe = Files.readAllLines(Path.of(filePath));
            for (int i = 1; i < righe.size(); i++) {
                String riga = righe.get(i);
                processaRiga(riga, i + 1);
            }
        } catch (IOException e) {
            System.err.println("Errore lettura file: " + e.getMessage());
        }
    }

    private void processaRiga(String riga, int numeroRiga) {
        String[] campi = riga.split(",");
        if (campi.length != 10) {
            scarti.add("Riga " + numeroRiga + ": Campi mancanti (" + campi.length + ") - " + riga);
            return;
        }

        try {
            int recordId = Integer.parseInt(campi[0].trim());
            LocalDateTime dateTime = LocalDateTime.parse(campi[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String patientId = campi[2].trim();
            Department department = Department.valueOf(campi[3].trim());
            String procedure = campi[4].trim();
            Priority priority = Priority.valueOf(campi[5].trim());
            int waitMin = Integer.parseInt(campi[6].trim());
            int durationMin = Integer.parseInt(campi[7].trim());
            int outcomeScore = Integer.parseInt(campi[8].trim());
            double costEur = Double.parseDouble(campi[9].trim());

            // Validazione
            if (waitMin < 0) throw new IllegalArgumentException("wait_min negativo");
            if (durationMin <= 0) throw new IllegalArgumentException("duration_min deve essere > 0");
            if (outcomeScore < 0 || outcomeScore > 100) throw new IllegalArgumentException("outcome_score fuori range (0-100)");

            PrestazioneMedica p = new PrestazioneMedica(recordId, dateTime, patientId, department, procedure, priority, waitMin, durationMin, outcomeScore, costEur);
            prestazioniValide.add(p);

        } catch (Exception e) {
            scarti.add("Riga " + numeroRiga + ": Errore parsing/validazione - " + e.getMessage() + " - " + riga);
        }
    }

    public void stampaReport() {
        System.out.println("--- REPORT ---");
        System.out.println("Prestazioni valide: " + prestazioniValide.size());
        System.out.println("Prestazioni scartate: " + scarti.size());
        System.out.println("\nMotivi scarto (primi 10):");
        scarti.stream().limit(10).forEach(System.out::println);

        System.out.println("\n--- ANALISI DATI ---");

        // Tempo medio di attesa complessivo
        double mediaAttesa = prestazioniValide.stream()
                .mapToInt(PrestazioneMedica::getWaitMin)
                .average()
                .orElse(0);
        System.out.printf("Tempo medio attesa complessivo: %.2f min%n", mediaAttesa);

        // Tempo medio di attesa per department
        System.out.println("\nTempo medio attesa per department:");
        Map<Department, Double> mediaAttesaPerDept = prestazioniValide.stream()
                .collect(Collectors.groupingBy(
                        PrestazioneMedica::getDepartment,
                        Collectors.averagingInt(PrestazioneMedica::getWaitMin)
                ));
        mediaAttesaPerDept.forEach((dept, avg) -> System.out.printf("%s: %.2f min%n", dept, avg));

        // Top 3 procedure più frequenti
        System.out.println("\nTop 3 procedure più frequenti:");
        Map<String, Long> procedureCount = prestazioniValide.stream()
                .collect(Collectors.groupingBy(PrestazioneMedica::getProcedure, Collectors.counting()));
        
        procedureCount.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(3)
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));

        // Costo totale e costo medio per department
        System.out.println("\nCosto totale e medio per department:");
        Map<Department, Double> costoTotalePerDept = prestazioniValide.stream()
                .collect(Collectors.groupingBy(
                        PrestazioneMedica::getDepartment,
                        Collectors.summingDouble(PrestazioneMedica::getCostEur)
                ));
        Map<Department, Double> costoMedioPerDept = prestazioniValide.stream()
                .collect(Collectors.groupingBy(
                        PrestazioneMedica::getDepartment,
                        Collectors.averagingDouble(PrestazioneMedica::getCostEur)
                ));
        
        costoTotalePerDept.forEach((dept, total) -> {
            double avg = costoMedioPerDept.get(dept);
            System.out.printf("%s: Totale %.2f €, Medio %.2f €%n", dept, total, avg);
        });

        // Success rate (outcome >= 80)
        long successCount = prestazioniValide.stream()
                .filter(p -> p.getOutcomeScore() >= 80)
                .count();
        double successRate = (double) successCount / prestazioniValide.size() * 100;
        System.out.printf("\nSuccess Rate complessivo (outcome >= 80): %.2f%%%n", successRate);

        System.out.println("Success Rate per department:");
        Map<Department, Long> totalPerDept = prestazioniValide.stream()
                .collect(Collectors.groupingBy(PrestazioneMedica::getDepartment, Collectors.counting()));
        
        Map<Department, Long> successPerDept = prestazioniValide.stream()
                .filter(p -> p.getOutcomeScore() >= 80)
                .collect(Collectors.groupingBy(PrestazioneMedica::getDepartment, Collectors.counting()));

        totalPerDept.forEach((dept, total) -> {
            long success = successPerDept.getOrDefault(dept, 0L);
            double rate = (double) success / total * 100;
            System.out.printf("%s: %.2f%%%n", dept, rate);
        });

        // Prestazione più costosa
        prestazioniValide.stream()
                .max(Comparator.comparingDouble(PrestazioneMedica::getCostEur))
                .ifPresent(p -> System.out.println("\nPrestazione più costosa: " + p));

        // Allarme performance
        System.out.println("\n--- ALLARME PERFORMANCE (wait > 60 o URGENT) ---");
        prestazioniValide.stream()
                .filter(p -> p.getWaitMin() > 60 || p.getPriority() == Priority.URGENT)
                .sorted(Comparator.comparingInt(PrestazioneMedica::getWaitMin).reversed())
                .forEach(System.out::println);

        // Facoltativo: Indice efficienza
        System.out.println("\n--- TOP 5 EFFICIENZA ---");
        prestazioniValide.stream()
                .sorted((p1, p2) -> {
                    double eff1 = (double) p1.getOutcomeScore() / (p1.getWaitMin() + p1.getDurationMin());
                    double eff2 = (double) p2.getOutcomeScore() / (p2.getWaitMin() + p2.getDurationMin());
                    return Double.compare(eff2, eff1);
                })
                .limit(5)
                .forEach(p -> {
                    double eff = (double) p.getOutcomeScore() / (p.getWaitMin() + p.getDurationMin());
                    System.out.printf("Eff: %.2f - %s%n", eff, p);
                });
    }
}
