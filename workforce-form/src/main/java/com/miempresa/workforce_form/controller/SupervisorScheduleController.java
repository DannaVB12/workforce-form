package com.miempresa.workforce_form.controller;

import com.miempresa.workforce_form.model.SupervisorScheduleRequest;
import com.miempresa.workforce_form.repository.SupervisorScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/supervisor")
public class SupervisorScheduleController {

    @Autowired
    private SupervisorScheduleRepository repository;

    @PostMapping("/submit-schedule")
    public String submitSupervisorSchedule(SupervisorScheduleRequest request,
                                           RedirectAttributes redirectAttributes) {

        // Guardar en la base de datos
        repository.save(request);

        // Mensaje de confirmación visual
        redirectAttributes.addFlashAttribute("message",
                "✅ Schedule change request submitted successfully!");

        // Redirigir de nuevo al formulario
        return "redirect:/supervisor/schedule";
    }
}
