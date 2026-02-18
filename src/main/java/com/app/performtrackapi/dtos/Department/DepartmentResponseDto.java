package com.app.performtrackapi.dtos.Department;

import com.app.performtrackapi.dtos.Sub_department.SubDepartmentResponseListDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentResponseDto {
    private UUID id;
    private String name;
    private List<SubDepartmentResponseListDto> subDepartments;
}
