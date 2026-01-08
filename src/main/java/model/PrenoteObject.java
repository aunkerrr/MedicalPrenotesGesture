package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class PrenoteObject {
    private String record_id;
    private LocalDateTime date_time;
    private String patient_id;
    private String department;
    private String procedure;
    private int priority;
    private int wait_min;
    private int duration_min;
    private int outcome_score;
    private double cost_eur;

    public PrenoteObject(String record_id,
                         LocalDateTime date_time,
                         String patient_id,
                         String department,
                         String procedure,
                         int priority,
                         int wait_min,
                         int duration_min,
                         int outcome_score,
                         Double cost_eur) {
        this.record_id = record_id;
        this.date_time = date_time;
        this.patient_id = patient_id;
        this.department = department;
        this.procedure = procedure;
        this.priority = priority;
        this.wait_min = wait_min;
        this.duration_min = duration_min;
        this.outcome_score = outcome_score;
        this.cost_eur = cost_eur;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public LocalDateTime getDate_time() {
        return date_time;
    }

    public void setDate_time(LocalDateTime date_time) {
        this.date_time = date_time;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getWait_min() {
        return wait_min;
    }

    public void setWait_min(int wait_min) {
        this.wait_min = wait_min;
    }

    public int getDuration_min() {
        return duration_min;
    }

    public void setDuration_min(int duration_min) {
        this.duration_min = duration_min;
    }

    public int getOutcome_score() {
        return outcome_score;
    }

    public void setOutcome_score(int outcome_score) {
        this.outcome_score = outcome_score;
    }

    public double getCost_eur() {
        return cost_eur;
    }

    public void setCost_eur(double cost_eur) {
        this.cost_eur = cost_eur;
    }

    @Override
    public String toString() {
        return "PrenoteObject{" +
                "record_id='" + record_id + '\'' +
                ", date_time=" + date_time +
                ", patient_id='" + patient_id + '\'' +
                ", department='" + department + '\'' +
                ", procedure='" + procedure + '\'' +
                ", priority=" + priority +
                ", wait_min=" + wait_min +
                ", duration_min=" + duration_min +
                ", outcome_score=" + outcome_score +
                ", cost_eur=" + cost_eur +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrenoteObject that = (PrenoteObject) o;
        return Objects.equals(record_id, that.record_id) && Objects.equals(date_time, that.date_time) && Objects.equals(patient_id, that.patient_id) && Objects.equals(department, that.department) && Objects.equals(procedure, that.procedure) && Objects.equals(priority, that.priority) && Objects.equals(wait_min, that.wait_min) && Objects.equals(duration_min, that.duration_min) && Objects.equals(outcome_score, that.outcome_score) && Objects.equals(cost_eur, that.cost_eur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(record_id,
                date_time, patient_id,
                department, procedure,
                priority, wait_min, duration_min,
                outcome_score, cost_eur) * 17 + 31;
    }
}
