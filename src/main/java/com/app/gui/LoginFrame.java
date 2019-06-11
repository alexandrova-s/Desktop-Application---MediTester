package com.app.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JButton adminBtn;
    private JButton userBtn;
    private JPanel passwordPanel;
    private JLabel passwordLabel;
    private String password;
    private JPasswordField  passwordTField;
    private JButton passwordBtn;

    public LoginFrame() {
        super("Logowanie");
        setLocationRelativeTo(null);

        adminBtn = new JButton("Administrator");
        adminBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                passwordPanel.setVisible(true);
            }
        });

        userBtn = new JButton("Użytkownik");
        userBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainFrame(true);
            }
        });

        password = "password";
        passwordLabel = new JLabel("Podaj hasło:");
        passwordTField = new JPasswordField (8);
        passwordPanel = new JPanel();

        passwordBtn = new JButton("Zaloguj");
        passwordBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(passwordTField.getText().equals(password)) {
                    dispose();
                    new MainFrame(false);
                } else {
                    JOptionPane.showMessageDialog(null,"Złe hasło","Ważne!",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        passwordPanel.add(passwordLabel, BorderLayout.NORTH);
        passwordPanel.add(passwordTField, BorderLayout.CENTER);
        passwordPanel.add(passwordBtn, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(adminBtn, BorderLayout.NORTH);
        add(userBtn, BorderLayout.SOUTH);
        add(passwordPanel, BorderLayout.CENTER);
        passwordPanel.setVisible(false);

        getContentPane().setBackground(new Color(156, 223, 232));
        setSize(400, 150);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}