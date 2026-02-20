package com.app.performtrackapi.services.Department;

import com.app.performtrackapi.dtos.Department.DepartmentDto;
import com.app.performtrackapi.dtos.Department.DepartmentResponseDto;

import java.util.List;
import java.util.UUID;

public interface DepartmentService {
    DepartmentResponseDto getDepartmentById(UUID id);
    DepartmentResponseDto createDepartment(DepartmentDto departmentDto);
    DepartmentResponseDto updateDepartment(UUID id, DepartmentDto departmentDto);
    List<DepartmentResponseDto> getAllDepartments();
    void deleteDepartment(UUID id);
}
