package com.app.performtrackapi.repositories;

import com.app.performtrackapi.entities.Record;
import com.app.performtrackapi.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecordRepository extends JpaRepository<Record, UUID> {
    Record findByEmployeeId(UUID employeeId);

    List<Record> findByPeriod(String period, Status status);

    List<Record> findByStatus(Status status);

    List<Record> findByEmployee_Position_Department_IdAndPeriodAndStatus(UUID departmentId, String period,
            Status status);
}
