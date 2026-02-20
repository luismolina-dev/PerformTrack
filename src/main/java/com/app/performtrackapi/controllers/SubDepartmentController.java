package com.app.performtrackapi.controllers;

import com.app.performtrackapi.dtos.Sub_department.SubDepartmentDto;
import com.app.performtrackapi.dtos.Sub_department.SubDepartmentResponseDto;
import com.app.performtrackapi.services.Sub_department.Sub_departmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/sub-department")
public class SubDepartmentController {

    private final Sub_departmentService sub_departmentService;
    public SubDepartmentController(Sub_departmentService sub_departmentService) {
        this.sub_departmentService = sub_departmentService;
    }

    @GetMapping("/{subDepartmentId}")
    public ResponseEntity<SubDepartmentResponseDto> getById(@PathVariable UUID subDepartmentId){
        SubDepartmentResponseDto subDepartmentDto = sub_departmentService.getSubDepartmentById(subDepartmentId);
        return ResponseEntity.ok(subDepartmentDto);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<SubDepartmentResponseDto> getByDepartmentId(@PathVariable UUID departmentId){
        SubDepartmentResponseDto subDepartmentDto = sub_departmentService.getSubDepartmentByDepartmentId(departmentId);
        return ResponseEntity.ok(subDepartmentDto);
    }

    @PostMapping("/")
    public ResponseEntity<SubDepartmentResponseDto> createSubDepartment(@RequestBody SubDepartmentDto subDepartmentDto){
        return new ResponseEntity<>(sub_departmentService.createSubDepartment(subDepartmentDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{subDepartmentId}")
    public ResponseEntity<SubDepartmentResponseDto> updateSubDepartment(@PathVariable UUID subDepartmentId, @RequestBody SubDepartmentDto subDepartmentDto){
        return ResponseEntity.ok(sub_departmentService.updateSubDepartment(subDepartmentId, subDepartmentDto));
    }

    @GetMapping("/")
    public ResponseEntity<List<SubDepartmentResponseDto>> getAllSubDepartments(){
        List<SubDepartmentResponseDto> subDepartments = sub_departmentService.getAllSubDepartments();
        if(subDepartments.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(subDepartments);
    }

    @DeleteMapping("/{subDepartmentId}")
    public ResponseEntity<Void> deleteSubDepartment(@PathVariable UUID subDepartmentId){
        sub_departmentService.deleteSubDepartment(subDepartmentId);
        return ResponseEntity.noContent().build();
    }
}
