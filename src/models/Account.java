package models;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Account {
    private String uid;
    private String upin;
    private String name;
    private String email;
    private String phone;
    private String aadharNumber;
    private String panNumber;
    private BigDecimal balance;
    private Timestamp createdAt;

    public Account() {}

    public Account(String uid, String upin, String name, String email, String phone, 
                  String aadharNumber, String panNumber, BigDecimal balance) {
        this.uid = uid;
        this.upin = upin;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.aadharNumber = aadharNumber;
        this.panNumber = panNumber;
        this.balance = balance;
    }

    // Getters and Setters
    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getUpin() { return upin; }
    public void setUpin(String upin) { this.upin = upin; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAadharNumber() { return aadharNumber; }
    public void setAadharNumber(String aadharNumber) { this.aadharNumber = aadharNumber; }

    public String getPanNumber() { return panNumber; }
    public void setPanNumber(String panNumber) { this.panNumber = panNumber; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
} 