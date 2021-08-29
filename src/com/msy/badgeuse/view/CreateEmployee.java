package com.msy.badgeuse.view;

import com.msy.badgeuse.control.Employees;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CreateEmployee extends JFrame {
    private JButton btnCreateEmployee;
    private JTextField txtNom;
    private JTextField txtPrenom;
    private JTextField txtCardId;
    private JTextField txtFonction;

    public CreateEmployee(String cardId) {
        this.setTitle("Nouvel Employé");
        this.setSize(350, 330);
        this.setLocation(600, 190);
        this.setResizable(false);

        // Input
        txtNom = getInputWithSize();
        txtPrenom = getInputWithSize();
        txtCardId = getInputWithSize();
        txtFonction = getInputWithSize();
        btnCreateEmployee = new JButton("Créé l'employé");

        // Panel of label
        JPanel pLabelNom = getLabelInPanelLeft("Nom");
        JPanel pLabelPrenom = getLabelInPanelLeft("Prénom");
        JPanel pLabelCardId = getLabelInPanelLeft("Matricule");
        JPanel pLabelFonction = getLabelInPanelLeft("Fonction");

        // Titre
        JLabel bigTitle = new JLabel("Créer un nouvel employé");
        bigTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        JPanel pBigTitle = new JPanel();
        pBigTitle.setBorder(new EmptyBorder(1,0, 5, 0));
        pBigTitle.add(bigTitle);

        // Les box pour alligner les Label et les input
        Box boxName = getBox(pLabelNom, txtNom);
        Box boxPrenom = getBox(pLabelPrenom, txtPrenom);
        Box boxCardId = getBox(pLabelCardId, txtCardId);
        Box boxFonction = getBox(pLabelFonction, txtFonction);

        // Button
        JPanel pCreateEmployee = new JPanel();
        pCreateEmployee.add(btnCreateEmployee);

        // tout regrouper vrticalement
        Box boxContent = Box.createVerticalBox();
        boxContent.add(pBigTitle);
        boxContent.add(boxName);
        boxContent.add(boxPrenom);
        boxContent.add(boxCardId);
        boxContent.add(boxFonction);
        boxContent.add(boxFonction);
        boxContent.add(pCreateEmployee);

        // Configure CardId input
        txtCardId.setText(cardId);
        txtCardId.setEditable(false);

        JPanel panel = new JPanel();
        panel.add(boxContent);
        this.setContentPane(panel);
    }

    public JButton getBtnCreateEmployee() {
        return btnCreateEmployee;
    }

    public boolean inputIsOk() {
        if (txtNom.getText().length() < 2) {
            warning("Le nom");
            return false;
        } else if (txtPrenom.getText().length() < 2){
            warning("Le prénom");
            return false;
        }
        else if (txtFonction.getText().length() < 2){
            warning("La fonction");
            return false;
        }
        else if (txtCardId.getText().length() < 5){
            warning("Le code de la carte");
            return false;
        }
        return true;
    }

    private void warning(String string) {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(new JFrame(), string + " n'est pas valide!", "Erreur de saisi", JOptionPane.ERROR_MESSAGE);
    }

    public Employees getEmployeeInInput() {
        return new Employees(txtCardId.getText(), txtNom.getText(), txtPrenom.getText(), txtFonction.getText());
    }

    public JPanel getLabelInPanelLeft(String label) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JLabel(label), BorderLayout.WEST);
        return panel;
    }

    public JTextField getInputWithSize() {
        JTextField textField = new JTextField(15);
        textField.setFont(new Font("SansSerif", Font.BOLD, 15));
        return textField;
    }

    public Box getBox(JPanel panelLabel, JTextField textField) {
        EmptyBorder emptyBorder = new EmptyBorder(3,2, 10, 2);
        Box box = Box.createVerticalBox();
        box.add(panelLabel);
        box.add(textField);
        box.setBorder(emptyBorder);
        return box;
    }
}
