package com.msy.badgeuse.view;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import javax.swing.*;

public class Main {
    public static Window w;
    public static final String CARTE_SUPER_USER = "";
    public static void main(String[] args) {
         w = new Window("Login");
        testConnection();



        /*
        try {
            SerialPort device = SerialPort.getCommPorts()[0];
            device.openPort();
            device.addDataListener(new SerialPortDataListener() {
                @Override
                public int getListeningEvents() {
                    return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
                }

                @Override
                public void serialEvent(SerialPortEvent serialPortEvent) {
                    if (serialPortEvent.getEventType() == SerialPort.LISTENING_EVENT_DATA_RECEIVED) {
                        byte[] data = serialPortEvent.getReceivedData();
                        String msg = new String(data);

                        if (msg.contains("dec: ")) {
                            msg = msg.substring(msg.indexOf("dec: ") + 6);
                            if(msg.equals(CARTE_SUPER_USER))
                                userActivity();
                            else {

                            }
                            System.out.println(msg);
                        }
                    }
                }
            });
        } catch (Exception e) {
            System.err.println("Problème de connexion avec le processeur de lecture de carte.");
            JOptionPane.showMessageDialog(new JFrame(), "Scanner de carte non détecté!", "Erreur", JOptionPane.ERROR_MESSAGE);
        }*/
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
            if(dialogResult == JOptionPane.YES_OPTION) {
                testConnection();
            }
        }
    }

    private static void userActivity() {

    }
}