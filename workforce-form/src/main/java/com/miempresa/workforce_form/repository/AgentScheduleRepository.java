package com.miempresa.workforce_form.repository;

import com.miempresa.workforce_form.model.AgentScheduleRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AgentScheduleRepository extends JpaRepository<AgentScheduleRequest, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE AgentScheduleRequest a SET a.status = :status WHERE a.id = :id")
    void updateStatusById(Long id, String status);
}
