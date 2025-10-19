package com.miempresa.workforce_form.controller;

import com.miempresa.workforce_form.model.SupervisorAttendanceRequest;
import com.miempresa.workforce_form.repository.SupervisorAttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SupervisorAttendanceController {

    @Autowired
    private SupervisorAttendanceRepository repository;

    @PostMapping("/submit-supervisor-attendance")
    public String submitSupervisorAttendance(SupervisorAttendanceRequest request) {
        repository.save(request);
        return "success"; // Redirige a la página de confirmación
    }
}

