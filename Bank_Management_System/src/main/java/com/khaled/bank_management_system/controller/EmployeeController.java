package com.khaled.bank_management_system.controller;

import com.khaled.bank_management_system.DTO.EmployeeDTO;
import com.khaled.bank_management_system.api.ApiResponse;
import com.khaled.bank_management_system.model.Employee;
import com.khaled.bank_management_system.model.User;
import com.khaled.bank_management_system.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/get-employees")
    public ResponseEntity getAllEmployees() {
        return ResponseEntity.ok().body(employeeService.getAllEmployees());
    }

    @PostMapping("/add-employee")
    public ResponseEntity addEmployee(@RequestBody @Valid EmployeeDTO employeeDTO) {
        employeeService.addEmployee(employeeDTO);
        return ResponseEntity.ok().body(new ApiResponse("Employee added"));
    }

    @PutMapping("/update-employee")
    public ResponseEntity updateEmployee(@AuthenticationPrincipal User user, @RequestBody @Valid EmployeeDTO employeeDTO) {
        employeeService.updateEmployee(user.getId(), employeeDTO);
        return ResponseEntity.ok().body(new ApiResponse("Employee updated"));
    }

    @DeleteMapping("/delete-employee")
    public ResponseEntity deleteEmployee(@AuthenticationPrincipal User user) {
        employeeService.deleteEmployee(user.getId());
        return ResponseEntity.ok().body(new ApiResponse("Employee deleted"));
    }
}
