package com.app.performtrackapi.services.Department;

import com.app.performtrackapi.dtos.Department.DepartmentDto;
import com.app.performtrackapi.dtos.Department.DepartmentResponseDto;
import com.app.performtrackapi.entities.Department;
import com.app.performtrackapi.exceptions.BadRequestException;
import com.app.performtrackapi.exceptions.ResourceNotFound;
import com.app.performtrackapi.mappers.DepartmentMapper;
import com.app.performtrackapi.repositories.DepartmentRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    @Override
    public DepartmentResponseDto getDepartmentById(UUID id) {

        Department department = departmentRepository.findById(id).
                orElseThrow(() -> new ResourceNotFound("Department not found")
                );

        return departmentMapper.toResponseDto(department);
    }

    @Override
    public DepartmentResponseDto createDepartment(@NonNull DepartmentDto departmentDto) {

        if (departmentRepository.existsByName(departmentDto.getName())) {
            throw new BadRequestException("Department already exists");
        }

        Department department = departmentMapper.toEntity(departmentDto);

        Department savedDepartment = departmentRepository.save(department);

        return departmentMapper.toResponseDto(savedDepartment);
    }

    @Override
    public DepartmentResponseDto updateDepartment(UUID id, @NonNull DepartmentDto departmentDto) {

        Department department = departmentRepository.findById(id).
                orElseThrow(() -> new ResourceNotFound("Department not found")
                );

        if (departmentDto.getName() != null) {
            department.setName(departmentDto.getName());
        }

        Department updatedDepartment = departmentRepository.save(department);

        return departmentMapper.toResponseDto(updatedDepartment);
    }

    @Override
    public List<DepartmentResponseDto> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(departmentMapper::toResponseDto).toList();
    }

    @Override
    public void deleteDepartment(UUID id) {
        if (!departmentRepository.existsById(id)) {
            throw new ResourceNotFound("Department not found");
        }
        departmentRepository.deleteById(id);
    }
}
