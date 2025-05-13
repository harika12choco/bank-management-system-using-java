import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import services.AccountService;
import models.Account;

public class BankManagementSystem extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private AccountService accountService;
    private Account currentAccount;
    
    public BankManagementSystem() {
        accountService = new AccountService();
        
        setTitle("Bank Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Create different panels
        createLoginPanel();
        createRegistrationPanel();
        createDashboardPanel();
        
        add(mainPanel);
    }
    
    private void createLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField uidField = new JTextField(20);
        JPasswordField upinField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("New Account");
        
        gbc.gridx = 0; gbc.gridy = 0;
        loginPanel.add(new JLabel("UID:"), gbc);
        gbc.gridx = 1;
        loginPanel.add(uidField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        loginPanel.add(new JLabel("UPIN:"), gbc);
        gbc.gridx = 1;
        loginPanel.add(upinField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);
        
        gbc.gridy = 3;
        loginPanel.add(registerButton, gbc);
        
        loginButton.addActionListener(e -> {
            try {
                currentAccount = accountService.login(uidField.getText(), new String(upinField.getPassword()));
                if (currentAccount != null) {
                    cardLayout.show(mainPanel, "dashboard");
                    updateDashboard();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        
        registerButton.addActionListener(e -> cardLayout.show(mainPanel, "register"));
        
        mainPanel.add(loginPanel, "login");
    }
    
    private void createRegistrationPanel() {
        JPanel registerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField aadharField = new JTextField(20);
        JTextField panField = new JTextField(20);
        JTextField depositField = new JTextField(20);
        JPasswordField upinField = new JPasswordField(20);
        JButton registerButton = new JButton("Create Account");
        JButton backButton = new JButton("Back to Login");
        
        // Add components with proper layout
        int row = 0;
        gbc.gridx = 0; gbc.gridy = row;
        registerPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(nameField, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        registerPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(emailField, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        registerPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(phoneField, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        registerPanel.add(new JLabel("Aadhar Number:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(aadharField, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        registerPanel.add(new JLabel("PAN Number:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(panField, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        registerPanel.add(new JLabel("Initial Deposit:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(depositField, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        registerPanel.add(new JLabel("Set UPIN:"), gbc);
        gbc.gridx = 1;
        registerPanel.add(upinField, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        registerPanel.add(registerButton, gbc);
        
        row++;
        gbc.gridy = row;
        registerPanel.add(backButton, gbc);
        
        registerButton.addActionListener(e -> {
            try {
                String upin = new String(upinField.getPassword());
                if (upin.length() < 4 || upin.length() > 6) {
                    JOptionPane.showMessageDialog(this, "UPIN must be 4-6 digits!");
                    return;
                }
                
                Account account = accountService.createAccount(
                    nameField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    aadharField.getText(),
                    panField.getText(),
                    new BigDecimal(depositField.getText()),
                    upin
                );
                
                JOptionPane.showMessageDialog(this, 
                    "Account created successfully!\nYour UID is: " + account.getUid());
                cardLayout.show(mainPanel, "login");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "login"));
        
        mainPanel.add(registerPanel, "register");
    }
    
    private void createDashboardPanel() {
        JPanel dashboardPanel = new JPanel(new BorderLayout());
        
        // Create top panel for user info
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel welcomeLabel = new JLabel();
        JLabel balanceLabel = new JLabel();
        topPanel.add(welcomeLabel);
        topPanel.add(new JLabel(" | "));
        topPanel.add(balanceLabel);
        
        // Create center panel for operations
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        JButton transferButton = new JButton("Transfer Money");
        JButton logoutButton = new JButton("Logout");
        
        gbc.gridx = 0; gbc.gridy = 0;
        centerPanel.add(transferButton, gbc);
        
        gbc.gridy = 1;
        centerPanel.add(logoutButton, gbc);
        
        dashboardPanel.add(topPanel, BorderLayout.NORTH);
        dashboardPanel.add(centerPanel, BorderLayout.CENTER);
        
        transferButton.addActionListener(e -> {
            String recipientUid = JOptionPane.showInputDialog("Enter recipient's UID:");
            if (recipientUid != null && !recipientUid.isEmpty()) {
                String amountStr = JOptionPane.showInputDialog("Enter amount to transfer:");
                try {
                    BigDecimal amount = new BigDecimal(amountStr);
                    if (accountService.transferMoney(currentAccount.getUid(), recipientUid, amount)) {
                        JOptionPane.showMessageDialog(this, "Transfer successful!");
                        updateDashboard();
                    } else {
                        JOptionPane.showMessageDialog(this, "Transfer failed! Insufficient balance or invalid recipient.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }
        });
        
        logoutButton.addActionListener(e -> {
            currentAccount = null;
            cardLayout.show(mainPanel, "login");
        });
        
        mainPanel.add(dashboardPanel, "dashboard");
    }
    
    private void updateDashboard() {
        if (currentAccount != null) {
            try {
                BigDecimal balance = accountService.getBalance(currentAccount.getUid());
                JPanel dashboardPanel = (JPanel) ((JPanel) mainPanel.getComponent(2));
                JPanel topPanel = (JPanel) dashboardPanel.getComponent(0);
                
                ((JLabel) topPanel.getComponent(0)).setText("Welcome, " + currentAccount.getName());
                ((JLabel) topPanel.getComponent(2)).setText("Balance: ₹" + balance);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error updating dashboard: " + e.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new BankManagementSystem().setVisible(true);
        });
    }
} 