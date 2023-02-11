package fr.iutfbleau.projetIHM2022FI2.controller.listeners.panel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.iutfbleau.projetIHM2022FI2.interfaces.ReloadablePanel;

/**
 * ChangeListener du TabbedPane de la fenêtre
 */
public class TabbedPaneListener implements ChangeListener {
    
    /**
     * Constructeur vide
     */
    public TabbedPaneListener(){
    }
    /**
     * Lorsque l'on change d'onglet le panneau à afficher met à jour ses informations.
     * Exemple : 
     *  On vient de supprimer un étudiant d'un groupe, on va sur l'onglet "Déplacer un étudiant", il faut que le groupe dans lequel il n'est plus ne soit pas affiché
     */
    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JTabbedPane) {
            JTabbedPane pane = (JTabbedPane) e.getSource();
            if(pane.getSelectedComponent() instanceof ReloadablePanel){
                ((ReloadablePanel)pane.getSelectedComponent()).reload();
            }
        }
    }
}
