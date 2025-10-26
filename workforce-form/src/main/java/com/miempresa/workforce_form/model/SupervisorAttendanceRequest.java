package com.miempresa.workforce_form.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "supervisor_attendance_requests")
public class SupervisorAttendanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String scope;
    private String role;
    private String submittedBy;
    private String typeOfRequest;
    private String agentEmail;
    private String appliesTo;
    private String durationType;
    private String notes;
    private String status = "Pending";

    private LocalDateTime createdAt = LocalDateTime.now();

    // === Getters y Setters ===
    public Long getId() { return id; }

    public String getScope() { return scope; }
    public void setScope(String scope) { this.scope = scope; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getSubmittedBy() { return submittedBy; }
    public void setSubmittedBy(String submittedBy) { this.submittedBy = submittedBy; }

    public String getTypeOfRequest() { return typeOfRequest; }
    public void setTypeOfRequest(String typeOfRequest) { this.typeOfRequest = typeOfRequest; }

    public String getAgentEmail() { return agentEmail; }
    public void setAgentEmail(String agentEmail) { this.agentEmail = agentEmail; }

    public String getAppliesTo() { return appliesTo; }
    public void setAppliesTo(String appliesTo) { this.appliesTo = appliesTo; }

    public String getDurationType() { return durationType; }
    public void setDurationType(String durationType) { this.durationType = durationType; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
