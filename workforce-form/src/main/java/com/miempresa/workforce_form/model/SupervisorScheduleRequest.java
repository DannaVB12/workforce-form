package com.miempresa.workforce_form.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "supervisor_schedule_requests")
public class SupervisorScheduleRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // === Campos del formulario ===
    private String activityType;         // Ej: Coaching, Meeting, Training, etc.
    private String requestType;          // Ej: Add new event, Remove event...
    private String groupEvent;           // Si es evento grupal o no
    private String calendarEventTitle;   // Título del evento
    private String agentInfo;            // Lista de agentes o nombres
    private String agentEmail;           // (Opcional, si se requiere más adelante)
    private String date;                 // Fecha del evento
    private String duration;             // Ej: 15 min, 1h, etc.
    private String notes;                // Campo libre para comentarios

    // === Datos base ===
    private String role;
    private String scope;
    private String submittedBy;

    private LocalDateTime createdAt = LocalDateTime.now();

    // === Getters y Setters ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getGroupEvent() {
        return groupEvent;
    }

    public void setGroupEvent(String groupEvent) {
        this.groupEvent = groupEvent;
    }

    public String getCalendarEventTitle() {
        return calendarEventTitle;
    }

    public void setCalendarEventTitle(String calendarEventTitle) {
        this.calendarEventTitle = calendarEventTitle;
    }

    public String getAgentInfo() {
        return agentInfo;
    }

    public void setAgentInfo(String agentInfo) {
        this.agentInfo = agentInfo;
    }

    public String getAgentEmail() {
        return agentEmail;
    }

    public void setAgentEmail(String agentEmail) {
        this.agentEmail = agentEmail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
