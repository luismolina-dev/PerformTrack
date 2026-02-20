package com.app.performtrackapi.services.Sub_department;
import com.app.performtrackapi.dtos.Sub_department.SubDepartmentDto;
import com.app.performtrackapi.dtos.Sub_department.SubDepartmentResponseDto;
import com.app.performtrackapi.entities.Department;
import com.app.performtrackapi.entities.Sub_department;
import com.app.performtrackapi.exceptions.BadRequestException;
import com.app.performtrackapi.exceptions.ResourceNotFound;
import com.app.performtrackapi.mappers.SubDepartmentMapper;
import com.app.performtrackapi.repositories.DepartmentRepository;
import com.app.performtrackapi.repositories.SubDepartmentRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class Sub_departmentServiceImpl implements Sub_departmentService {

    private final SubDepartmentRepository subDepartmentRepository;
    private final SubDepartmentMapper subDepartmentMapper;
    private final DepartmentRepository departmentRepository;


    public Sub_departmentServiceImpl(SubDepartmentRepository subDepartmentRepository, SubDepartmentMapper subDepartmentMapper, DepartmentRepository departmentRepository) {
        this.subDepartmentRepository = subDepartmentRepository;
        this.subDepartmentMapper = subDepartmentMapper;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public SubDepartmentResponseDto getSubDepartmentById(UUID id) {
        Sub_department subDepartment = subDepartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Sub department not found")
                );

        return subDepartmentMapper.toResponseDto(subDepartment);
    }

    @Override
    public SubDepartmentResponseDto getSubDepartmentByDepartmentId(UUID departmentId) {

        if (!departmentRepository.existsById(departmentId)) {
            throw new ResourceNotFound("Department not found");
        }

        Sub_department subDepartment = subDepartmentRepository.findByDepartmentId(departmentId);
        return subDepartmentMapper.toResponseDto(subDepartment);
    }

    @Override
    public SubDepartmentResponseDto createSubDepartment(@NonNull SubDepartmentDto subDepartmentDto) {

        if (subDepartmentRepository.existsByName(subDepartmentDto.getName())) {
            throw new BadRequestException("Sub department already exists");
        }

        Department department = departmentRepository.findById(subDepartmentDto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFound("Department not found"));

        Sub_department subDepartment = subDepartmentMapper.toEntity(subDepartmentDto);
        subDepartment.setDepartment(department);

        Sub_department savedSubDepartment = subDepartmentRepository.save(subDepartment);

        return subDepartmentMapper.toResponseDto(savedSubDepartment);
    }

    @Override
    public SubDepartmentResponseDto updateSubDepartment(UUID id, @NonNull SubDepartmentDto subDepartmentDto) {
        Sub_department subDepartment = subDepartmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Sub department not found")
                );

        if (subDepartmentDto.getName() != null) {
            subDepartment.setName(subDepartmentDto.getName());
        }

        if (subDepartmentDto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(subDepartmentDto.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFound("Department not found"));
            subDepartment.setDepartment(department);
        }

        Sub_department updatedSubDepartment = subDepartmentRepository.save(subDepartment);

        return subDepartmentMapper.toResponseDto(updatedSubDepartment);
    }

    @Override
    public List<SubDepartmentResponseDto> getAllSubDepartments() {
        return subDepartmentRepository.findAll()
                .stream()
                .map(subDepartmentMapper::toResponseDto).toList();
    }

    @Override
    public void deleteSubDepartment(UUID id) {
        if (!subDepartmentRepository.existsById(id)) {
            throw new ResourceNotFound("Sub department not found");
        }

        subDepartmentRepository.deleteById(id);
    }
}
