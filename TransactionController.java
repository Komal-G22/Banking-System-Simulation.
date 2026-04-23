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
public class TransactionController {

    private final BankService bankService;

    public TransactionController(BankService bankService) {
        this.bankService = bankService;
    }

    // ================= DEPOSIT =================
    @PostMapping("/deposit")
    public String deposit(
            @RequestParam double amount,
            HttpSession session,
            Model model) {

        Integer accNo = (Integer) session.getAttribute("accountNumber");
        if (accNo == null) {
            return "redirect:/";
        }

        try {
            bankService.deposit(accNo, amount);

            Account acc = bankService.getAccount(accNo); // ✅ fresh account
            model.addAttribute("account", acc);
            model.addAttribute("message", "Amount deposited successfully");

        } catch (RuntimeException e) {
            Account acc = bankService.getAccount(accNo);
            model.addAttribute("account", acc);
            model.addAttribute("error", e.getMessage());
        }

        return "dashboard"; // ✅ always dashboard
    }

    // ================= WITHDRAW =================
    @PostMapping("/withdraw")
    public String withdraw(
            @RequestParam double amount,
            HttpSession session,
            Model model) {

        Integer accNo = (Integer) session.getAttribute("accountNumber");
        if (accNo == null) {
            return "redirect:/";
        }

        try {
            bankService.withdraw(accNo, amount);

            Account acc = bankService.getAccount(accNo); // ✅ fresh account
            model.addAttribute("account", acc);
            model.addAttribute("message", "Amount withdrawn successfully");

        } catch (RuntimeException e) {
            Account acc = bankService.getAccount(accNo);
            model.addAttribute("account", acc);
            model.addAttribute("error", e.getMessage());
        }

        return "dashboard"; // ✅ error bhi dashboard pe
    }

    // ================= TRANSACTION HISTORY =================
    @GetMapping("/transactions")
    public String viewTransactions(HttpSession session, Model model) {

        Integer accNo = (Integer) session.getAttribute("accountNumber");
        if (accNo == null) {
            return "redirect:/";
        }

        model.addAttribute("transactions",
                bankService.getTransactions(accNo));

        return "transactionHistory"; // ✅ matches transactionHistory.html
    }
    @GetMapping("/dashboard")
public String dashboard(HttpSession session, Model model) {

    Integer accNo = (Integer) session.getAttribute("accountNumber");

    if (accNo == null) {
        return "redirect:/";
    }

    Account acc = bankService.getAccount(accNo);

    if (acc == null) {
        model.addAttribute("error", "Account not found");
        return "error";
    }

    model.addAttribute("account", acc);
    return "dashboard";
}
}
