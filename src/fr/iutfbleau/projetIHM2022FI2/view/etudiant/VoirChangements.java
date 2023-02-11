package fr.iutfbleau.projetIHM2022FI2.view.etudiant;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import fr.iutfbleau.projetIHM2022FI2.Constants;
import fr.iutfbleau.projetIHM2022FI2.API.AbstractChangementFactory;

import fr.iutfbleau.projetIHM2022FI2.API.Groupe;
import fr.iutfbleau.projetIHM2022FI2.API.TypeGroupe;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.comboBox.MoveComboBoxListener;
import fr.iutfbleau.projetIHM2022FI2.interfaces.ReloadablePanel;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.buttons.ChangementButtonListener;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.buttons.CreateChangementListener;


import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedHashSet;
import java.util.Set;
import java.awt.GridBagConstraints;

/**
 * Classe représentant le panneau contenant les changements et la création d'un changement (uniquement si l'utilisateur est un administrateur)
 */
public class VoirChangements extends JPanel implements ReloadablePanel{

    private AbstractChangementFactory acf;
    private ShowChangementPanels listChangement;
    private CreateChangementPanel createChangement;
    private MoveComboBoxListener comboListener;
    private CreateChangementListener buttonListener;
    private boolean admin;

    /**
     * Constructeur
     * @param acf la factory qui crée les changements
     * @param admin est-ce que l'utilisateur est administrateur ?
     */
    public VoirChangements(AbstractChangementFactory acf, boolean admin) {
        super();
        this.acf = acf;
        this.admin = admin;
        this.setLayout(new GridBagLayout());
        this.listChangement = new ShowChangementPanels(admin);
        this.createChangement = new CreateChangementPanel();
        JScrollPane scrollPane = new JScrollPane(listChangement);
        scrollPane.setBorder(new EmptyBorder(scrollPane.getInsets()));

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.BOTH;
        if(!admin) {
            this.add(createChangement, gbc);
        }
            
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.5;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.BOTH;
        this.add(scrollPane, gbc);

        listChangement.setListener(new ChangementButtonListener(listChangement, acf));
        this.comboListener = new MoveComboBoxListener(this.createChangement, acf.getGroupeFactory());
        this.createChangement.setComboBoxListener(comboListener);
        this.buttonListener = new CreateChangementListener(createChangement, listChangement, acf);
        this.createChangement.setButtonListener(buttonListener);
    }

    private void loadGroupe() {
        Set<Groupe> allGroupes = this.acf.getGroupeFactory().getGroupesOfEtudiant(Constants.loggedEtudiant);
            Set<Groupe> studentGroupes = new LinkedHashSet<Groupe>();
            for(Groupe groupe : allGroupes){
                if(groupe.getPointPoint().getType() == TypeGroupe.PARTITION)
                    studentGroupes.add(groupe);
            }
        this.createChangement.setGroupeDepart(studentGroupes);
    }

    private void reloadChangements() {
        this.listChangement.reload();
    }

    /**
     * Met à jour les informations du panneau
     * @see fr.iutfbleau.projetIHM2022FI2.interfaces.ReloadablePanel
     */
    public void reload() {
        if(!this.admin)
            loadGroupe();
        reloadChangements();
    }
}