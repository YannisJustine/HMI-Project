package fr.iutfbleau.projetIHM2022FI2.controller.listeners.comboBox;


import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import fr.iutfbleau.projetIHM2022FI2.API.Groupe;

/**
 * Classe représentant un listener des JComboBox du panneau {@link fr.iutfbleau.projetIHM2022FI2.view.admin.MoveStudentPanel MoveStudentPanel}. 
 */
public class ComboJTreeListener implements ItemListener {
    
    private DefaultTreeModel model;
    private JTree tree;

    /**
     * Le constructeur du listener.
     * @param tree un arbre de groupe
     */
    public ComboJTreeListener(JTree tree){
        this.model = (DefaultTreeModel) tree.getModel();
        this.tree = tree;
    }

    @Override
    /**
     * Lorsque un groupe est change dans le JComboBox, alors l'arbre qui représente le chemin vers ce groupe est actualisé.
     */
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED){
            Groupe groupe = (Groupe) e.getItem();
            LinkedList<Groupe> list = new LinkedList<>();
            list.offerFirst(groupe);
            do{
                groupe = groupe.getPointPoint();
                list.offerFirst(groupe);
            } while(!groupe.getPointPoint().equals(groupe));
            DefaultMutableTreeNode node;
            DefaultMutableTreeNode root = new DefaultMutableTreeNode(list.poll());
            this.model.setRoot(root);
            while(!list.isEmpty()){
                node = new DefaultMutableTreeNode(list.poll());
                root.add(node);
                root = node;
            }
            model.reload();
            expandAllNodes();
        }
        else {
            model.setRoot(null);
            model.reload();
        }
    }
    
    /**
     * Permet d'étendre le JTree (Trouvé sur stackOverflow car en Java 8 il n'y a pas la méthode qui ouvre tout le JTree).
     */
    private void expandAllNodes() {
        int j = tree.getRowCount();
        int i = 0;
        while(i < j) {
            tree.expandRow(i);
            i += 1;
            j = tree.getRowCount();
        }
    }
}