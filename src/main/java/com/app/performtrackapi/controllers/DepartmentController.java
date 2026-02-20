package com.app.performtrackapi.controllers;

import com.app.performtrackapi.dtos.Department.DepartmentDto;
import com.app.performtrackapi.dtos.Department.DepartmentResponseDto;
import com.app.performtrackapi.services.Department.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    private final DepartmentService departmentService;
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentResponseDto> getDepartmentById(@PathVariable UUID departmentId){
        DepartmentResponseDto departmentDto = departmentService.getDepartmentById(departmentId);
        return ResponseEntity.ok(departmentDto);
    }

    @PostMapping
    public ResponseEntity<DepartmentResponseDto> createDepartment(@RequestBody DepartmentDto departmentDto){
        return new ResponseEntity<>(departmentService.createDepartment(departmentDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{departmentId}")
    public ResponseEntity<DepartmentResponseDto> updateDepartment(@PathVariable UUID departmentId, @RequestBody DepartmentDto departmentDto){
        return ResponseEntity.ok(departmentService.updateDepartment(departmentId, departmentDto));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentResponseDto>> getAllDepartments(){
        List<DepartmentResponseDto> departments = departmentService.getAllDepartments();
        if(departments.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(departments);
    }

    @DeleteMapping("/{departmentId}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable UUID departmentId){
        departmentService.deleteDepartment(departmentId);
        return ResponseEntity.noContent().build();
    }
}
