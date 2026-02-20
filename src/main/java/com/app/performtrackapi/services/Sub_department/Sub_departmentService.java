package com.app.performtrackapi.services.Sub_department;

import com.app.performtrackapi.dtos.Sub_department.SubDepartmentDto;
import com.app.performtrackapi.dtos.Sub_department.SubDepartmentResponseDto;

import java.util.List;
import java.util.UUID;

public interface Sub_departmentService {
    SubDepartmentResponseDto getSubDepartmentById(UUID id);
    SubDepartmentResponseDto getSubDepartmentByDepartmentId(UUID departmentId);
    SubDepartmentResponseDto createSubDepartment(SubDepartmentDto subDepartmentDto);
    SubDepartmentResponseDto updateSubDepartment(UUID id, SubDepartmentDto subDepartmentDto);
    List<SubDepartmentResponseDto> getAllSubDepartments();
    void deleteSubDepartment(UUID id);
}
