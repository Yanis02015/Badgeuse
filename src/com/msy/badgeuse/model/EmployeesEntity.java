package com.msy.badgeuse.model;

import com.msy.badgeuse.control.Employees;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeesEntity {
    // SQL REQUEST
    public static final String SQL_SELECT_TABLE = "SELECT idEmploye, nom, prenom, fontion FROM employe";

    private Connexion connexion;

    public DefaultTableModel getTableModel(String[] title) {
        DefaultTableModel tModel = new DefaultTableModel(title, 0);
        connexion = new Connexion();
        connexion.seConnecter();
        Statement statement;
        statement = connexion.getStatement();
        //On récupère les MetaData
        try {
            //L'objet ResultSet contient le résultat de la requête SQL
            ResultSet result = statement.executeQuery("SELECT id, nom, prenom, fonction FROM table_d ORDER BY id");
            connexion.close();
            //On récupère les MetaData
            ResultSetMetaData resultMeta = result.getMetaData();
            while(result.next()) {
                Object[] obj = new Object[resultMeta.getColumnCount()];
                for(int i = 1; i <= resultMeta.getColumnCount(); i++) {
                    obj[i-1] = result.getObject(i);
                }
                tModel.addRow(obj);
            }
            result.close();
            statement.close();

        } catch (SQLException ex) {
            Logger.getLogger(EmployeesEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tModel;
    }

    public DefaultTableModel getTableDetails(String[] title, int id) {
        DefaultTableModel tModel = new DefaultTableModel(title, 0);
        connexion = new Connexion();
        connexion.seConnecter();
        Statement statement;
        statement = connexion.getStatement();
        //On récupère les MetaData
        try {
            //L'objet ResultSet contient le résultat de la requête SQL
            ResultSet result = statement.executeQuery("SELECT nom, prenom, day, start_at, end_at FROM table_d, table_f WHERE table_d.id = " + id + " AND table_d.carte_id = table_f.idemploye;");
            connexion.close();
            //On récupère les MetaData
            ResultSetMetaData resultMeta = result.getMetaData();
            Object[] obj = new Object[resultMeta.getColumnCount()];
            while(result.next()) {
                for(int i = 1; i <= resultMeta.getColumnCount(); i++) {
                    obj[i-1] = result.getObject(i);
                }
                tModel.addRow(obj);
            }
            result.close();
            statement.close();

        } catch (SQLException ex) {
            Logger.getLogger(EmployeesEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tModel;
    }

    public void addNewEmployee(Employees employee) {
        connexion = new Connexion();
        connexion.seConnecter();
        Statement statement;
        statement = connexion.getStatement();
        try {
            statement.execute("INSERT INTO table_d(carte_id, nom, prenom, fonction) VALUES('" + employee.getIdentifier() + "', '" + employee.getFirstName() + "', '" + employee.getLastName() + "', '" + employee.getRole() + "');");
            connexion.close();
            JOptionPane.showMessageDialog(new JFrame(), "Employé ajouter avec sucées", "Succes message", JOptionPane.INFORMATION_MESSAGE);
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Une erreur s'est produite, employé non ajouté", "Error message", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Employees getEmployeById(int id) {
        connexion = new Connexion();
        connexion.seConnecter();
        Statement statement;
        statement = connexion.getStatement();
        try {
            ResultSet result = statement.executeQuery("SELECT carte_id, nom, prenom, fonction FROM table_d WHERE id = " + id +";");
            connexion.close();
            result.next();
            String newCardId = result.getObject(1).toString();
            String newName = result.getObject(2).toString();
            String newLastName = result.getObject(3).toString();
            String newFonction = result.getObject(4).toString();
            statement.close();

            return new Employees(newCardId, newName, newLastName, newFonction);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new Employees("", "erreur", "erreur", "erreur");
    }

    public void editEmployee(Employees employees, int id) {
        connexion = new Connexion();
        connexion.seConnecter();
        Statement statement;
        statement = connexion.getStatement();
        try {
            statement.execute("UPDATE table_d SET carte_id = '" + employees.getIdentifier() + "', nom = '" + employees.getFirstName() + "', prenom = '" + employees.getLastName() + "', fonction= '" + employees.getRole() + "' WHERE id = " + id + ";");
            connexion.close();
            statement.close();
        } catch (SQLException throwables) {
            JOptionPane.showMessageDialog(new JFrame(), "Une erreur s'est produite, employé non modifier", "Error message", JOptionPane.ERROR_MESSAGE);
            throwables.printStackTrace();
        }
    }

    public boolean itExists(String idCard) {
        connexion = new Connexion();
        connexion.seConnecter();
        Statement statement;
        statement = connexion.getStatement();
        try {
            ResultSet result = statement.executeQuery("SELECT exists (SELECT 1 FROM table_d WHERE carte_id = '" + idCard + "' LIMIT 1);");
            connexion.close();
            result.next();
            boolean exists = Boolean.parseBoolean(result.getObject(1).toString());
            statement.close();
            return exists;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}