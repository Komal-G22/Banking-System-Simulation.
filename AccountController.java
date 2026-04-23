package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Account;
import com.example.demo.service.BankService;

@Controller
public class AccountController {

    private final BankService bankService;

    public AccountController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping("/createAccount")
    public String createAccount(
            @RequestParam String name,
            @RequestParam int pin,
            @RequestParam double balance,
            Model model) {

        Account acc = bankService.createAccount(name, pin, balance);
        model.addAttribute("account", acc);
        return "success";
    }
}
