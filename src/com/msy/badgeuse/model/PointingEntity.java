package com.msy.badgeuse.model;

import com.msy.badgeuse.control.Employees;
import com.msy.badgeuse.control.Pointing;

import javax.swing.*;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PointingEntity {
    private Connexion connexion;

    public void addPointing(Pointing pointing) {
        connexion = new Connexion();
        connexion.seConnecter();
        Statement statement;
        statement = connexion.getStatement();
        try {
            String sqlRequest = "INSERT INTO table_f (day, start_at, idemploye) VALUES('" + pointing.getDate() + "', '" + pointing.getStartAt() + "', '" + pointing.getIdentifier() + "');";
            if(pointing.getEndAt() == null) {
                statement.execute(sqlRequest);
            }
            connexion.close();
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Une erreur s'est produite, donnée non ajouté", "Error message", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addPointingEnd(int id, String endAt) {
        connexion = new Connexion();
        connexion.seConnecter();
        Statement statement;
        statement = connexion.getStatement();
        try {
            statement.execute("UPDATE table_f SET end_at = '" + endAt + "' WHERE id = " + id + ";");
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "Une erreur s'est produite, donnée non ajouté", "Error message", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isNotEnded(String identifier, String date) {
        connexion = new Connexion();
        connexion.seConnecter();
        Statement statement;
        statement = connexion.getStatement();
        try {
            ResultSet result = statement.executeQuery("SELECT end_at FROM table_f WHERE day = '" + date + "' AND idemploye = '" + identifier + "';");
            connexion.close();
            String endAt = "NULL";
            while(result.next()) {
                endAt = "NULL";
                endAt = result.getObject(1).toString();
            }
            if (endAt.equals("NULL")) {
                statement.close();
                return true;
            } else {
                statement.close();
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean itExists(String idCard) {
        connexion = new Connexion();
        connexion.seConnecter();
        Statement statement;
        statement = connexion.getStatement();
        try {
            ResultSet result = statement.executeQuery("SELECT exists (SELECT 1 FROM table_f WHERE idemploye = '" + idCard + "' LIMIT 1);");
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

    public int getLastIdOf(String cardId) {
        connexion = new Connexion();
        connexion.seConnecter();
        Statement statement;
        statement = connexion.getStatement();
        try {
            ResultSet result = statement.executeQuery("SELECT id FROM table_f WHERE idemploye = '" + cardId + "';");
            connexion.close();
            int lastId = -1;
            while (result.next()) {
                lastId = Integer.parseInt(result.getObject(1).toString());
            }
            statement.close();
            return lastId;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
}
