package com.miempresa.workforce_form.repository;

import com.miempresa.workforce_form.model.AgentAttendanceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AgentAttendanceRepository extends JpaRepository<AgentAttendanceRequest, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE AgentAttendanceRequest r SET r.status = :status WHERE r.id = :id")
    void updateStatusById(Long id, String status);
}
