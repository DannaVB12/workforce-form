package com.miempresa.workforce_form.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "agent_schedule_requests")
public class AgentScheduleRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String scope;
    private String role;
    private String submittedBy;
    private String activityAgent;
    private String agentEmail;
    private String appliesTo;
    private String timeOption;
    private String preferredTime;
    private String duration;
    private String notes;

    private String status = "Pending"; // 👈 nuevo campo
    private LocalDateTime createdAt = LocalDateTime.now(); // 👈 nuevo campo

    // === Getters y Setters ===
    public Long getId() { return id; }

    public String getScope() { return scope; }
    public void setScope(String scope) { this.scope = scope; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getSubmittedBy() { return submittedBy; }
    public void setSubmittedBy(String submittedBy) { this.submittedBy = submittedBy; }

    public String getActivityAgent() { return activityAgent; }
    public void setActivityAgent(String activityAgent) { this.activityAgent = activityAgent; }

    public String getAgentEmail() { return agentEmail; }
    public void setAgentEmail(String agentEmail) { this.agentEmail = agentEmail; }

    public String getAppliesTo() { return appliesTo; }
    public void setAppliesTo(String appliesTo) { this.appliesTo = appliesTo; }

    public String getTimeOption() { return timeOption; }
    public void setTimeOption(String timeOption) { this.timeOption = timeOption; }

    public String getPreferredTime() { return preferredTime; }
    public void setPreferredTime(String preferredTime) { this.preferredTime = preferredTime; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
