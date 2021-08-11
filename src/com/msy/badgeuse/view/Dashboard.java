package com.msy.badgeuse.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Dashboard extends JPanel {
    private DefaultTableModel defaultTableModel;
    private JTable table;
    private JButton btnShowEmployeSelected;
    public Dashboard() {
        this.setLayout(new BorderLayout());

        // Label
        JLabel labelTitle = new JLabel("Liste des employés");
        JPanel pTitle = new JPanel();
        pTitle.add(labelTitle);

        // Table
        String[] tableHeader = {"Id", "Nom", "Prénom", "Fonction"};
        /*defaultTableModel = employeEntity.getTableModel(tableHeader);
        table = new JTable(defaultTableModel) {
            // Pour désactiver l'édition des cellules dans la JTable (Source : https://www.tutorialspoint.com/how-can-we-disable-the-cell-editing-inside-a-jtable-in-java#:~:text=By%20default%2C%20we%20can%20edit,and%20it%20must%20return%20false. )
            @Override
            public boolean  editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        JScrollPane sc = new JScrollPane(table);*/

        // Jbutton show employe selected
        JPanel pShowEmployeSelected = new JPanel();
        btnShowEmployeSelected = new JButton("Afficher l'article");
        pShowEmployeSelected.add(btnShowEmployeSelected);
    }

    public JButton getBtnShowEmployeSelected() {
        return btnShowEmployeSelected;
    }
}
