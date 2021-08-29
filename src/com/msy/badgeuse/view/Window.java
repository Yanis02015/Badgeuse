package com.msy.badgeuse.view;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.msy.badgeuse.control.Employees;
import com.msy.badgeuse.control.Pointing;
import com.msy.badgeuse.model.EmployeesEntity;
import com.msy.badgeuse.model.PointingEntity;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Window extends JFrame {
    private static final String CARTE_SUPER_USER = "SUPER_USER_ID";
    private Boolean adminConnected = false;
    private final int width = 500;
    private final int height = 610;

    private MenuBar menuBar = new MenuBar();
    private JPanel content;

    private Dashboard dashboard = new Dashboard();

    // Login Panel
    private Login login;
    private Home home;

    public Window(String title) {
        this.setTitle(title);
        this.setLocation(600, 190);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Menu
        configureMenu();

        // The JPanel configuration
        content = new JPanel();
        content.setBorder(new EmptyBorder(5, 10, 5, 10));
        content.setLayout(new BorderLayout());

        toHome();
    }

    private void configureMenu() {
        menuBar.getAdmin().addActionListener(e -> toLogin());
        menuBar.getFermer().addActionListener(e -> this.dispose());
        menuBar.getHome().addActionListener(e -> toHome());
        menuBar.getDeconnecter().addActionListener(e -> toLogin());
        menuBar.getRefresh().addActionListener(e -> dashboard.refreshTable());
        this.setJMenuBar(menuBar);
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

                        EmployeesEntity employeesEntity = new EmployeesEntity();
                        if (employeesEntity.itExists(msg)) {
                            DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                            DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm:ss");
                            LocalDateTime now = LocalDateTime.now();
                            home.addToResultScan(msg + " : " + date.format(now) + ":" + time.format(now) + "+" + now);

                            PointingEntity pointingEntity = new PointingEntity();
                            if(pointingEntity.itExists(msg)) {
                                int lastId = pointingEntity.getLastIdOf(msg);
                                if (pointingEntity.isNotEnded(msg, date.format(now))) {
                                    pointingEntity.addPointingEnd(lastId, time.format(now));
                                } else {
                                    now = LocalDateTime.now();
                                    Pointing pointing = new Pointing(msg, date.format(now), time.format(now), null);
                                    pointingEntity.addPointing(pointing);
                                }
                            } else {
                                Pointing pointing = new Pointing(msg, date.format(now), time.format(now), null);
                                pointingEntity.addPointing(pointing);
                            }
                        } else {
                            newCard(msg);
                        }
                        System.out.println(msg);
                    }
                }
            }
        });
    }

    private void newCard(String cardId) {
        if (adminConnected) {
            CreateEmployee createEmployee = new CreateEmployee(cardId);
            createEmployee.setVisible(true);
            createEmployee.getBtnCreateEmployee().addActionListener(e -> {
                if (createEmployee.inputIsOk()) {
                    Employees employees = createEmployee.getEmployeeInInput();
                    EmployeesEntity employeesEntity = new EmployeesEntity();
                    employeesEntity.addNewEmployee(employees);
                    createEmployee.dispose();
                }
            });
        } else {
            String messageToAddNewCard = "Nouvelle carte detecter.\nVoulez devez vous connecter en tant que administratur !";
            JOptionPane.showMessageDialog(new JFrame(), messageToAddNewCard, "Nouvelle carte", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void toHome() {
        adminConnected = false;
        menuBar.getAdmin().setVisible(true);
        menuBar.getHome().setVisible(false);
        menuBar.getDeconnecter().setVisible(false);
        menuBar.getRefresh().setVisible(false);

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
        adminConnected = false;
        menuBar.getAdmin().setVisible(false);
        menuBar.getHome().setVisible(true);
        menuBar.getDeconnecter().setVisible(false);
        menuBar.getRefresh().setVisible(false);

        content.removeAll();
        login = new Login();
        login.getBtnLogin().addActionListener(e -> {
            if (login.loginIsOk()) {
                adminConnected = true;
                content.remove(login);
                this.setContentPane(content);
                toDashboard();
            } else
                adminConnected = false;
        });
        content.add(login, BorderLayout.CENTER);
        this.setContentPane(content);

        this.setSize(200, 200);
        this.setLocation(600, 190);
    }

    private void toDashboard() {
        EmployeesEntity employeesEntity = new EmployeesEntity();
        menuBar.getAdmin().setVisible(false);
        menuBar.getHome().setVisible(true);
        menuBar.getDeconnecter().setVisible(true);
        menuBar.getRefresh().setVisible(true);

        content.removeAll();
        dashboard.getEditEmployee().addActionListener(e -> {
            int idSelected = dashboard.getIdEmployeSelected();
            Employees employees = employeesEntity.getEmployeById(idSelected);
            UpdateEmployee updateEmployee = new UpdateEmployee(employees);
            updateEmployee.setVisible(true);
            updateEmployee.getBtnUpdateEmployee().addActionListener(e1 -> {
                employeesEntity.editEmployee(updateEmployee.getEmployeeInInput(), idSelected);
                updateEmployee.dispose();
            });

        });
        content.add(dashboard, BorderLayout.CENTER);
        this.setContentPane(content);

        this.setSize(width, height);
        this.setLocation(450, 100);

    }

    private boolean loginIsOK() {
        return false;
    }
}
