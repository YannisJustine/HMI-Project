package fr.iutfbleau.projetIHM2022FI2.controller.listeners.action;

import javax.swing.AbstractAction;

import fr.iutfbleau.projetIHM2022FI2.view.custom.tree.CustomJTree;
import java.awt.event.ActionEvent;

/**
 * Classe qui représente ajouter un groupe lors de l'appui sur la touche INSER
 */
public class DeleteGroupeAction extends AbstractAction{

    private CustomJTree tree;

    /**
     * Constructeur
     * @param tree le {@link fr.iutfbleau.projetIHM2022FI2.view.custom.tree.CustomJTree CustomTree} sur lequel l'action est effectué 
     */
    public DeleteGroupeAction(CustomJTree tree) {
        this.tree = tree;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tree.removeNode();
    }

}