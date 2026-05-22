package com.miempresa.workforce_form.controller;

import com.miempresa.workforce_form.model.SupervisorGeneralRequest;
import com.miempresa.workforce_form.repository.SupervisorGeneralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/supervisor")
public class SupervisorGeneralController {

    @Autowired
    private SupervisorGeneralRepository repository;

    // =====================================================
    // 🔹 Mostrar formulario
    // =====================================================
    @GetMapping({"/general", "/general-request"})
    public String showGeneralForm() {
        return "supervisor-general"; // archivo en templates/
    }

    // =====================================================
    // 🔹 Procesar formulario (AJAX)
    // =====================================================
    @PostMapping("/submit-general")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> submitGeneral(@ModelAttribute SupervisorGeneralRequest request) {
        request.setCreatedAt(LocalDateTime.now());
        request.setStatus("Pending");
        repository.save(request);

        Map<String, Object> response = new HashMap<>();
        response.put("ok", true);
        response.put("message", "Supervisor general request submitted successfully!");

        return ResponseEntity.ok(response);
    }
}

