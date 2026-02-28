package com.app.performtrackapi.services.Employee;

import com.app.performtrackapi.dtos.Employee.EmployeeDto;
import com.app.performtrackapi.dtos.Employee.EmployeeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EmployeeService {
    EmployeeResponseDto getEmployeeById(UUID id);
    EmployeeResponseDto getEmployeeByPositionId(UUID positionId);
    Page<EmployeeResponseDto> getAllEmployee(Pageable pageable);
    EmployeeResponseDto createEmployee(EmployeeDto employeeDto);
    EmployeeResponseDto updateEmployee(UUID id, EmployeeDto employeeDto);
    void deleteEmployee(UUID id);
}
