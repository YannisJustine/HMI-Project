package fr.iutfbleau.projetIHM2022FI2.controller.listeners.jlist;

import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import fr.iutfbleau.projetIHM2022FI2.API.Etudiant;

/**
 * Listener d'un clic sur la JList affichant tous les étudiants
 * @see fr.iutfbleau.projetIHM2022FI2.view.panel.PanelChoixEleves
 */
public class ChoixEleveListListener implements ListSelectionListener {

    private JLabel prenom;
    private JLabel nom;
    private JLabel id;
    
    public ChoixEleveListListener(JLabel nom, JLabel prenom, JLabel id){
        this.prenom = prenom;
        this.nom = nom;
        this.id = id;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void valueChanged(ListSelectionEvent e) {
        Etudiant etu =(Etudiant) ((JList<Etudiant>)e.getSource()).getSelectedValue();
        this.prenom.setText(etu.getPrenom());
        this.nom.setText(etu.getNom());
        this.id.setText(Integer.toString(etu.getId()));
    }
}
