package com.bank.services;

import com.bank.models.Account;
import com.bank.utils.DatabaseConnector;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Random;

public class AccountService {
    
    public Account createAccount(String name, String email, String phone, 
                               String aadharNumber, String panNumber, 
                               BigDecimal initialDeposit, String upin) throws SQLException {
        
        String uid = generateUID();
        Account account = new Account(uid, upin, name, email, phone, 
                                    aadharNumber, panNumber, initialDeposit);
        
        String sql = "INSERT INTO ACCOUNTS (uid, upin, name, email, phone, " +
                    "aadhar_number, pan_number, balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, account.getUid());
            pstmt.setString(2, account.getUpin());
            pstmt.setString(3, account.getName());
            pstmt.setString(4, account.getEmail());
            pstmt.setString(5, account.getPhone());
            pstmt.setString(6, account.getAadharNumber());
            pstmt.setString(7, account.getPanNumber());
            pstmt.setBigDecimal(8, account.getBalance());
            
            pstmt.executeUpdate();
            return account;
        }
    }
    
    public Account login(String uid, String upin) throws SQLException {
        String sql = "SELECT * FROM ACCOUNTS WHERE uid = ? AND upin = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, uid);
            pstmt.setString(2, upin);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Account account = new Account();
                    account.setUid(rs.getString("uid"));
                    account.setUpin(rs.getString("upin"));
                    account.setName(rs.getString("name"));
                    account.setEmail(rs.getString("email"));
                    account.setPhone(rs.getString("phone"));
                    account.setAadharNumber(rs.getString("aadhar_number"));
                    account.setPanNumber(rs.getString("pan_number"));
                    account.setBalance(rs.getBigDecimal("balance"));
                    account.setCreatedAt(rs.getTimestamp("created_at"));
                    return account;
                }
            }
        }
        return null;
    }
    
    public boolean transferMoney(String fromUid, String toUid, BigDecimal amount) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnector.getConnection();
            conn.setAutoCommit(false);
            
            // Check if sender has sufficient balance
            String balanceQuery = "SELECT balance FROM ACCOUNTS WHERE uid = ? FOR UPDATE";
            try (PreparedStatement pstmt = conn.prepareStatement(balanceQuery)) {
                pstmt.setString(1, fromUid);
                ResultSet rs = pstmt.executeQuery();
                if (!rs.next() || rs.getBigDecimal("balance").compareTo(amount) < 0) {
                    conn.rollback();
                    return false;
                }
            }
            
            // Update sender's balance
            String debitSql = "UPDATE ACCOUNTS SET balance = balance - ? WHERE uid = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(debitSql)) {
                pstmt.setBigDecimal(1, amount);
                pstmt.setString(2, fromUid);
                pstmt.executeUpdate();
            }
            
            // Update receiver's balance
            String creditSql = "UPDATE ACCOUNTS SET balance = balance + ? WHERE uid = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(creditSql)) {
                pstmt.setBigDecimal(1, amount);
                pstmt.setString(2, toUid);
                pstmt.executeUpdate();
            }
            
            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
            }
        }
    }
    
    public BigDecimal getBalance(String uid) throws SQLException {
        String sql = "SELECT balance FROM ACCOUNTS WHERE uid = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, uid);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("balance");
                }
            }
        }
        return null;
    }
    
    public boolean resetUPIN(String uid, String aadharNumber, String panNumber, String newUPIN) throws SQLException {
        String sql = "UPDATE ACCOUNTS SET upin = ? WHERE uid = ? AND aadhar_number = ? AND pan_number = ?";
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, newUPIN);
            pstmt.setString(2, uid);
            pstmt.setString(3, aadharNumber);
            pstmt.setString(4, panNumber);
            
            return pstmt.executeUpdate() > 0;
        }
    }
    
    private String generateUID() {
        Random random = new Random();
        StringBuilder uid = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            uid.append(random.nextInt(10));
        }
        return uid.toString();
    }
} 