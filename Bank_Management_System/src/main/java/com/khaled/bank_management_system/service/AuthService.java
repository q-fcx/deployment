package com.khaled.bank_management_system.service;

import com.khaled.bank_management_system.DTO.CustomerDTO;
import com.khaled.bank_management_system.DTO.EmployeeDTO;
import com.khaled.bank_management_system.api.ApiException;
import com.khaled.bank_management_system.model.Customer;
import com.khaled.bank_management_system.model.Employee;
import com.khaled.bank_management_system.model.User;
import com.khaled.bank_management_system.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;


}
