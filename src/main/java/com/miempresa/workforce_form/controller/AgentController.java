package com.miempresa.workforce_form.controller;

import com.miempresa.workforce_form.model.*;
import com.miempresa.workforce_form.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AgentController {

    @Autowired
    private AgentAttendanceRepository agentAttendanceRepo;

    @Autowired
    private AgentScheduleRepository agentScheduleRepo;

    @Autowired
    private GeneralRequestRepository generalRequestRepo;

    // =====================================================
    // 🔹 Attendance Request
    // =====================================================
    @GetMapping("/agent-attendance")
    public String showAttendanceForm() {
        return "agent-attendance";
    }

    @PostMapping("/submit-attendance")
    public String submitAttendance(@ModelAttribute AgentAttendanceRequest request) {
        request.setCreatedAt(LocalDateTime.now());
        request.setStatus("Pending");
        agentAttendanceRepo.save(request);
        return "redirect:/agent-attendance?success";
    }

    // =====================================================
    // 🔹 Schedule Change Request
    // =====================================================
    @GetMapping("/agent-schedule")
    public String showScheduleForm() {
        return "agent-schedule";
    }

    @PostMapping("/submit-schedule")
    public String submitSchedule(@ModelAttribute AgentScheduleRequest request) {
        request.setCreatedAt(LocalDateTime.now());
        request.setStatus("Pending");
        agentScheduleRepo.save(request);
        return "redirect:/agent-schedule?success";
    }

    // =====================================================
    // 🔹 General Request (GET + POST)
    // =====================================================
    @GetMapping("/agent-general")
    public String showGeneralRequestForm() {
        return "agent-general"; // archivo en templates/agent-general.html
    }

    @PostMapping("/submit-general")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> submitGeneral(@ModelAttribute GeneralRequest request) {
        request.setCreatedAt(LocalDateTime.now());
        request.setStatus("Pending");
        generalRequestRepo.save(request);

        Map<String, Object> payload = new HashMap<>();
        payload.put("ok", true);
        payload.put("id", request.getId());
        payload.put("message", "General request created");

        return ResponseEntity.ok(payload); // devuelve 200 OK para AJAX
    }
}
