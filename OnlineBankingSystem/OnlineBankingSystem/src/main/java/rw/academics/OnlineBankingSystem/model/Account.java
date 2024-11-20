package rw.academics.OnlineBankingSystem.model;

import java.math.BigDecimal;
import java.util.Date;

public class Account {
    private Long id;
    private String type; // e.g., "Savings", "Current", "Loan"
    private BigDecimal balance;
    private String recentActivity;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getRecentActivity() {
        return recentActivity;
    }

    public void setRecentActivity(String recentActivity) {
        this.recentActivity = recentActivity;
    }
}

