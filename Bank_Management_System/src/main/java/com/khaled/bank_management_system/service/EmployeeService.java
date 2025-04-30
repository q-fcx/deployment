package com.khaled.bank_management_system.service;

import com.khaled.bank_management_system.DTO.EmployeeDTO;
import com.khaled.bank_management_system.api.ApiException;
import com.khaled.bank_management_system.model.Employee;
import com.khaled.bank_management_system.model.User;
import com.khaled.bank_management_system.repository.AuthRepository;
import com.khaled.bank_management_system.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AuthRepository authRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public void addEmployee(EmployeeDTO employeeDTO) {
        User user = new User();
        user.setName(employeeDTO.getName());
        user.setRole("EMPLOYEE");

        String hashPassword = new BCryptPasswordEncoder().encode(employeeDTO.getPassword());
        user.setPassword(hashPassword);
        user.setUsername(employeeDTO.getUsername());
        user.setEmail(employeeDTO.getEmail());

        Employee employee = new Employee(null, employeeDTO.getPosition(), employeeDTO.getSalary(), user);
        user.setEmployee(employee);
        employeeRepository.save(employee);
        authRepository.save(user);
    }

    public void updateEmployee(Integer employeeId, EmployeeDTO employeeDTO) {
        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if(employee == null) throw new ApiException("Employee not found");

        User user = authRepository.findUserById(employeeId);
        user.setName(employeeDTO.getName());
        user.setUsername(employeeDTO.getUsername());
        user.setEmail(employeeDTO.getEmail());
        String hashPassword = new BCryptPasswordEncoder().encode(employeeDTO.getPassword());
        user.setPassword(hashPassword);
        employee.setPosition(employeeDTO.getPosition());
        employee.setSalary(employeeDTO.getSalary());

        authRepository.save(user);
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Integer employeeId){
        Employee employee = employeeRepository.findEmployeeById(employeeId);
        if(employee == null) throw new ApiException("employee not found");

        employeeRepository.delete(employee);
    }
}
