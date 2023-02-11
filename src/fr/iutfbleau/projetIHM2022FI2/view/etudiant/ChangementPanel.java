package fr.iutfbleau.projetIHM2022FI2.view.etudiant;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.awt.GridBagLayout;
import java.awt.Insets;

import com.formdev.flatlaf.FlatClientProperties;

import java.awt.GridBagConstraints;

import fr.iutfbleau.projetIHM2022FI2.Constants;
import fr.iutfbleau.projetIHM2022FI2.API.Changement;

/**
 * Représentation visuelle d'un changement
 */
public class ChangementPanel extends JPanel {
    public static final String ACCEPT = "accept";
    public static final String DENY = "deny";
    private Changement changement;
    private JButton acceptButton;
    private JButton denyButton;

    /**
     * Constructeur 
     * @param c le changement qui doit être représenté
     * @param isAdmin est-ce l'utilisateur qui voit le changement est un Administrateur ?
     */
    public ChangementPanel(Changement c, boolean isAdmin) {
        super();
        this.changement = c;
        this.putClientProperty(FlatClientProperties.STYLE, "arc: 8" );
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // --------------- Disposition des éléments ---------------
        StringBuilder sb = new StringBuilder();
        sb.append("L'étudiant ");
        sb.append(c.getEtu().getNom());
        sb.append(" ");
        sb.append(c.getEtu().getPrenom());
        if(Constants.loggedEtudiant != null && Constants.loggedEtudiant.equals(c.getEtu()))
            sb.append(" (vous) ");
        sb.append(" voudrait passer du groupe ");
        sb.append(c.getA().getName());
        sb.append(" (");
        sb.append(c.getA().getSize());
        sb.append(")");
        sb.append(" au groupe ");
        sb.append(c.getB().getName());
        sb.append(" (");
        sb.append(c.getB().getSize());
        sb.append(")");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Titre du changement
        JTextArea texte = new JTextArea();
        texte.setEditable(false);  
        texte.setCursor(null);  
        texte.setOpaque(true);  
        texte.setFocusable(false);
        texte.setLineWrap(true);
        texte.setWrapStyleWord(true);
        texte.append(sb.toString());
        texte.setOpaque(false);
        texte.setBorder(BorderFactory.createEmptyBorder());
        this.add(texte, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.BOTH;
        
        // Explications
        JTextArea explication = new JTextArea(10,20);
        explication.setEditable(false);  
        explication.setCursor(null);  
        explication.setOpaque(true);  
        explication.setFocusable(false);
        explication.setLineWrap(true);
        explication.setWrapStyleWord(true);
        explication.append("Explications : \n" + c.getExplication());
        JScrollPane scroll = new JScrollPane(explication);

        this.add(scroll, gbc);
        // Potentiels boutons
        this.addButtons(isAdmin);
    }

    private void addButtons(boolean isAdmin) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        this.acceptButton = new ChangementButton("Accepter", this);
        this.acceptButton.setActionCommand(ChangementPanel.ACCEPT);
        if(isAdmin)
            this.add(this.acceptButton,gbc);
        gbc.anchor = GridBagConstraints.EAST;
        this.denyButton = new ChangementButton(isAdmin ? "Refuser" : "Annuler" , this);
        this.denyButton.setActionCommand(ChangementPanel.DENY);
        if(isAdmin || Constants.loggedEtudiant == changement.getEtu())
            this.add(this.denyButton,gbc);
    }

    /**
     * Permet d'ajouter un ActionListener aux boutons du panneau 
     * @param l le l'ActionListener
     */
    public void addButtonsListener(ActionListener l) {
        this.acceptButton.addActionListener(l);
        this.denyButton.addActionListener(l);
    }

    /**
     * Renvoie le changement qui est représenté par ce panneau
     * @return le changement
     */
    public Changement getChangement() {
        return changement;
    }

}
