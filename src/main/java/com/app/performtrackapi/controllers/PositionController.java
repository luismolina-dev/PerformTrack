package com.app.performtrackapi.controllers;

import com.app.performtrackapi.dtos.Position.PositionDto;
import com.app.performtrackapi.dtos.Position.PositionResponseDto;
import com.app.performtrackapi.services.Position.PositionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<PositionResponseDto>> getAllPosition(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(positionService.getAllPosition(pageable));
    }

    @GetMapping("/{positionId}")
    public ResponseEntity<PositionResponseDto> getPositionById(@PathVariable UUID positionId) {
        return ResponseEntity.ok(positionService.getPositionById(positionId));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<Page<PositionResponseDto>> getAllPositionByDepartmentId(
            @PathVariable UUID departmentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return ResponseEntity.ok(positionService.getAllPositionByDepartmentId(departmentId, pageable));
    }

    @PostMapping("/")
    public ResponseEntity<PositionResponseDto> createPosition(@RequestBody PositionDto positionDto) {
        return new ResponseEntity<>(positionService.createPosition(positionDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{positionId}")
    public ResponseEntity<PositionResponseDto> updatePosition(@PathVariable UUID positionId,
            @RequestBody PositionDto positionDto) {
        return ResponseEntity.ok(positionService.updatePosition(positionId, positionDto));
    }

    @DeleteMapping("/{positionId}")
    public ResponseEntity<Void> deletePosition(@PathVariable UUID positionId) {
        positionService.deletePosition(positionId);
        return ResponseEntity.noContent().build();
    }

}
