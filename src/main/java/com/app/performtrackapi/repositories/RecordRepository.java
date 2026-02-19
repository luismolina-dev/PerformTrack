package com.app.performtrackapi.repositories;

import com.app.performtrackapi.entities.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RecordRepository extends JpaRepository<Record, UUID> {
    Record findByEmployeeId(UUID employeeId);
    List<Record> findByPeriod(String period);
}
