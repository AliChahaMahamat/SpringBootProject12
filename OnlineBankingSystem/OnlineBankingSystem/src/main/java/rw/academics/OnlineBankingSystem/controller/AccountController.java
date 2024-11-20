package rw.academics.OnlineBankingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import rw.academics.OnlineBankingSystem.model.Account;
import rw.academics.OnlineBankingSystem.service.AccountService;

import java.util.List;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts")
    public String viewAccounts(Model model) {
        List<Account> accounts = accountService.getAllAccountsForUser();
        model.addAttribute("accounts", accounts);
        return "index";
    }
}
