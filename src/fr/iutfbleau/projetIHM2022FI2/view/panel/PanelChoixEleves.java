package fr.iutfbleau.projetIHM2022FI2.view.panel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import fr.iutfbleau.projetIHM2022FI2.Fenetre;
import fr.iutfbleau.projetIHM2022FI2.API.AbstractChangementFactory;
import fr.iutfbleau.projetIHM2022FI2.API.AbstractGroupeFactory;
import fr.iutfbleau.projetIHM2022FI2.API.Etudiant;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.buttons.ChoixEleveButton;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.jlist.ChoixEleveListListener;
import fr.iutfbleau.projetIHM2022FI2.view.custom.renderer.CustomListRenderer;

/**
 * Classe représentant le panneau du choix des élèves.
 */
public class PanelChoixEleves extends JPanel {
    
    /**
     * Constructeur du panneau.
     * @param fenetre la "Fenetre" pour la passer en argument à un listener.
     * @param agf la factory qui gère les groupes
     * @param acf la factory qui gère les changements
     */
    public PanelChoixEleves(Fenetre fenetre, AbstractGroupeFactory agf,AbstractChangementFactory acf ){
        super(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        

        JLabel text = new JLabel("Choisir un élève");
        text.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        //Charge tous les élèves disponible
        DefaultListModel<Etudiant> model = new DefaultListModel<Etudiant>();
        for(Etudiant etu : agf.getPromotion().getEtudiants())
            model.addElement(etu);
        JList<Etudiant> list = new JList<>(model);

        list.setCellRenderer(new CustomListRenderer(false));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Un ScrollPane pour la liste 
        JScrollPane scroll = new JScrollPane(list);
        JPanel panelScroll = new JPanel(new BorderLayout());

        panelScroll.add(new JLabel("Liste étudiants"), BorderLayout.NORTH);
        panelScroll.add(scroll, BorderLayout.CENTER);

       //Les bordures pour les JLabels
        Border border = BorderFactory.createLineBorder(null, 3);
        Border nomBorder = BorderFactory.createTitledBorder(border,"Nom");
        Border prenomBorder = BorderFactory.createTitledBorder(border,"Prénom");
        Border idBorder = BorderFactory.createTitledBorder(border,"Id");

        JLabel nom = new JLabel("Nom",JLabel.CENTER);
        nom.setBorder(nomBorder);
        JLabel prenom = new JLabel("Prénom",JLabel.CENTER);
        prenom.setBorder(prenomBorder);
        JLabel id = new JLabel("id",JLabel.CENTER);
        id.setBorder(idBorder);

        JButton confirme = new JButton("Confirmer");

        //Les listeners pour la liste et le bouton
        confirme.addActionListener(new ChoixEleveButton(fenetre, agf, acf, list));
        list.addListSelectionListener(new ChoixEleveListListener(nom, prenom, id));

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(10,0,10,0);
        this.add(text,gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = 3;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        gbc.weighty = 1;
        gbc.insets = new Insets(0,10,0,10);
        this.add(panelScroll,gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        gbc.weighty = 1;
        gbc.insets = new Insets(0,10,0,10);
        this.add(nom,gbc);
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.7;
        gbc.weighty = 1;
        this.add(prenom,gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0.7;
        gbc.weighty = 1;
        gbc.insets = new Insets(0,10,0,10);
        this.add(id,gbc);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(10,0,10,0);
        this.add(confirme,gbc);
    }
}
