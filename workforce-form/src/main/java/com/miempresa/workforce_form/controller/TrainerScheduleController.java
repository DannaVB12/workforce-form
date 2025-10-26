package com.miempresa.workforce_form.controller;

import com.miempresa.workforce_form.model.TrainerScheduleRequest;
import com.miempresa.workforce_form.repository.TrainerScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TrainerScheduleController {

    @Autowired
    private TrainerScheduleRepository repository;

    // === Mostrar formulario ===
    @GetMapping("/trainer-schedule")
    public String showTrainerScheduleForm() {
        return "trainer-schedule"; // se llama igual que el HTML
    }

    // === Guardar solicitud ===
    @PostMapping("/submit-trainer-schedule")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> submitTrainerSchedule(@ModelAttribute TrainerScheduleRequest request) {
        request.setCreatedAt(LocalDateTime.now());
        request.setStatus("Pending");
        repository.save(request);

        Map<String, Object> response = new HashMap<>();
        response.put("ok", true);
        response.put("message", "Trainer schedule change request submitted successfully!");
        return ResponseEntity.ok(response);
    }
}
