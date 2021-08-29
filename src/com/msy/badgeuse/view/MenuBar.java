package com.msy.badgeuse.view;

import javax.swing.*;

public class MenuBar extends JMenuBar {
    private JMenuItem admin, fermer, home, deconnecter, refresh;

    public MenuBar() {
        JMenu fichier = new JMenu("Fichier");
        JMenu edition = new JMenu("Edition");
        JMenu about = new JMenu("A propos");

        admin = new JMenuItem("Admin");
        fermer = new JMenuItem("Fermer");
        home = new JMenuItem("Home");
        deconnecter = new JMenuItem("Se deconnecter");

        refresh = new JMenuItem("Actualiser");
        refresh.setVisible(false);
        JMenuItem e2 = new JMenuItem("e2");

        fichier.add(admin);
        fichier.add(home);
        fichier.add(deconnecter);
        fichier.add(fermer);

        edition.add(refresh);

        this.add(fichier);
        this.add(edition);
    }

    public JMenuItem getAdmin() {
        return admin;
    }

    public JMenuItem getFermer() {
        return fermer;
    }

    public JMenuItem getHome() {
        return home;
    }

    public JMenuItem getDeconnecter() {
        return deconnecter;
    }

    public JMenuItem getRefresh() {
        return refresh;
    }
}
