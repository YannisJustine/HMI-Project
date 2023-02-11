package fr.iutfbleau.projetIHM2022FI2.controller.listeners.jtree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import fr.iutfbleau.projetIHM2022FI2.Constants;
import fr.iutfbleau.projetIHM2022FI2.view.custom.tree.CustomJTree;


/**
 * Classe représentant le listener pour l'appuie d'un bouton pour le JTree. 
 */
public class JTreeButtonsListener implements ActionListener{

    private CustomJTree tree ; 
    
    public JTreeButtonsListener(CustomJTree tree){
        this.tree = tree;
    }

    /**
     * Permet d'ajouter, retirer ou renommer un groupe.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (Constants.ADD.equals(command))
            tree.questionForGroupe();
        else if(Constants.REMOVE.equals(command))
            tree.removeNode();
        else if(Constants.RENAME.equals(command))
            tree.rename();
    }
}
