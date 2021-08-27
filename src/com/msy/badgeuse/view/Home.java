package com.msy.badgeuse.view;

import javax.swing.*;
import java.awt.*;

public class Home extends JPanel {
    private final JButton btnGoAdmin;
    private final JTextArea resultScan;

    public Home() {
        this.setLayout(new BorderLayout());

        btnGoAdmin = new JButton("Je suis l'administrateur, me connecter au tableau de bord");
        JPanel panelButtonAdmin = new JPanel();
        panelButtonAdmin.add(btnGoAdmin);

        JPanel pGoAdmin = new JPanel();
        pGoAdmin.setLayout(new BorderLayout());
        pGoAdmin.add(panelButtonAdmin, BorderLayout.CENTER);

        JLabel txtWaitingToScan = new JLabel("Scan en cours..");
        JPanel pWaitingToScan = new JPanel();
        pWaitingToScan.add(txtWaitingToScan);

        resultScan = new JTextArea(5, 20);
        resultScan.setEditable(false);
        resultScan.setLineWrap(true);

        JScrollPane jScrollPane = new JScrollPane(resultScan, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel pResultScan = new JPanel();
        pResultScan.setLayout(new BorderLayout());
        pResultScan.add(jScrollPane, BorderLayout.CENTER);

        JPanel pLeftResult = new JPanel();
        pLeftResult.setLayout(new BorderLayout());
        pLeftResult.add(pWaitingToScan, BorderLayout.NORTH);
        pLeftResult.add(pResultScan, BorderLayout.CENTER);

        JPanel pHome = new JPanel();
        pHome.setLayout(new BorderLayout());

        pHome.add(pLeftResult, BorderLayout.CENTER);
        pHome.add(pGoAdmin, BorderLayout.SOUTH);
        this.add(pHome, BorderLayout.CENTER);
    }

    public JButton getBtnAdmin() {
        return btnGoAdmin;
    }

    public void addToResultScan(String string) {
        resultScan.setText(resultScan.getText() + string + "\n");
    }
}