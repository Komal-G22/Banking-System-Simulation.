package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Account;
import com.example.demo.service.BankService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final BankService bankService;

    public LoginController(BankService bankService) {
        this.bankService = bankService;
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public String login(
            @RequestParam int accountNumber,
            @RequestParam int pin,
            HttpSession session,
            Model model) {

        try {
            Account acc = bankService.login(accountNumber, pin);

            // store account number in session
            session.setAttribute("accountNumber", acc.getAccountNumber());

            model.addAttribute("account", acc);
            return "dashboard";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    // ================= LOGOUT =================
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();   // clear session
        return "redirect:/";    // go back to home page
    }
}
