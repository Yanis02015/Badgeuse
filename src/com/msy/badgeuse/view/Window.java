package com.msy.badgeuse.view;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private final int width = 500;
    private final int height = 610;

    private JPanel content;

    // Login Panel
    private Login login;

    public Window(String title) {
        this.setTitle(title);
        this.setSize(200, 170);
        this.setLocation(600, 190);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The JPanel configuration
        content = new JPanel();
        content.setLayout(new BorderLayout());

        toLogin();
    }

    private void toLogin() {
        login = new Login();
        login.getBtnLogin().addActionListener(e -> {
            if(login.loginIsOk()) {
                toHome();
            }
        });
        content.add(login, BorderLayout.CENTER);
        this.setContentPane(content);
    }

    private void toHome() {
        this.setSize(width, height);
        this.setLocation(450, 100);
        JPanel p = new JPanel();
        JLabel l = new JLabel("gggge");
        p.add(l);
        this.setContentPane(p);
    }

    private boolean loginIsOK() {
        return false;
    }
}
