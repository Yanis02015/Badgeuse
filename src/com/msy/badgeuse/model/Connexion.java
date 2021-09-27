package com.msy.badgeuse.model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connexion {
    private java.sql.Connection connection;
    private Statement statement;

    public Connexion() {
        String username = "salah";
        String password = "salah";
        String url = "jdbc:postgresql://localhost:5432/salah"; //?autoReconnect=true&useSSL=false

        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("org.postgresql.Driver");
            System.out.println("connexion 1");
        } catch (ClassNotFoundException e) {
            System.err.print("ClassNotFoundException : ");
            System.err.println(e.getMessage());
        }

        try{
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("OK connexion réussie...");
            statement = connection.createStatement();
        } catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
    }

    public Statement getStatement() {
        return this.statement;
    }

    public Connection getConnection() {
        return connection;
    }

    public void seConnecter() {
        try {
            Connexion connect = new Connexion();
            statement = connect.getStatement();
            connection = connect.getConnection();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void close(){
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
