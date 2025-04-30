package com.khaled.bank_management_system.controller;

import com.khaled.bank_management_system.DTO.CustomerDTO;
import com.khaled.bank_management_system.api.ApiResponse;
import com.khaled.bank_management_system.model.Customer;
import com.khaled.bank_management_system.model.User;
import com.khaled.bank_management_system.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/get-customers")
    public ResponseEntity getAllCustomers() {
        return ResponseEntity.ok().body(customerService.getAllCustomers());
    }

    @PostMapping("/add-customer")
    public ResponseEntity addCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        customerService.addCustomer(customerDTO);
        return ResponseEntity.ok().body(new ApiResponse("Customer added"));
    }

    @PutMapping("/update-customer")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal Customer customer, @RequestBody @Valid CustomerDTO customerDTO) {
        customerService.updateCustomer(customer.getId(),customerDTO);
        return ResponseEntity.ok().body(new ApiResponse("Customer updated"));
    }

    @DeleteMapping("/delete-customer")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal Customer customer) {
        customerService.deleteCustomer(customer.getId());
        return ResponseEntity.ok().body(new ApiResponse("Customer deleted"));
    }
}
