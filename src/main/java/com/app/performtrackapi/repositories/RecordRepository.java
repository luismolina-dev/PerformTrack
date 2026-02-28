package com.app.performtrackapi.repositories;

import com.app.performtrackapi.entities.Record;
import com.app.performtrackapi.entities.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecordRepository extends JpaRepository<Record, UUID> {
    Record findByEmployeeId(UUID employeeId);

    Page<Record> findByPeriod(String period, Status status, Pageable pageable);

    Page<Record> findByStatus(Status status, Pageable pageable);
}
