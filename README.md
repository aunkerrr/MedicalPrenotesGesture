Esercitazione n.10 - Gestione prestazioni mediche
Contesto
Un’azienda sanitaria esporta un file CSV con le prestazioni erogate (visite/esami) e alcune metriche di performance: tempi di attesa, durata, costo e un indicatore di esito (0–100). Il file non è pulito: ci sono righe con campi mancanti, numeri non validi, date sbagliate, valori negativi che vanno gestiti.
Consegnare almeno 2 classi che dovranno chiamarsi PrestazioneMedica e GestionePrestazioni, utilizzare ENUM dove necessario
Tracciato CSV
Ordine e significato dei campi del file
record_id (int)
date_time (yyyy-MM-dd HH:mm)
patient_id (string tipo P042)
department (es. CARDIO, RADIO, LAB, ORTHO, DERM, NEURO)
procedure (es. ECG, RX_TORACE, EMOCROMO…)
priority (LOW, MED, HIGH, URGENT)
wait_min (int)
duration_min (int)
outcome_score (int 0–100)
cost_eur (double)
Obiettivi
Lettura file
Parsing + eccezioni:
DateTimeParseException, NumberFormatException, righe con campi mancanti
validazione (valori negativi, outcome fuori range, priority non valida…)
Costruzione oggetto Prestazione
Stream per fare report:
filtri, map, collect, groupingBy, summarizingInt/Double, sorting, max/min
Gestione “righe scartate” con stampa su terminale (contatore + motivi).



Consegna
Parte A: Parsing
Leggi tutte le righe.


Salta l’header.
Per ogni riga:
split su
se non hai 10 campi → scarto
prova a fare parser di data e numeri (try/catch)
valida:
wait_min >= 0, duration_min > 0
0 <= outcome_score <= 100
priority tra LOW/MED/HIGH/URGENT


Se ok → costruisci Prestazione e mettila nella lista “buona”.
Se ko → incrementa contatore scarti e salva un messaggio (anche solo in una List<String>).
Parte B: Stream
Quante prestazioni valide? Quante scartate?
Tempo medio di attesa complessivo e per department.
Top 3 procedure più frequenti (conteggio).
Costo totale e costo medio per department.
Success rate: percentuale di prestazioni con outcome_score >= 80 (complessiva e per reparto).
La prestazione più costosa (tutta la riga) + reparto.
Allarme performance: estrai tutte le prestazioni con wait_min > 60 o priority = URGENT e ordinale per wait_min decrescente.
Facoltativo
“Indice efficienza”: eff = outcome_score / (wait_min + duration_min) (double).
Stampa la top 5 per efficienza (con 2 decimali).

