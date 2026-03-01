package model;

import java.time.LocalDateTime;

public class PrestazioneMedica {
    private int recordId;
    private LocalDateTime dateTime;
    private String patientId;
    private Department department;
    private String procedure;
    private Priority priority;
    private int waitMin;
    private int durationMin;
    private int outcomeScore;
    private double costEur;

    public PrestazioneMedica(int recordId, LocalDateTime dateTime, String patientId, Department department, String procedure, Priority priority, int waitMin, int durationMin, int outcomeScore, double costEur) {
        this.recordId = recordId;
        this.dateTime = dateTime;
        this.patientId = patientId;
        this.department = department;
        this.procedure = procedure;
        this.priority = priority;
        this.waitMin = waitMin;
        this.durationMin = durationMin;
        this.outcomeScore = outcomeScore;
        this.costEur = costEur;
    }

    public int getRecordId() {
        return recordId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getPatientId() {
        return patientId;
    }

    public Department getDepartment() {
        return department;
    }

    public String getProcedure() {
        return procedure;
    }

    public Priority getPriority() {
        return priority;
    }

    public int getWaitMin() {
        return waitMin;
    }

    public int getDurationMin() {
        return durationMin;
    }

    public int getOutcomeScore() {
        return outcomeScore;
    }

    public double getCostEur() {
        return costEur;
    }

    @Override
    public String toString() {
        return "PrestazioneMedica{" +
                "recordId=" + recordId +
                ", dateTime=" + dateTime +
                ", patientId='" + patientId + '\'' +
                ", department=" + department +
                ", procedure='" + procedure + '\'' +
                ", priority=" + priority +
                ", waitMin=" + waitMin +
                ", durationMin=" + durationMin +
                ", outcomeScore=" + outcomeScore +
                ", costEur=" + costEur +
                '}';
    }
}
