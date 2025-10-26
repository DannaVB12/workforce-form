package com.miempresa.workforce_form.controller;

import com.miempresa.workforce_form.model.SupervisorScheduleRequest;
import com.miempresa.workforce_form.repository.SupervisorScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/supervisor")
public class SupervisorScheduleController {

    @Autowired
    private SupervisorScheduleRepository repository;

    @GetMapping("/schedule-form")
    public String showScheduleForm() {
        return "supervisor-schedule";
    }

    @PostMapping("/submit-schedule-form")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> submitSupervisorSchedule(
            @ModelAttribute SupervisorScheduleRequest request) {

        request.setCreatedAt(LocalDateTime.now());
        request.setStatus("Pending");
        repository.save(request);

        Map<String, Object> response = new HashMap<>();
        response.put("ok", true);
        response.put("message", "Schedule change request submitted successfully!");

        return ResponseEntity.ok(response);
    }
}
