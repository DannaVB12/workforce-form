package com.miempresa.workforce_form.controller;

import com.miempresa.workforce_form.model.AgentAttendanceRequest;
import com.miempresa.workforce_form.model.AgentScheduleRequest;
import com.miempresa.workforce_form.model.SupervisorAttendanceRequest;
import com.miempresa.workforce_form.model.SupervisorScheduleRequest;
import com.miempresa.workforce_form.repository.AgentAttendanceRepository;
import com.miempresa.workforce_form.repository.AgentScheduleRepository;
import com.miempresa.workforce_form.repository.SupervisorAttendanceRepository;
import com.miempresa.workforce_form.repository.SupervisorScheduleRepository;
import com.miempresa.workforce_form.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/analyst")
public class AnalystController {

    @Autowired
    private AgentAttendanceRepository agentAttendanceRepo;

    @Autowired
    private AgentScheduleRepository agentScheduleRepo;

    @Autowired
    private SupervisorAttendanceRepository supervisorAttendanceRepo;

    @Autowired
    private SupervisorScheduleRepository supervisorScheduleRepo;

    @Autowired
    private ReportService reportService;

    // === 1️⃣ Mostrar todas las solicitudes ===
    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        List<Map<String, Object>> allRequests = new ArrayList<>();

        // AGENTE - Attendance
        for (AgentAttendanceRequest r : agentAttendanceRepo.findAll()) {
            Map<String, Object> row = new HashMap<>();
            row.put("id", r.getId());
            row.put("submittedBy", r.getSubmitted_by());
            row.put("scope", r.getScope());
            row.put("role", "Agent");
            row.put("requestType", "Attendance");
            row.put("createdAt", r.getCreated_at());
            row.put("status", r.getStatus());
            allRequests.add(row);
        }

        // AGENTE - Schedule
        for (AgentScheduleRequest r : agentScheduleRepo.findAll()) {
            Map<String, Object> row = new HashMap<>();
            row.put("id", r.getId());
            row.put("submittedBy", r.getSubmitted_by());
            row.put("scope", r.getScope());
            row.put("role", "Agent");
            row.put("requestType", "Schedule Change");
            row.put("createdAt", r.getCreated_at());
            row.put("status", r.getStatus());
            allRequests.add(row);
        }

        // SUPERVISOR - Attendance
        for (SupervisorAttendanceRequest r : supervisorAttendanceRepo.findAll()) {
            Map<String, Object> row = new HashMap<>();
            row.put("id", r.getId());
            row.put("submittedBy", r.getSubmitted_by());
            row.put("scope", r.getScope());
            row.put("role", "Supervisor");
            row.put("requestType", "Attendance");
            row.put("createdAt", r.getCreated_at());
            row.put("status", r.getStatus());
            allRequests.add(row);
        }

        // SUPERVISOR - Schedule
        for (SupervisorScheduleRequest r : supervisorScheduleRepo.findAll()) {
            Map<String, Object> row = new HashMap<>();
            row.put("id", r.getId());
            row.put("submittedBy", r.getSubmitted_by());
            row.put("scope", r.getScope());
            row.put("role", "Supervisor");
            row.put("requestType", "Schedule Change");
            row.put("createdAt", r.getCreated_at());
            row.put("status", r.getStatus());
            allRequests.add(row);
        }

        // ORDENAR POR FECHA (más recientes primero)
        allRequests.sort((a, b) -> ((Date) b.get("createdAt")).compareTo((Date) a.get("createdAt")));

        model.addAttribute("requests", allRequests);
        return "analyst-dashboard";
    }

    // === 2️⃣ Actualizar estado ===
    @PostMapping("/update-status")
    public String updateStatus(@RequestParam Long id, @RequestParam String status) {
        // Actualiza en todas las tablas posibles
        agentAttendanceRepo.updateStatusById(id, status);
        agentScheduleRepo.updateStatusById(id, status);
        supervisorAttendanceRepo.updateStatusById(id, status);
        supervisorScheduleRepo.updateStatusById(id, status);

        return "redirect:/analyst/dashboard";
    }

    // === 3️⃣ Descargar reporte PDF ===
    @GetMapping("/download-pdf")
    public void downloadPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=requests_report.pdf");

        List<Map<String, Object>> allRequests = new ArrayList<>();
        // reutilizamos el método de arriba
        agentAttendanceRepo.findAll().forEach(r -> {
            Map<String, Object> row = new HashMap<>();
            row.put("id", r.getId());
            row.put("submittedBy", r.getSubmitted_by());
            row.put("scope", r.getScope());
            row.put("role", "Agent");
            row.put("requestType", "Attendance");
            row.put("createdAt", r.getCreated_at());
            row.put("status", r.getStatus());
            allRequests.add(row);
        });
        reportService.generateRequestsReport(allRequests, response.getOutputStream());
    }
}
