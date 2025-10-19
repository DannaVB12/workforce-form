package com.miempresa.workforce_form.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "supervisor_attendance_request")
public class SupervisorAttendanceRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String scope;
    private String role;
    private String submittedBy;
    private String agentEmail;
    private String typeOfRequest;
    private String appliesTo;
    private String durationType;

    @Column(columnDefinition = "TEXT")
    private String notes;

    private LocalDateTime createdAt;

    // === Constructor ===
    public SupervisorAttendanceRequest() {
        this.createdAt = LocalDateTime.now();
    }

    // === Getters y Setters ===
    public Long getId() { return id; }

    public String getScope() { return scope; }
    public void setScope(String scope) { this.scope = scope; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getSubmittedBy() { return submittedBy; }
    public void setSubmittedBy(String submittedBy) { this.submittedBy = submittedBy; }

    public String getAgentEmail() { return agentEmail; }
    public void setAgentEmail(String agentEmail) { this.agentEmail = agentEmail; }

    public String getTypeOfRequest() { return typeOfRequest; }
    public void setTypeOfRequest(String typeOfRequest) { this.typeOfRequest = typeOfRequest; }

    public String getAppliesTo() { return appliesTo; }
    public void setAppliesTo(String appliesTo) { this.appliesTo = appliesTo; }

    public String getDurationType() { return durationType; }
    public void setDurationType(String durationType) { this.durationType = durationType; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
