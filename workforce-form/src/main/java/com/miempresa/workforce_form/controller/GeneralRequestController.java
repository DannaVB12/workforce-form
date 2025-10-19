package com.miempresa.workforce_form.controller;

import com.miempresa.workforce_form.model.GeneralRequest;
import com.miempresa.workforce_form.repository.GeneralRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GeneralRequestController {

    @Autowired
    private GeneralRequestRepository generalRequestRepository;

    // Mostrar formulario
    @GetMapping("/agent/general")
    public String showGeneralRequestForm() {
        return "agent-general";
    }

    // Guardar datos
    @PostMapping("/submit-general")
    public String submitGeneralRequest(GeneralRequest request, Model model) {
        request.setCreatedAt(java.time.LocalDateTime.now());
        generalRequestRepository.save(request);
        model.addAttribute("message", "Your General Request has been successfully submitted!");
        return "confirmation";
    }
}

