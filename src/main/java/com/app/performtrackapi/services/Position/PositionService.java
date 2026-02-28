package com.app.performtrackapi.services.Position;

import com.app.performtrackapi.dtos.Position.PositionDto;
import com.app.performtrackapi.dtos.Position.PositionResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PositionService {
    PositionResponseDto getPositionById(UUID id);

    Page<PositionResponseDto> getAllPosition(Pageable pageable);

    Page<PositionResponseDto> getAllPositionByDepartmentId(UUID departmentId, Pageable pageable);

    PositionResponseDto createPosition(PositionDto positionDto);

    PositionResponseDto updatePosition(UUID id, PositionDto positionDto);

    void deletePosition(UUID id);
}
