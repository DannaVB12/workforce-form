package com.miempresa.workforce_form.controller;

import com.miempresa.workforce_form.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class DashboardController {

    @Autowired private AgentAttendanceRepository agentAttendanceRepo;
    @Autowired private AgentScheduleRepository agentScheduleRepo;
    @Autowired private GeneralRequestRepository generalRequestRepo;
    @Autowired private SupervisorAttendanceRepository supervisorAttendanceRepo;
    @Autowired private SupervisorScheduleRepository supervisorScheduleRepo;
    @Autowired private SupervisorGeneralRepository supervisorGeneralRepo;
    @Autowired(required = false) private TrainerScheduleRepository trainerScheduleRepo;
    @Autowired(required = false) private TrainerGeneralRepository trainerGeneralRepo;

    @GetMapping("/dashboard")
    public String verDashboard(Model model) {

        long attendance = agentAttendanceRepo.count() + supervisorAttendanceRepo.count();

        long schedule = agentScheduleRepo.count()
                + supervisorScheduleRepo.count()
                + (trainerScheduleRepo != null ? trainerScheduleRepo.count() : 0);

        long general = generalRequestRepo.count()
                + supervisorGeneralRepo.count()
                + (trainerGeneralRepo != null ? trainerGeneralRepo.count() : 0);

        long totalSolicitudes = attendance + schedule + general;

        List<Object[]> datosTipo = new ArrayList<>();
        datosTipo.add(new Object[]{"Attendance", attendance});
        datosTipo.add(new Object[]{"Schedule Change", schedule});
        datosTipo.add(new Object[]{"General Request", general});

        List<Map<String, Object>> solicitudes = new ArrayList<>();

        // ✅ DIRECTO, SIN REFLECTION (como lo tenías antes)
        agentAttendanceRepo.findAll().forEach(r -> {
            Map<String, Object> row = new HashMap<>();
            row.put("id", r.getId());
            row.put("submittedBy", r.getSubmittedBy());
            row.put("requestType", "Attendance");
            row.put("status", r.getStatus());
            solicitudes.add(row);
        });

        supervisorAttendanceRepo.findAll().forEach(r -> {
            Map<String, Object> row = new HashMap<>();
            row.put("id", r.getId());
            row.put("submittedBy", r.getSubmittedBy());
            row.put("requestType", "Attendance");
            row.put("status", r.getStatus());
            solicitudes.add(row);
        });

        agentScheduleRepo.findAll().forEach(r -> {
            Map<String, Object> row = new HashMap<>();
            row.put("id", r.getId());
            row.put("submittedBy", r.getSubmittedBy());
            row.put("requestType", "Schedule Change");
            row.put("status", r.getStatus());
            solicitudes.add(row);
        });

        supervisorScheduleRepo.findAll().forEach(r -> {
            Map<String, Object> row = new HashMap<>();
            row.put("id", r.getId());
            row.put("submittedBy", r.getSubmittedBy());
            row.put("requestType", "Schedule Change");
            row.put("status", r.getStatus());
            solicitudes.add(row);
        });

        generalRequestRepo.findAll().forEach(r -> {
            Map<String, Object> row = new HashMap<>();
            row.put("id", r.getId());
            row.put("submittedBy", r.getSubmittedBy());
            row.put("requestType", "General Request");
            row.put("status", r.getStatus());
            solicitudes.add(row);
        });

        supervisorGeneralRepo.findAll().forEach(r -> {
            Map<String, Object> row = new HashMap<>();
            row.put("id", r.getId());
            row.put("submittedBy", r.getSubmittedBy());
            row.put("requestType", "General Request");
            row.put("status", r.getStatus());
            solicitudes.add(row);
        });

        if (trainerScheduleRepo != null) {
            trainerScheduleRepo.findAll().forEach(r -> {
                Map<String, Object> row = new HashMap<>();
                row.put("id", r.getId());
                row.put("submittedBy", r.getSubmittedBy());
                row.put("requestType", "Schedule Change");
                row.put("status", r.getStatus());
                solicitudes.add(row);
            });
        }

        if (trainerGeneralRepo != null) {
            trainerGeneralRepo.findAll().forEach(r -> {
                Map<String, Object> row = new HashMap<>();
                row.put("id", r.getId());
                row.put("submittedBy", r.getSubmittedBy());
                row.put("requestType", "General Request");
                row.put("status", r.getStatus());
                solicitudes.add(row);
            });
        }

        model.addAttribute("datosTipo", datosTipo);
        model.addAttribute("solicitudes", solicitudes);
        model.addAttribute("totalSolicitudes", totalSolicitudes);

        return "dashboard";
    }
}
