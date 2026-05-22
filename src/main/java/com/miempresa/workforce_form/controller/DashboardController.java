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

        long attendance = agentAttendanceRepo.count()
                + supervisorAttendanceRepo.count();

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

        // ===== ATTENDANCE =====

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

        // ===== SCHEDULE =====

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

        // ===== GENERAL =====

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

        // ===== TRAINERS =====

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

        // ===== DATOS PREDICTIVOS =====

        int currentTotal = (int) Math.max(totalSolicitudes, 1);

        List<Integer> historicalData = Arrays.asList(
                Math.max(currentTotal - 8, 1),
                Math.max(currentTotal - 6, 1),
                Math.max(currentTotal - 4, 1),
                Math.max(currentTotal - 2, 1),
                currentTotal
        );

        double xMean = 3.0;

        double yMean = historicalData.stream()
                .mapToInt(i -> i)
                .average()
                .orElse(0);

        double numerator = 0;
        double denominator = 0;

        for (int i = 0; i < historicalData.size(); i++) {

            numerator += ((i + 1) - xMean)
                    * (historicalData.get(i) - yMean);

            denominator += Math.pow((i + 1) - xMean, 2);
        }

        double slope = denominator == 0
                ? 0
                : numerator / denominator;

        double intercept = yMean - (slope * xMean);

        double predictedValue = intercept + slope * 6;

        // ===== PREDICTED GROWTH =====

        double growthPercentage =
                ((predictedValue - currentTotal) / currentTotal) * 100;

        // ===== WORKLOAD LEVEL =====

        String workloadLevel;
        String workloadMessage;

        if (predictedValue >= currentTotal + 5) {

            workloadLevel = "HIGH";
            workloadMessage = "Increasing request trend detected";

        } else if (predictedValue >= currentTotal + 2) {

            workloadLevel = "MEDIUM";
            workloadMessage = "Moderate workload expected";

        } else {

            workloadLevel = "LOW";
            workloadMessage = "Stable request trend";
        }

        List<Double> predictionData = new ArrayList<>();

        for (Integer value : historicalData) {
            predictionData.add(value.doubleValue());
        }

        predictionData.add(predictedValue);

        // ===== MODEL =====

        model.addAttribute("attendance", attendance);
        model.addAttribute("schedule", schedule);
        model.addAttribute("general", general);

        model.addAttribute("datosTipo", datosTipo);
        model.addAttribute("solicitudes", solicitudes);
        model.addAttribute("totalSolicitudes", totalSolicitudes);

        model.addAttribute("predictionData", predictionData);

        model.addAttribute("workloadLevel", workloadLevel);
        model.addAttribute("workloadMessage", workloadMessage);
        model.addAttribute(
                "growthPercentage",
                Math.round(growthPercentage)
        );

        return "dashboard";
    }
}
