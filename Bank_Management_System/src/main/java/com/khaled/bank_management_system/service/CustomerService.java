package com.khaled.bank_management_system.service;

import com.khaled.bank_management_system.DTO.CustomerDTO;
import com.khaled.bank_management_system.api.ApiException;
import com.khaled.bank_management_system.model.Customer;
import com.khaled.bank_management_system.model.User;
import com.khaled.bank_management_system.repository.AuthRepository;
import com.khaled.bank_management_system.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void addCustomer(CustomerDTO customerDTO) {
        User user = new User();
        user.setName(customerDTO.getName());
        user.setRole("CUSTOMER");

        String hashPassword = new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        user.setPassword(hashPassword);
        user.setUsername(customerDTO.getUsername());
        user.setEmail(customerDTO.getEmail());

        Customer customer = new Customer(null, customerDTO.getPhoneNumber(), user, null);
        user.setCustomer(customer);
        authRepository.save(user);
        customerRepository.save(customer);
    }

    public void updateCustomer(Integer customerId, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if(customer == null) throw new ApiException("Customer not found");
        User user = authRepository.findUserById(customerId);
        user.setUsername(customerDTO.getUsername());
        user.setEmail(customerDTO.getEmail());
        user.setName(customerDTO.getName());
        String hashPassword = new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        user.setPassword(hashPassword);
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        authRepository.save(user);
        customerRepository.save(customer);
    }

    public void deleteCustomer(Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if(customer == null) throw new ApiException("Customer not found");
        customerRepository.delete(customer);
    }
}
