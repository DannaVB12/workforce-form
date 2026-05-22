package com.miempresa.workforce_form.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trainer_schedule_requests")
public class TrainerScheduleRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String scope;
    private String role;
    private String submittedBy;
    private String activityType;
    private String requestType;
    private String groupEvent;
    private String calendarEventTitle;
    private String agentInfo;
    private String date;
    private String duration;
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

    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }

    public String getRequestType() { return requestType; }
    public void setRequestType(String requestType) { this.requestType = requestType; }

    public String getGroupEvent() { return groupEvent; }
    public void setGroupEvent(String groupEvent) { this.groupEvent = groupEvent; }

    public String getCalendarEventTitle() { return calendarEventTitle; }
    public void setCalendarEventTitle(String calendarEventTitle) { this.calendarEventTitle = calendarEventTitle; }

    public String getAgentInfo() { return agentInfo; }
    public void setAgentInfo(String agentInfo) { this.agentInfo = agentInfo; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
