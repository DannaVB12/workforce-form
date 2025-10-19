package com.miempresa.workforce_form.repository;

import com.miempresa.workforce_form.model.AttendanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRequestRepository extends JpaRepository<AttendanceRequest, Long> {
}

