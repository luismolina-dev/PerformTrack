package com.app.performtrackapi.services.Employee;

import com.app.performtrackapi.dtos.Employee.EmployeeDto;
import com.app.performtrackapi.dtos.Employee.EmployeeResponseDto;
import com.app.performtrackapi.entities.Employee;
import com.app.performtrackapi.entities.Position;
import com.app.performtrackapi.entities.User;
import com.app.performtrackapi.exceptions.BadRequestException;
import com.app.performtrackapi.mappers.EmployeeMapper;
import com.app.performtrackapi.repositories.EmployeeRepository;
import com.app.performtrackapi.repositories.PositionRepository;
import com.app.performtrackapi.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final UserRepository userRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, PositionRepository positionRepository, UserRepository userRepository) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.positionRepository = positionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public EmployeeResponseDto getEmployeeById(UUID id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Employee not found")
                );

        return employeeMapper.toResponseDto(employee);
    }

    @Override
    public EmployeeResponseDto getEmployeeByPositionId(UUID positionId) {
        if (!positionRepository.existsById(positionId)) {
            throw new BadRequestException("Position not found");
        }

        Employee employee = employeeRepository.findByPositionId(positionId);

        return employeeMapper.toResponseDto(employee);
    }

    @Override
    public List<EmployeeResponseDto> getAllEmployee() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toResponseDto).toList();
    }

    @Override
    public EmployeeResponseDto createEmployee(EmployeeDto employeeDto) {

        Employee employee = employeeMapper.toEntity(employeeDto);

        employee.setName(employeeDto.getName());

        employee.setFirst_year(employee.getFirst_year());

        Position position = positionRepository.findById(employeeDto.getPositionId())
                .orElseThrow(() -> new BadRequestException("Position not found"));
        employee.setPosition(position);

        if (employeeDto.getUserId() != null) {
            User user = userRepository.findById(employeeDto.getUserId())
                    .orElseThrow(() -> new BadRequestException("User not found"));
            employee.setUser(user);
        }

        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.toResponseDto(savedEmployee);
    }

    @Override
    public EmployeeResponseDto updateEmployee(UUID id, EmployeeDto employeeDto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Employee not found")
                );

        if (employeeDto.getName() != null) {
            employee.setName(employeeDto.getName());
        }

        if (employeeDto.getFirst_year() != null) {
            employee.setFirst_year(employeeDto.getFirst_year());
        }

        if (employeeDto.getPositionId() != null) {
            Position position = positionRepository.findById(employeeDto.getPositionId())
                    .orElseThrow(() -> new BadRequestException("Position not found"));
            employee.setPosition(position);
        }

        if (employeeDto.getUserId() != null) {
            User user = userRepository.findById(employeeDto.getUserId())
                    .orElseThrow(() -> new BadRequestException("User not found"));
            employee.setUser(user);
        }

        Employee updatedEmployee = employeeRepository.save(employee);

        return employeeMapper.toResponseDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(UUID id) {
        if (!employeeRepository.existsById(id)) {
            throw new BadRequestException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }
}
