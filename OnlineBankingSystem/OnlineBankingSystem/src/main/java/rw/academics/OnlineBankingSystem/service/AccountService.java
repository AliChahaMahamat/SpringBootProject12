package rw.academics.OnlineBankingSystem.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import rw.academics.OnlineBankingSystem.model.Account;

@Service
public class AccountService {

    public List<Account> getAllAccountsForUser() {
        // For demonstration, return mock data
        List<Account> accounts = new ArrayList<>();
        Account account1 = new Account();
        account1.setId(1L);
        account1.setType("Savings");
        account1.setBalance(new BigDecimal("1500.00"));
        account1.setRecentActivity("Deposit on 2024-10-25");
        accounts.add(account1);

        Account account2 = new Account();
        account2.setId(2L);
        account2.setType("Current");
        account2.setBalance(new BigDecimal("2500.00"));
        account2.setRecentActivity("Withdrawal on 2024-10-24");
        accounts.add(account2);

        return accounts;
    }
}

