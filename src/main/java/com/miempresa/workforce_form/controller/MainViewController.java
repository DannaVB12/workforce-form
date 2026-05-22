package com.miempresa.workforce_form.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainViewController {

    // Página principal (selección de formulario)
    @GetMapping("/")
    public String showMainPage() {
        return "main";
    }

    // Vista del formulario de Attendance (Agente)
    @GetMapping("/agent/attendance")
    public String showAgentAttendanceForm(
            @RequestParam(required = false) String scope,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String submitted_by,
            Model model) {

        model.addAttribute("scope", scope);
        model.addAttribute("role", role);
        model.addAttribute("submitted_by", submitted_by);

        return "agent-attendance";
    }

    // Vista del formulario de Schedule Change (Agente)
    @GetMapping("/agent/schedule")
    public String showAgentScheduleForm() {
        return "agent-schedule";
    }

    // Vista del formulario de Attendance (Supervisor)
    @GetMapping("/supervisor/attendance")
    public String showSupervisorAttendanceForm() {
        return "supervisor-attendance";
    }

    // Vista del formulario de Schedule Change (Supervisor)
    @GetMapping("/supervisor/schedule")
    public String showSupervisorScheduleForm(
            @RequestParam(required = false) String scope,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String submitted_by,
            Model model) {

        model.addAttribute("scope", scope);
        model.addAttribute("role", role);
        model.addAttribute("submitted_by", submitted_by);

        return "supervisor-schedule";
    }
}
