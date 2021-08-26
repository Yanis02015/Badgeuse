package com.msy.badgeuse.view;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.*;
import java.awt.*;

public class Login extends JPanel {
    public static final String userLogin = "root";
    public static final String passwordHash = "ce5ca673d13b36118d54a7cf13aeb0ca012383bf771e713421b4d1fd841f539a";

    private final JButton btnLogin;
    private final JTextField login;
    private final JPasswordField password;

    public Login() {
        this.setLayout(new BorderLayout());

        JPanel header = new JPanel();
        header.setLayout(new BorderLayout());

        // Label
        JLabel title = new JLabel("Se connecter");
        JPanel pTitle = new JPanel();
        pTitle.add(title);

        // TextField
        login = new JTextField();
        JPanel pLogin = new JPanel(); pLogin.add(login);
        password = new JPasswordField();
        JPanel pPassword = new JPanel(); pPassword.add(password);
        JPanel pLoginPassword = new JPanel();
        pLoginPassword.setLayout(new BorderLayout());
        pLoginPassword.add(pLogin, BorderLayout.NORTH);
        pLoginPassword.add(pPassword, BorderLayout.CENTER);

        // Button
        btnLogin = new JButton("Se connecter");
        JPanel pBtnLogin = new JPanel();
        pBtnLogin.add(btnLogin);
        pLoginPassword.add(pBtnLogin, BorderLayout.SOUTH);

        // TextField custom
        login.setColumns(10);
        login.setFont(new Font("SansSerif", Font.BOLD, 15));
        password.setColumns(10);
        password.setFont(new Font("SansSerif", Font.BOLD, 15));



        // Implimentation finale
        header.add(pTitle, BorderLayout.NORTH);
        header.add(pLoginPassword, BorderLayout.CENTER);
        this.add(header, BorderLayout.NORTH);
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public String getLogin() {
        return login.getText();
    }

    public String getPasswordEntered() {
        return String.valueOf(password.getPassword());
    }

    public String hashPassword(String passwordEntered) {
        String pass = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(passwordEntered.getBytes());

            byte[] byteData = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte byteDatum : byteData) {
                sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
            }
            pass = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return pass;
    }

    public boolean loginIsOk() {
        return userLogin.equals(this.getLogin()) && passwordHash.equals(this.hashPassword(this.getPasswordEntered()));
    }
}
