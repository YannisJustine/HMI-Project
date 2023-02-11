package fr.iutfbleau.projetIHM2022FI2.view.custom.dialog;

import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import fr.iutfbleau.projetIHM2022FI2.API.Groupe;
import fr.iutfbleau.projetIHM2022FI2.view.custom.renderer.CustomListRenderer;

/**
 * Classe représentant une fenêtre de dialogue qui affiche les groupes d'un étudiant
 */
public class CustomDialogEtudiant extends JDialog {
    /**
     * Construteur de la fenêtre de dialogue
     * @param f la fenêtre depuis laquelle est affiché le dialog
     * @param name le nom de l'étudiant
     * @param surname le prénom de l'étudiant
     * @param groupes l'ensemble des groupes auxquels appartient l'étudiant
     */
    public CustomDialogEtudiant(JFrame f, String name, String surname, Set<Groupe> groupes) {
        super(f,"Informations", false);
        this.setLayout(new GridBagLayout());
        DefaultListModel<Groupe> listModel = new DefaultListModel<Groupe>();
        JList<Groupe> list = new JList<Groupe>(listModel);
        for(Groupe groupe : groupes)
            listModel.addElement(groupe);
        list.setVisibleRowCount(groupes.size()); 
        list.setCellRenderer(new CustomListRenderer());
        list.putClientProperty("List.isFileList", Boolean.TRUE);
        JScrollPane scrollPane = new JScrollPane(list) ;

        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.NONE;

        JLabel label = new JLabel("Etudiant : " + name.toUpperCase() + " " + surname);
        label.setHorizontalAlignment(JLabel.CENTER);
        this.add(label, gbc);

        JLabel groupe = new JLabel("Groupes auxquels il appartient : ");
        groupe.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        this.add(groupe, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.BOTH;
        this.add(scrollPane, gbc);
        this.pack();
        this.setMinimumSize(this.getSize());
    }
    
}
