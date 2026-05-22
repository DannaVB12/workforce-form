package com.miempresa.workforce_form.controller;

import com.miempresa.workforce_form.model.SupervisorAttendanceRequest;
import com.miempresa.workforce_form.repository.SupervisorAttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class SupervisorAttendanceController {

    @Autowired
    private SupervisorAttendanceRepository supervisorAttendanceRepo;

    // ✅ Mostrar la vista del formulario
    @GetMapping("/supervisor-attendance")
    public String showForm() {
        return "supervisor-attendance"; // Nombre del archivo HTML
    }

    // ✅ Procesar el envío del formulario
    @PostMapping("/submit-supervisor-attendance")
    public String submitSupervisorAttendance(@ModelAttribute SupervisorAttendanceRequest request) {
        try {
            request.setCreatedAt(LocalDateTime.now());
            request.setStatus("Pending");
            supervisorAttendanceRepo.save(request);
            return "redirect:/supervisor-attendance?success";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/supervisor-attendance?error";
 }
}
}