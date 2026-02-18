package com.app.performtrackapi.dtos.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private UUID positionId;
    private String name;
    private UUID userId;
    private Boolean first_year;
}
