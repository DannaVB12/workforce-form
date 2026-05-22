package com.miempresa.workforce_form.controller;

import com.miempresa.workforce_form.model.TrainerGeneralRequest;
import com.miempresa.workforce_form.repository.TrainerGeneralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TrainerGeneralController {

    @Autowired
    private TrainerGeneralRepository repository;

    // === Mostrar formulario ===
    @GetMapping("/trainer-general")
    public String showTrainerGeneralForm() {
        return "trainer-general"; // archivo en templates/
    }

    // === Guardar solicitud ===
    @PostMapping("/submit-trainer-general")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> submitTrainerGeneral(@ModelAttribute TrainerGeneralRequest request) {
        request.setCreatedAt(LocalDateTime.now());
        request.setStatus("Pending");
        repository.save(request);

        Map<String, Object> response = new HashMap<>();
        response.put("ok", true);
        response.put("message", "Trainer general request submitted successfully!");
        return ResponseEntity.ok(response);
    }
}

