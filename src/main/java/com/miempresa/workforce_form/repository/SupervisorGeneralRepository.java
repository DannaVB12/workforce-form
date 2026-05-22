package com.miempresa.workforce_form.repository;

import com.miempresa.workforce_form.model.SupervisorGeneralRequest;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SupervisorGeneralRepository extends JpaRepository<SupervisorGeneralRequest, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE SupervisorGeneralRequest r SET r.status = :status WHERE r.id = :id")
    void updateStatusById(Long id, String status);
}
