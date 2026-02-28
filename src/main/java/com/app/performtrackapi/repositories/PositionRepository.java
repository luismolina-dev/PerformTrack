package com.app.performtrackapi.repositories;

import com.app.performtrackapi.entities.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PositionRepository extends JpaRepository<Position, UUID> {
    Boolean existsByName(String name);
    Page<Position> findAllByDepartmentId(UUID departmentId, Pageable pageable);
}
