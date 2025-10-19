package com.miempresa.workforce_form.repository;

import com.miempresa.workforce_form.model.SupervisorAttendanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupervisorAttendanceRepository extends JpaRepository<SupervisorAttendanceRequest, Long> {
}


