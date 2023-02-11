package fr.iutfbleau.projetIHM2022FI2.view.etudiant;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Set;

import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import fr.iutfbleau.projetIHM2022FI2.API.Groupe;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.comboBox.ComboJTreeListener;
import fr.iutfbleau.projetIHM2022FI2.interfaces.ComboBoxGroupeInterface;
import fr.iutfbleau.projetIHM2022FI2.view.custom.renderer.CustomJTreeRenderer;
import fr.iutfbleau.projetIHM2022FI2.view.custom.renderer.CustomListRenderer;

/**
 * Panneau permettant la création d'un changement
 */
public class CreateChangementPanel extends JPanel implements ComboBoxGroupeInterface{
    
    private JComboBox<Groupe> groupeDepart;
    private JComboBox<Groupe> groupeArrivee;
    private JTextArea explications;
    private JButton button;

    /**
     * Constructeur du panneau
     */
    public CreateChangementPanel() {
        super();
        JLabel titre = new JLabel("Demander un changement");
        titre.setHorizontalAlignment(JLabel.CENTER);
        this.groupeDepart = new JComboBox<Groupe>();
        this.groupeArrivee = new JComboBox<Groupe>();
        this.groupeDepart.setRenderer(new CustomListRenderer());
        this.groupeArrivee.setRenderer(new CustomListRenderer());
        this.button = new JButton("Envoyer la demande");
        this.explications = new JTextArea(2,2);
        this.explications.setLineWrap(true);

        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.NONE;
        this.add(titre, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.NONE;
        this.add(groupeDepart, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.NONE;
        this.add(groupeArrivee, gbc);

        JTree tree = new JTree();
        tree.setCellRenderer(new CustomJTreeRenderer());
        tree.setEnabled(false);

        JTree tree2 = new JTree();
        tree2.setCellRenderer(new CustomJTreeRenderer());
        tree2.setEnabled(false);

        this.groupeDepart.addItemListener(new ComboJTreeListener(tree));
        this.groupeArrivee.addItemListener(new ComboJTreeListener(tree2));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.NONE;
        this.add(tree,gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.NONE;
        this.add(tree2,gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.BOTH;
        JScrollPane scrollPane = new JScrollPane(this.explications);
        this.add(scrollPane, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.NONE;
        this.add(this.button, gbc);

    }

    /**
     * Permet de définir les groupes de départ
     * @param groupes l'ensemble des groupes
     * @see fr.iutfbleau.projetIHM2022FI2.API.Changement#getA()
     */
    public void setGroupeDepart(Set<Groupe> groupes) {
        this.groupeDepart.removeAllItems();
        for(Groupe g : groupes)
            this.groupeDepart.addItem(g);
        if(!groupes.isEmpty())
            this.groupeDepart.setSelectedIndex(0);
    }

    /**
     * Permet de définir les groupes d'arrivée
     * @param groupes l'ensemble des groupes
     * @see fr.iutfbleau.projetIHM2022FI2.API.Changement#getB()
     */
    public void setGroupeArrivee(Set<Groupe> groupes) {
        this.groupeArrivee.removeAllItems();
        for(Groupe g : groupes)
            this.groupeArrivee.addItem(g);
        if(!groupes.isEmpty())
            this.groupeArrivee.setSelectedIndex(0);
    }

    /**
     * Ajoute un ActionListener au bouton "confirmer"
     * @param l l'ActionListener à ajouter
     */
    public void setButtonListener(ActionListener l) {
        this.button.addActionListener(l);
    }

    /**
     * Ajoute un ItemListener au ComboBox du groupe de départ
     * @param l l'ItemListener à ajouter
     */
    public void setComboBoxListener(ItemListener l) {
        this.groupeDepart.addItemListener(l);
    }

    /**
     * Retourne le groupe de départ sélectionné
     * @return le groupe sélectionné
     * @see fr.iutfbleau.projetIHM2022FI2.API.Groupe
    */
    public Groupe getSelectedGroupeDepart() {
        return (Groupe) this.groupeDepart.getSelectedItem();
    }

    /**
     * Retourne le groupe d'arrivée sélectionné
     * @return le groupe sélectionné
     * @see fr.iutfbleau.projetIHM2022FI2.API.Groupe
    */
    public Groupe getSelectedGroupeArrivee() {
        return (Groupe) this.groupeArrivee.getSelectedItem();
    }

    /**
     * Retourne les explications
     * @return une {@link String chaine de caractère} qui contient les explications
     */
    public String getExplications() {
        return this.explications.getText().trim();
    }
    
    /**
     * Efface le texte dans le champs de texte
     * @see javax.swing.JTextArea#setText(String)
     */
    public void reset() {
        this.explications.setText("");
    }

}
