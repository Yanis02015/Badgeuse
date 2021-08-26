package com.msy.badgeuse.view;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class Main {
    public static final String CARTE_SUPER_USER = "";
    public static void main(String[] args) {
        Window w = new Window("login");
        w.setVisible(true);

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
            System.out.println("Problème de connexion avec le processeur de lecture de carte.");
        }
    }

    private static void userActivity() {

    }
}