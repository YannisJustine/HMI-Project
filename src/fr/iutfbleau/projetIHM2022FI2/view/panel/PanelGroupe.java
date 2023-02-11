package fr.iutfbleau.projetIHM2022FI2.view.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

import fr.iutfbleau.projetIHM2022FI2.Constants;
import fr.iutfbleau.projetIHM2022FI2.API.Etudiant;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.action.AddGroupeAction;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.action.DeleteGroupeAction;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.jtree.GroupeTreeListener;
import fr.iutfbleau.projetIHM2022FI2.view.custom.renderer.CustomListRenderer;
import fr.iutfbleau.projetIHM2022FI2.view.custom.tree.CustomJTree;

/**
 * Classe représentant le panneau contenant l'arbre des groupes et une Liste d'étudiants
 */
public class PanelGroupe extends JSplitPane {
    
    private JList<Etudiant> list;

    /**
     * Un constructeur pour les admins.
     * @param tree l'arbre des groupes
     * @param listener l'actionListener des boutons
     * @param listenerCard l'actionListener du bouton "Ajouter / Retirer Etudiant"
     */
    public PanelGroupe(CustomJTree tree, ActionListener listener, ActionListener listenerCard){
        super(JSplitPane.HORIZONTAL_SPLIT);
        CustomJTree jtree = tree;
        this.setResizeWeight(0.4);
        //Le panneau de gauche contenant l'arbre des groupes et ses boutons
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        //Le JScrollPane contenant l'arbre des groupes 
        JScrollPane treePanel = new JScrollPane(tree);
        
        //Le panneau contenant les boutons
        JPanel panelButton = new JPanel();

        //Le bouton pour ajouter un groupe
        JButton addButton = new JButton("Ajouter Groupe");
        addButton.setActionCommand(Constants.ADD);
        addButton.addActionListener(listener);

        //Le bouton pour retirer un groupe
        JButton removeButton = new JButton("Retirer Groupe");
        removeButton.setActionCommand(Constants.REMOVE);
        removeButton.addActionListener(listener);

        //Le bouton pour renommer un groupe
        JButton renameButton = new JButton("Renommer");
        renameButton.setActionCommand(Constants.RENAME);
        renameButton.addActionListener(listener);

        //Un raccourci pour supprimer un groupe
        tree.getActionMap().put("remove", new DeleteGroupeAction(tree));
        tree.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "remove");
        
        //Un raccourci pour ajouter un groupe
        tree.getActionMap().put("add", new AddGroupeAction(tree));
        tree.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), "add");

        //Ajoute tous les boutons au panneau pour les boutons
        panelButton.add(addButton);
        panelButton.add(removeButton);
        panelButton.add(renameButton);

        //Un JLabel 
        JLabel groupe = new JLabel("Groupes",JLabel.CENTER);

        //Ajout au panneau de gauche les différents composants (JLabel, Arbre, Boutons)
        leftPanel.add(groupe,BorderLayout.NORTH);
        leftPanel.add(treePanel,BorderLayout.CENTER);  
        leftPanel.add(panelButton,BorderLayout.SOUTH);

        //Le panneau de droite contenant la liste des étudiants et son bouton
        JPanel rightPanel = new JPanel(new BorderLayout());

        //Une JList d'étudiants
        DefaultListModel<Etudiant> listModel = new DefaultListModel<>();
        this.list = new JList<>(listModel);
        list.setCellRenderer(new CustomListRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);
        list.setVisibleRowCount(0);

        //Le panneau pour le bouton d'ajout d'étudiant (au cas où on voudrait rajouter des boutons)
        JPanel panelButtonStudent = new JPanel();
     
        //Le bouton pour ajouter/retirer des étudiants
        JButton studentButton = new JButton("Ajouter / Retirer Étudiant");
        studentButton.setActionCommand(Constants.ELEVE);
        studentButton.addActionListener(listenerCard);

        //Ajout du bouton à son panel
        panelButtonStudent.add(studentButton);

        //Un JScrollPane pour la liste
        JScrollPane scrollPane = new JScrollPane(list);

        //Un JLabel
        JLabel eleve = new JLabel("Élèves",JLabel.CENTER);

        //Ajout au panneau de droite les différents composants (JLabel, JList, Bouton)
        rightPanel.add(eleve,BorderLayout.NORTH);
        rightPanel.add(scrollPane,BorderLayout.CENTER);  
        rightPanel.add(panelButtonStudent,BorderLayout.SOUTH);

        jtree.addTreeSelectionListener(new GroupeTreeListener(listModel));
        this.setLeftComponent(leftPanel);
        this.setRightComponent(rightPanel);
    }

    /**
     * Un constructeur pour les étudiants et le professeur. 
     * @param tree l'arbre des groupes 
     */
    public PanelGroupe(CustomJTree tree){
        super(JSplitPane.HORIZONTAL_SPLIT);
        CustomJTree jtree = tree;
        this.setResizeWeight(0.5);

        //Le panneau de gauche contenant l'arbre des groupes et un jLabel
        JPanel leftPanel = new JPanel(new BorderLayout());

        //Le JScrollPane contenant l'arbre des groupes 
        JScrollPane treePanel = new JScrollPane(tree);

        //Un JLabel 
        JLabel groupe = new JLabel("Groupes",JLabel.CENTER);

        //Ajout au panneau de gauche les différents composants (JLabel, Arbre)
        leftPanel.add(groupe,BorderLayout.NORTH);
        leftPanel.add(treePanel,BorderLayout.CENTER);  

        //Le panneau de droite contenant la liste des étudiants 
        JPanel rightPanel = new JPanel(new BorderLayout());
        DefaultListModel<Etudiant> listModel = new DefaultListModel<>();
        this.list = new JList<>(listModel);
        list.setCellRenderer(new CustomListRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);
        list.setVisibleRowCount(0);

        
        //Un JScrollPane pour la liste
        JScrollPane scrollPane = new JScrollPane(list);

        //Un JLabel
        JLabel eleve = new JLabel("Élèves",JLabel.CENTER);

        //Ajout au panneau de droite les différents composants (JLabel, JList)
        rightPanel.add(eleve,BorderLayout.NORTH);
        rightPanel.add(scrollPane,BorderLayout.CENTER);  

        jtree.addTreeSelectionListener(new GroupeTreeListener(listModel));
        this.setLeftComponent(leftPanel);
        this.setRightComponent(rightPanel);
    }

    /**
     * Retourne la liste des étudiants.
     * @return La liste des étudiants 
     */
    public JList<Etudiant> getList(){
        return this.list;
    }
}
