package com.msy.badgeuse.view;

import com.msy.badgeuse.model.EmployeesEntity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Dashboard extends JPanel {
    private DefaultTableModel defaultTableModel;
    private JTable table;
    private JTable tableDetail;
    private int idEmployeSelected;
    private JButton btnShowEmployeSelected;
    private EmployeesEntity employeesEntity;
    private DefaultTableModel defaultTableDetails;

    public Dashboard() {
        this.setLayout(new BorderLayout());

        // Label
        JLabel labelTitle = new JLabel("Liste des employés");
        JPanel pTitle = new JPanel();
        pTitle.add(labelTitle);

        // Table
        employeesEntity = new EmployeesEntity();
        String[] tableHeader = {"Id", "Nom", "Prénom", "Fonction"};
        defaultTableModel = employeesEntity.getTableModel(tableHeader);
        table = new JTable(defaultTableModel) {
            // Pour désactiver l'édition des cellules dans la JTable (Source : https://www.tutorialspoint.com/how-can-we-disable-the-cell-editing-inside-a-jtable-in-java#:~:text=By%20default%2C%20we%20can%20edit,and%20it%20must%20return%20false. )
            @Override
            public boolean  editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        JScrollPane sc = new JScrollPane(table);

        // Table details :
        idEmployeSelected = 1;
        String[] tableDetailsHeader = {"Nom", "Prénom", "Jour", "Début de service", "Fin de service"};
        defaultTableDetails = employeesEntity.getTableDetails(tableDetailsHeader, 1);
        tableDetail = new JTable(defaultTableDetails) {
            // Pour désactiver l'édition des cellules dans la JTable (Source : https://www.tutorialspoint.com/how-can-we-disable-the-cell-editing-inside-a-jtable-in-java#:~:text=By%20default%2C%20we%20can%20edit,and%20it%20must%20return%20false. )
            @Override
            public boolean  editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        JScrollPane scDetail = new JScrollPane(tableDetail);

        // JButton showEmployeDetails
        JButton showEmployeDetails = new JButton("Afficher les details de cet employé");
        JPanel pBtnShowDetails = new JPanel();
        pBtnShowDetails.add(showEmployeDetails);
        showEmployeDetails.addActionListener(e -> {
            int indexRowSelected = table.getSelectedRow();
            System.out.println(indexRowSelected);
            if(indexRowSelected != -1) {
                String valueId = table.getModel().getValueAt(indexRowSelected, 0).toString();
                idEmployeSelected = Integer.parseInt(valueId);
                this.updateModel();
            }
        });

        JPanel p1 = new JPanel();
        p1.setLayout(new BorderLayout());
        p1.add(sc, BorderLayout.CENTER);

        p1.add(pBtnShowDetails, BorderLayout.SOUTH);

        JPanel p2 = new JPanel();
        p2.setLayout(new BorderLayout());
        p2.add(scDetail, BorderLayout.CENTER);

        Box b1 = Box.createVerticalBox();

        b1.add(p1);
        b1.add(p2);

        this.add(b1, BorderLayout.CENTER);
    }

    public JButton getBtnShowEmployeSelected() {
        return btnShowEmployeSelected;
    }
    public void updateModel() {
        String[] header = {"Nom", "Prénom", "Jour", "Début de service", "Fin de service"};
        defaultTableDetails.setRowCount(0);
        defaultTableDetails = employeesEntity.getTableDetails(header, idEmployeSelected);
        tableDetail.setModel(defaultTableDetails);
    }
}
