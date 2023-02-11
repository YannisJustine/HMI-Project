package fr.iutfbleau.projetIHM2022FI2.view.panel;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.iutfbleau.projetIHM2022FI2.Constants;
import fr.iutfbleau.projetIHM2022FI2.Fenetre;
import fr.iutfbleau.projetIHM2022FI2.API.AbstractChangementFactory;
import fr.iutfbleau.projetIHM2022FI2.API.AbstractGroupeFactory;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.buttons.ChoixListener;

/**
 * Classe représentant le panneau des choix de compte.
 */
public class PanelChoix extends JPanel{

    /**
     * Constructeur du panneau.
     * @param fenetre la "Fenetre" pour la passer en argument à un listener.
     * @param agf la factory qui gère les groupes
     * @param acf la factory qui gère les changements
     */
    public PanelChoix(Fenetre fenetre, AbstractGroupeFactory agf,AbstractChangementFactory acf){
        super(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        ActionListener listener = new ChoixListener(fenetre,agf,acf);

        JButton eleve = new JButton("Eleve");
        eleve.setActionCommand(Constants.ELEVE);
        eleve.addActionListener(listener);
    
        JButton prof = new JButton("Professeur");
        prof.setActionCommand(Constants.PROF);
        prof.addActionListener(listener);
    
        JButton admin = new JButton("Administrateur");
        admin.setActionCommand(Constants.ADMIN);
        admin.addActionListener(listener);
    
        JLabel text = new JLabel("Choisir un compte");

        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        this.add(text,gbc);
    
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridy = 1;
        gbc.ipady = 20;
        gbc.insets = new Insets(0,10,0,10);
        gbc.gridx = 0;
        this.add(eleve, gbc);
       
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.ipady = 20;
        gbc.insets = new Insets(0,10,0,10);
        this.add(prof, gbc);
    
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridy = 1;
        gbc.ipady = 20;
        gbc.insets = new Insets(0,10,0,10);
        gbc.gridx = 2;
        this.add(admin, gbc);
    }

}
