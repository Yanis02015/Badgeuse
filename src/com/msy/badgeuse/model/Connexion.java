package com.msy.badgeuse.model;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connexion {
    private java.sql.Connection connection;
    private Statement statement;

    public Connexion() {
        String username = "yanis";
        String password = "root";
        String url = "jdbc:postgresql://localhost:5432/yanis"; //?autoReconnect=true&useSSL=false

        try {
            //Class.forName("com.mysql.jdbc.Driver");
            Class.forName("org.postgresql.Driver");
            System.out.println("1");
        } catch (ClassNotFoundException e) {
            System.err.print("ClassNotFoundException : ");
            System.err.println(e.getMessage());
        }

        try{
            System.out.println("2");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("OK connexion réussie...");
            statement = connection.createStatement();
            System.out.println("3");
        } catch(SQLException ex){
            System.out.println(ex.getMessage());
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
            System.out.println(ex.getMessage());
        }
    }
    public void close(){
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Connexion conn = new Connexion();
        conn.seConnecter();
        Statement statement;
        ResultSet rs;
        statement = conn.getStatement();
        try {
            statement.execute("INSERT INTO table_a(id, username, password) VALUES (4 , 'k', 'l')");
            System.out.println("INSERTION REUSSI !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        conn.close();
    }
}
