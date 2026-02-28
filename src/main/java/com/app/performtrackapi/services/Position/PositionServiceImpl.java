package com.app.performtrackapi.services.Position;

import com.app.performtrackapi.dtos.Position.PositionDto;
import com.app.performtrackapi.dtos.Position.PositionResponseDto;
import com.app.performtrackapi.entities.Department;
import com.app.performtrackapi.entities.Position;
import com.app.performtrackapi.entities.Sub_department;
import com.app.performtrackapi.exceptions.BadRequestException;
import com.app.performtrackapi.mappers.PositionMapper;
import com.app.performtrackapi.repositories.DepartmentRepository;
import com.app.performtrackapi.repositories.PositionRepository;
import com.app.performtrackapi.repositories.SubDepartmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;
    private final DepartmentRepository departmentRepository;
    private final SubDepartmentRepository subDepartmentRepository;

    public PositionServiceImpl(PositionRepository positionRepository, PositionMapper positionMapper,
            DepartmentRepository departmentRepository, SubDepartmentRepository subDepartmentRepository) {
        this.positionRepository = positionRepository;
        this.positionMapper = positionMapper;
        this.departmentRepository = departmentRepository;
        this.subDepartmentRepository = subDepartmentRepository;
    }

    @Override
    public PositionResponseDto getPositionById(UUID id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Position not found"));

        return positionMapper.toResponseDto(position);
    }

    @Override
    public Page<PositionResponseDto> getAllPosition(Pageable pageable) {
        return positionRepository.findAll(pageable)
                .map(positionMapper::toResponseDto);
    }

    @Override
    public Page<PositionResponseDto> getAllPositionByDepartmentId(UUID departmentId, Pageable pageable) {

       if (!departmentRepository.existsById(departmentId)) {
           throw new BadRequestException("Department not found");
       }

        return positionRepository.findAllByDepartmentId(departmentId, pageable)
                .map(positionMapper::toResponseDto);
    }

    @Override
    public PositionResponseDto createPosition(PositionDto positionDto) {

        if (positionRepository.existsByName(positionDto.getName())) {
            throw new BadRequestException("Position already exists");
        }

        Position position = positionMapper.toEntity(positionDto);
        position.setName(positionDto.getName());

        Department department = departmentRepository.findById(positionDto.getDepartmentId())
                .orElseThrow(() -> new BadRequestException("Department not found"));
        position.setDepartment(department);

        if (positionDto.getSubDepartmentId() != null) {
            Sub_department subDepartment = subDepartmentRepository.findById(positionDto.getSubDepartmentId())
                    .orElseThrow(() -> new BadRequestException("Sub department not found"));
            position.setSubDepartment(subDepartment);
        } else {
            position.setSubDepartment(null);
        }

        Position savedPosition = positionRepository.save(position);

        return positionMapper.toResponseDto(savedPosition);
    }

    @Override
    public PositionResponseDto updatePosition(UUID id, PositionDto positionDto) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Position not found"));

        if (positionDto.getName() != null) {
            position.setName(positionDto.getName());
        }

        if (positionDto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(positionDto.getDepartmentId())
                    .orElseThrow(() -> new BadRequestException("Department not found"));
            position.setDepartment(department);
        }

        if (positionDto.getSubDepartmentId() != null) {
            Sub_department subDepartment = subDepartmentRepository.findById(positionDto.getSubDepartmentId())
                    .orElseThrow(() -> new BadRequestException("Sub department not found"));
            position.setSubDepartment(subDepartment);
        }

        Position updatedPosition = positionRepository.save(position);

        return positionMapper.toResponseDto(positionRepository.save(updatedPosition));
    }

    @Override
    public void deletePosition(UUID id) {

        if (!positionRepository.existsById(id)) {
            throw new BadRequestException("Position not found");
        }

        positionRepository.deleteById(id);
    }
}
