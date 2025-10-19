package com.miempresa.workforce_form.controller;

import com.miempresa.workforce_form.model.AttendanceRequest;
import com.miempresa.workforce_form.repository.AttendanceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AttendanceRequestController {

    @Autowired
    private AttendanceRequestRepository repository;

    @PostMapping("/submit-attendance")
    public String submitAttendance(AttendanceRequest request, Model model) {
        repository.save(request);
        model.addAttribute("message", "✅ Your attendance request has been successfully submitted!");
        return "confirmation";
    }
}
