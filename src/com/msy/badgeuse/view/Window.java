package com.msy.badgeuse.view;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.msy.badgeuse.control.Employees;
import com.msy.badgeuse.model.EmployeesEntity;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Window extends JFrame {
    private static final String CARTE_SUPER_USER = "SUPER_USER_ID";
    private final int width = 500;
    private final int height = 610;

    private JPanel content;

    // Login Panel
    private Login login;
    private Home home;

    public Window(String title) {
        this.setTitle(title);
        this.setSize(1000, 610);
        this.setLocation(600, 190);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The JPanel configuration
        content = new JPanel();
        content.setLayout(new BorderLayout());

        toHome();
    }

    public void startScan() {
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
                        Toolkit.getDefaultToolkit().beep();
                        msg = msg.substring(msg.indexOf("dec: ") + 6);
                        if (msg.equals(CARTE_SUPER_USER))
                            userActivity();
                        else {
                            EmployeesEntity employeesEntity = new EmployeesEntity();
                            if (employeesEntity.itExists(msg)) {
                                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                LocalDateTime now = LocalDateTime.now();
                                home.addToResultScan(msg + " : " + dtf.format(now));
                            } else {
                                String messageToAddNewCard = "Nouvelle carte detecter.\nVoulez devez vous connecter en tant que administratur !";
                                JOptionPane.showMessageDialog(new JFrame(), messageToAddNewCard, "Nouvelle carte", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        System.out.println(msg);
                    }
                }
            }
        });
    }

    private void userActivity() {
        System.out.println("SUPERUSER MODE ACTIVATED");
    }

    private void toHome() {
        home = new Home();
        home.getBtnAdmin().addActionListener(e -> {
            toLogin();
        });
        content.removeAll();
        content.add(home, BorderLayout.CENTER);
        this.setContentPane(content);

        this.setSize(600, 300);
        this.setLocation(400, 190);
    }

    private void toLogin() {
        content.removeAll();
        login = new Login();
        login.getBtnLogin().addActionListener(e -> {
            if (login.loginIsOk()) {
                content.remove(login);
                this.setContentPane(content);
                toDashboard();
            }
        });
        content.add(login, BorderLayout.CENTER);
        this.setContentPane(content);

        this.setSize(200, 170);
        this.setLocation(600, 190);
    }

    private void toDashboard() {
        content.removeAll();
        Dashboard dashboard = new Dashboard();
        content.add(dashboard, BorderLayout.CENTER);
        this.setContentPane(content);

        this.setSize(width, height);
        this.setLocation(450, 100);

    }

    private boolean loginIsOK() {
        return false;
    }
}
