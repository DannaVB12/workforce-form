package com.miempresa.workforce_form.controller;

import com.miempresa.workforce_form.model.*;
import com.miempresa.workforce_form.repository.*;
import com.miempresa.workforce_form.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/analyst")
public class AnalystController {

    @Autowired
    private GeneralRequestRepository generalRequestRepo;

    @Autowired
    private AgentAttendanceRepository agentAttendanceRepo;

    @Autowired
    private AgentScheduleRepository agentScheduleRepo;

    @Autowired
    private SupervisorAttendanceRepository supervisorAttendanceRepo;

    @Autowired
    private SupervisorScheduleRepository supervisorScheduleRepo;

    @Autowired
    private SupervisorGeneralRepository supervisorGeneralRepo;

    @Autowired(required = false)
    private TrainerScheduleRepository trainerScheduleRepo;

    @Autowired(required = false)
    private TrainerGeneralRepository trainerGeneralRepo;

    @Autowired
    private ReportService reportService;

    // 🔐 Clave de acceso al módulo Analyst
    private static final String ACCESS_KEY = "analyst2025";

    // Tiempo de sesión (en segundos) → 5 minutos
    private static final int SESSION_TIMEOUT = 300;

    // =====================================================
    // 🔹 LOGIN
    // =====================================================
    @GetMapping("/login")
    public String showLoginPage(HttpSession session) {
        Boolean isAnalyst = (Boolean) session.getAttribute("isAnalyst");
        if (isAnalyst != null && isAnalyst) {
            return "redirect:/analyst/dashboard";
        }
        return "analyst-login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String password, HttpSession session, Model model) {
        if (ACCESS_KEY.equals(password)) {
            session.setAttribute("isAnalyst", true);
            session.setMaxInactiveInterval(SESSION_TIMEOUT);
            return "redirect:/analyst/dashboard";
        } else {
            model.addAttribute("error", "Invalid access key");
            return "analyst-login";
        }
    }

    // =====================================================
    // 🔹 LOGOUT
    // =====================================================
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/analyst/login";
    }

    // =====================================================
    // 🔹 DASHBOARD
    // =====================================================
    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {
        if (!isAuthorized(session)) {
            return "redirect:/analyst/login";
        }

        List<Map<String, Object>> allRequests = collectAllRequests();

        // Ordenar por fecha (más recientes primero)
        allRequests.sort((a, b) -> {
            Object dateA = a.get("createdAt");
            Object dateB = b.get("createdAt");
            if (dateA == null || dateB == null) return 0;
            if (dateA instanceof LocalDateTime && dateB instanceof LocalDateTime)
                return ((LocalDateTime) b.get("createdAt")).compareTo((LocalDateTime) a.get("createdAt"));
            return 0;
        });

        model.addAttribute("requests", allRequests);
        return "analyst-dashboard";
    }

    // =====================================================
    // 🔹 Actualizar estado (CORREGIDO)
    // =====================================================
    @PostMapping("/update-status")
    public String updateStatus(
            @RequestParam Long id,
            @RequestParam String status,
            @RequestParam String role,
            @RequestParam String requestType,
            HttpSession session) {

        if (!isAuthorized(session)) {
            return "redirect:/analyst/login";
        }

        try {
            System.out.println("🟢 DEBUG => ID: " + id + ", ROLE: " + role + ", TYPE: " + requestType + ", STATUS: " + status);

            String normalizedRole = role.trim().toLowerCase();
            String normalizedType = requestType.trim().toLowerCase();

            // === AGENT ===
            if (normalizedRole.contains("agent")) {
                if (normalizedType.contains("attendance")) {
                    agentAttendanceRepo.updateStatusById(id, status);
                } else if (normalizedType.contains("schedule")) {
                    agentScheduleRepo.updateStatusById(id, status);
                } else if (normalizedType.contains("general")) {
                    generalRequestRepo.updateStatusById(id, status);
                }
            }

            // === SUPERVISOR ===
            else if (normalizedRole.contains("supervisor")) {
                if (normalizedType.contains("attendance")) {
                    supervisorAttendanceRepo.updateStatusById(id, status);
                } else if (normalizedType.contains("schedule")) {
                    supervisorScheduleRepo.updateStatusById(id, status);
                } else if (normalizedType.contains("general")) {
                    supervisorGeneralRepo.updateStatusById(id, status);
                }
            }

            // === TRAINER ===
            else if (normalizedRole.contains("trainer")) {
                if (trainerScheduleRepo != null && normalizedType.contains("schedule")) {
                    trainerScheduleRepo.updateStatusById(id, status);
                } else if (trainerGeneralRepo != null && normalizedType.contains("general")) {
                    trainerGeneralRepo.updateStatusById(id, status);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/analyst/dashboard";
    }

    // =====================================================
    // 🔹 Descargar PDF
    // =====================================================
    @GetMapping("/download-pdf")
    public void downloadPdf(HttpServletResponse response, HttpSession session) throws IOException {
        if (!isAuthorized(session)) {
            response.sendRedirect("/analyst/login");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=requests_report.pdf");

        List<Map<String, Object>> allRequests = collectAllRequests();
        reportService.generateRequestsReport(allRequests, response.getOutputStream());
    }

    // =====================================================
    // 🔹 Descargar Excel
    // =====================================================
    @GetMapping("/download-excel")
    public void downloadExcel(HttpServletResponse response, HttpSession session) throws IOException {
        if (!isAuthorized(session)) {
            response.sendRedirect("/analyst/login");
            return;
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=requests_report.xlsx");

        List<Map<String, Object>> allRequests = collectAllRequests();
        reportService.generateRequestsExcel(allRequests, response.getOutputStream());
    }

    // =====================================================
    // 🔹 Métodos auxiliares
    // =====================================================
    private boolean isAuthorized(HttpSession session) {
        Boolean isAnalyst = (Boolean) session.getAttribute("isAnalyst");
        return isAnalyst != null && isAnalyst;
    }

    private Map<String, Object> createRow(Long id, String submittedBy, String scope,
                                          String role, String type, Object createdAt, String status) {
        Map<String, Object> row = new HashMap<>();
        row.put("id", id);
        row.put("submittedBy", submittedBy);
        row.put("scope", scope);
        row.put("role", role);
        row.put("requestType", type);
        row.put("createdAt", createdAt);
        row.put("status", status);
        return row;
    }

    private List<Map<String, Object>> collectAllRequests() {
        List<Map<String, Object>> allRequests = new ArrayList<>();

        // 🟢 AGENT REQUESTS
        agentAttendanceRepo.findAll().forEach(r ->
                allRequests.add(createRow(r.getId(), r.getSubmittedBy(), r.getScope(), "Agent",
                        "Attendance", r.getCreatedAt(), r.getStatus()))
        );

        agentScheduleRepo.findAll().forEach(r ->
                allRequests.add(createRow(r.getId(), r.getSubmittedBy(), r.getScope(), "Agent",
                        "Schedule Change", r.getCreatedAt(), r.getStatus()))
        );

        generalRequestRepo.findAll().forEach(r ->
                allRequests.add(createRow(r.getId(), r.getSubmittedBy(), r.getScope(), "Agent",
                        "General Request", r.getCreatedAt(), r.getStatus()))
        );

        // 🟣 SUPERVISOR REQUESTS
        supervisorAttendanceRepo.findAll().forEach(r ->
                allRequests.add(createRow(r.getId(), r.getSubmittedBy(), r.getScope(), "Supervisor",
                        "Attendance", r.getCreatedAt(), r.getStatus()))
        );

        supervisorScheduleRepo.findAll().forEach(r ->
                allRequests.add(createRow(r.getId(), r.getSubmittedBy(), r.getScope(), "Supervisor",
                        "Schedule Change", r.getCreatedAt(), r.getStatus()))
        );

        supervisorGeneralRepo.findAll().forEach(r ->
                allRequests.add(createRow(r.getId(), r.getSubmittedBy(), r.getScope(), "Supervisor",
                        "General Request", r.getCreatedAt(), r.getStatus()))
        );

        // 🔵 TRAINER REQUESTS
        if (trainerScheduleRepo != null)
            trainerScheduleRepo.findAll().forEach(r ->
                    allRequests.add(createRow(r.getId(), r.getSubmittedBy(), r.getScope(), "Trainer",
                            "Schedule Change", r.getCreatedAt(), r.getStatus()))
            );

        if (trainerGeneralRepo != null)
            trainerGeneralRepo.findAll().forEach(r ->
                    allRequests.add(createRow(r.getId(), r.getSubmittedBy(), r.getScope(), "Trainer",
                            "General Request", r.getCreatedAt(), r.getStatus()))
            );

        return allRequests;
    }
}
