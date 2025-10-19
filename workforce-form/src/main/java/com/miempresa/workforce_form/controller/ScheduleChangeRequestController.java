package com.miempresa.workforce_form.controller;

import com.miempresa.workforce_form.model.ScheduleChangeRequest;
import com.miempresa.workforce_form.repository.ScheduleChangeRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class ScheduleChangeRequestController {

    @Autowired
    private ScheduleChangeRequestRepository repository;

    @PostMapping("/submit-schedule")
    public String submitScheduleChange(ScheduleChangeRequest request, Model model) {

        // Establecer la fecha y hora actuales
        request.setCreatedAt(LocalDateTime.now());

        // Guardar en la base de datos
        repository.save(request);

        // Enviar mensaje de confirmación
        model.addAttribute("message", "Your Schedule Change Request has been successfully submitted!");
        return "confirmation";
    }
}


