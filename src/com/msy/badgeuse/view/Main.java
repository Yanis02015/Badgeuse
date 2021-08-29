package com.msy.badgeuse.view;

import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;

public class Main {
    public static Window w;
    public static final String CARTE_SUPER_USER = "";

    public static void main(String[] args) {
        w = new Window("Login");
        testConnection();
    }

    private static void testConnection() {
        w.setVisible(true);
        try {
            SerialPort testDevice = SerialPort.getCommPorts()[0];
            w.startScan();
        } catch (Exception e) {
            System.err.println("Problème de connexion avec le processeur de lecture de carte.");
            String messageError = "Branchez la carte Arduino, puis reessayez.\nVoulez vous reessayer ?";
            int dialogResult = JOptionPane.showConfirmDialog(new JFrame(), messageError, "Erreur de connexion", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                testConnection();
            }
        }
    }

    private static void userActivity() {

    }
}