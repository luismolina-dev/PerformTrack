package com.app.performtrackapi.dtos.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDto {
    private UUID id;
    private UUID positionId;
    private UUID userId;
    private String name;
    private Boolean first_year;
}
