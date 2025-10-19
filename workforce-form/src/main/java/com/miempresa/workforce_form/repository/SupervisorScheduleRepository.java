package com.miempresa.workforce_form.repository;

import com.miempresa.workforce_form.model.SupervisorScheduleRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupervisorScheduleRepository extends JpaRepository<SupervisorScheduleRequest, Long> {
}
