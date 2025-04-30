package com.khaled.bank_management_system.controller;

import com.khaled.bank_management_system.api.ApiResponse;
import com.khaled.bank_management_system.model.Account;
import com.khaled.bank_management_system.model.User;
import com.khaled.bank_management_system.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;


    @GetMapping("/get-all-accounts")
    public ResponseEntity getAllAccounts() {
        return ResponseEntity.ok().body(accountService.getAllAccounts());
    }

    @GetMapping("/get-my-accounts")
    public ResponseEntity getMyAccounts(@AuthenticationPrincipal User user){
        return ResponseEntity.ok().body(accountService.getMyAccounts(user.getId()));
    }

    @PostMapping("/add-account")
    public ResponseEntity addAccount(@AuthenticationPrincipal User user, @RequestBody @Valid Account account){
        accountService.addAccount(user.getId(),account);
        return ResponseEntity.ok().body(new ApiResponse("Account added"));
    }


    @PutMapping("/update-account/{accountId}")
    public ResponseEntity updateAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId,@RequestBody @Valid Account account){
        accountService.updateAccount(user.getId(),accountId,account);
        return ResponseEntity.ok().body(new ApiResponse("Account updated"));
    }


    @DeleteMapping("/delete-account/{accountId}")
    public ResponseEntity deleteAccount(@AuthenticationPrincipal User user,@PathVariable Integer accountId){
        accountService.deleteAccount(user.getId(),accountId);

        return ResponseEntity.ok().body(new ApiResponse("Account deleted"));

    }


    @GetMapping("view/{accountId}")
    public ResponseEntity ViewAccountDetails(@AuthenticationPrincipal User user,@PathVariable Integer accountId){
        return ResponseEntity.ok().body(accountService.viewAccountDetails(user.getId(),accountId));
    }


    @PostMapping("active-account/{accountId}")
    public ResponseEntity ActiveBankAccount(@AuthenticationPrincipal User user,@PathVariable Integer accountId){
        accountService.activeAccount(user.getId(),accountId);
        return ResponseEntity.ok().body(new ApiResponse("Account activated"));
    }


    @PostMapping("deposit/{accountId}/{amount}")
    public ResponseEntity deposit(@AuthenticationPrincipal User user,@PathVariable Integer accountId,@PathVariable Double amount){
        accountService.deposit(user.getId(),accountId,amount);
        return ResponseEntity.ok().body(new ApiResponse("money deposited successfully"));
    }


    @PostMapping("withdraw/{accountId}/{amount}")
    public ResponseEntity withDraw(@AuthenticationPrincipal User user,@PathVariable Integer accountId,@PathVariable Double amount){
        accountService.withDraw(user.getId(),accountId,amount);
        return ResponseEntity.ok().body(new ApiResponse("withdraw successfully"));
    }

    @PostMapping("/transfer/{account1Id}/{account2Id}/{amount}")
    public ResponseEntity transferFundsBetweenAccounts(@AuthenticationPrincipal User user,@PathVariable Integer account1Id,@PathVariable Integer account2Id,@PathVariable Double amount){
        accountService.transferBetweenAccounts (user.getId(),account1Id, account2Id, amount);
        return ResponseEntity.ok().body(new ApiResponse("Amount transferred successfully"));

    }

    @PostMapping("/block-account/{accountId}")
    public ResponseEntity blockAccount(@AuthenticationPrincipal User user, @PathVariable Integer accountId){
        accountService.blockAccount(user.getId(), accountId);
        return ResponseEntity.ok().body(new ApiResponse("Account Blocked successfully"));
    }
}