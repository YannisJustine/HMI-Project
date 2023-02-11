package fr.iutfbleau.projetIHM2022FI2.view.custom.tree;

import fr.iutfbleau.projetIHM2022FI2.Constants;
import fr.iutfbleau.projetIHM2022FI2.API.AbstractGroupeFactory;
import fr.iutfbleau.projetIHM2022FI2.API.Groupe;
import fr.iutfbleau.projetIHM2022FI2.API.TypeGroupe;
import fr.iutfbleau.projetIHM2022FI2.interfaces.SwingWorkerInterface;
import fr.iutfbleau.projetIHM2022FI2.view.custom.renderer.CustomJTreeRenderer;
import fr.iutfbleau.projetIHM2022FI2.view.custom.worker.CustomWorker;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;



/**
 * Classe représentant l'arbre des groupes.
 */
public class CustomJTree extends JTree implements SwingWorkerInterface{
    
    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private AbstractGroupeFactory groupeFactory;
    private JProgressBar bar;
    private JDialog dialog;
    private JTextField field1;
    private JTextField field2;
    private Groupe parentGroupe;

    /**
     * Le constructeur.
     * @param agf la factory qui gère les groupes
     */
    public CustomJTree(AbstractGroupeFactory agf){
        super();
        this.groupeFactory = agf;
        this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.root = createNode(agf.getPromotion());
        this.treeModel = new DefaultTreeModel(this.root);
        this.setModel(treeModel);
        this.setCellRenderer(new CustomJTreeRenderer());    
        ToolTipManager.sharedInstance().registerComponent(this);
        this.dialog = new JDialog((JFrame)null," Modifications en cours" ,true);
        this.dialog.setLayout(new BorderLayout());
        this.bar = new JProgressBar(0,100);
        this.dialog.add(new JLabel("Chargement",JLabel.CENTER),BorderLayout.NORTH);
        this.dialog.add(bar,BorderLayout.CENTER);
        this.dialog.pack();
        this.dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }

    /**
     * Permet de créer un Node contenant tous ses fils.
     * Utilisé lors de la création de l'arbre ou de l'ajout d'un groupe
     * @param root le groupe père
     * @return un Node avec tous ses fils
     */
    public DefaultMutableTreeNode createNode(Groupe root){
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(root);
        for(Groupe child : root.getSousGroupes()){
            if( child.getSousGroupes() != null){
                rootNode.add(createNode(child));
            }
            else{
                rootNode.add(new DefaultMutableTreeNode(child));
            }
        }
        return rootNode; 
    }

  
    /**
     * Permet de demander l'ajout d'un groupe ou d'une partition
     */
    public void questionForGroupe(){
        Object[] possibilite = {"Groupe", "Partition"};
        int choix =  JOptionPane.showOptionDialog(this.getParent(),"Voulez-vous créer un groupe ou une partition ?", "Groupe/Partition", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,  null, possibilite, possibilite[0]);
        if(choix == JOptionPane.YES_OPTION )
            addGroupe();
        if(choix == JOptionPane.NO_OPTION )
            addPartition();
    }

    /**
     * Permet d'ajouter un groupe.
     */
    private void addGroupe(){
        
        JTextField field1 = new JTextField("Groupe");
        JTextField field2 = new JTextField("1");
        JTextField field3 = new JTextField("15");
        Object[] message = {
            "Entrez le nom du groupe", field1,
            "Entrez le minimum d'élève", field2,
            "Entrez le maximum d'élève", field3,
        };
        int choix = JOptionPane.showConfirmDialog(this.getParent(), message, "Entrez les informations du groupe", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
       
        if(choix != JOptionPane.OK_OPTION)
            return;
        while(field1.getText().trim().length() > Constants.LENGTH || field1.getText().trim().length() <= 0){
            JOptionPane.showMessageDialog(this.getParent(), "0 < nom <= " + Constants.LENGTH); 
            choix = JOptionPane.showConfirmDialog(this.getParent(), message, "Entrez les informations du groupe", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(choix != JOptionPane.OK_OPTION)
                return;
        }
        DefaultMutableTreeNode parentNode = null;
        TreePath parentPath = this.getSelectionPath();
        if (parentPath == null) {
            parentNode = this.root;
        } else {
            parentNode = (DefaultMutableTreeNode)(parentPath.getLastPathComponent());
        }
        try{
            groupeFactory.createGroupe((Groupe)parentNode.getUserObject(), field1.getText(), Integer.parseInt(field2.getText()), Integer.parseInt(field3.getText()));
            Groupe parent = (Groupe)parentNode.getUserObject();
            DefaultMutableTreeNode groupe = createNode(parent);
            if(parentNode != this.root){
                this.treeModel.insertNodeInto(groupe, (MutableTreeNode) parentNode.getParent(), parentNode.getParent().getIndex(parentNode));
                this.treeModel.removeNodeFromParent(parentNode);
            }
            else{
                this.root = groupe;
                this.treeModel.setRoot(groupe);
            }
            TreePath chemin = new TreePath(groupe.getPath());
            this.setSelectionPath(chemin);
            this.expandPath(chemin); // Ouvre le noeud
            this.scrollPathToVisible(chemin);
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this.getParent(),e.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
        }
        
    }

    /**
     * Permet d'ajouter une partition.
     */
    private void addPartition(){
         
        this.field1 = new JTextField("TD");
        this.field2 = new JTextField("2");
        Object[] message = {
            "Entrez le nom du groupe", field1,
            "Combien de partition ? ", field2,
        };
        int choix = JOptionPane.showConfirmDialog(this.getParent(), message, "Entrez les informations de la partition", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(choix != JOptionPane.OK_OPTION)
            return;
        while(field1.getText().trim().length() > Constants.LENGTH || field1.getText().trim().length() <= 0){
            JOptionPane.showMessageDialog(this.getParent(), "0 < nom <= 25"); 
            choix = JOptionPane.showConfirmDialog(this.getParent(), message, "Entrez les informations de la partition", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(choix != JOptionPane.OK_OPTION)
                return;
        }
        if(choix == JOptionPane.OK_OPTION){
            
            DefaultMutableTreeNode parentNode = null;
            TreePath parentPath = this.getSelectionPath();
            if(parentPath == null)
                parentNode = this.root;
            else
                parentNode = (DefaultMutableTreeNode)(parentPath.getLastPathComponent());
                
            parentGroupe = (Groupe)parentNode.getUserObject();
            this.dialog.setLocationRelativeTo(this);
            bar.setIndeterminate(true);
            SwingWorker<Void,Void> worker = new CustomWorker(this);
            worker.execute();
            this.dialog.setVisible(true);
            DefaultMutableTreeNode groupe = createNode(parentGroupe);
            if(parentNode != this.root){
                this.treeModel.insertNodeInto(groupe, (MutableTreeNode) parentNode.getParent(), parentNode.getParent().getIndex(parentNode));
                this.treeModel.removeNodeFromParent(parentNode);
            }
            else{
                this.root = groupe;
                this.treeModel.setRoot(groupe);
            }
            TreePath chemin = new TreePath(groupe.getPath());
            this.setSelectionPath(chemin);
            this.expandPath(chemin); // Ouvre le noeud
            this.scrollPathToVisible(chemin);
        }
    }
    

    /**
     * Permet d'enlever une Node et donc de retirer un groupe.
     */
    public void removeNode(){

        TreePath currentPath = this.getSelectionPath();
        if(currentPath != null){

            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) currentPath.getLastPathComponent();
            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) currentNode.getParent();
            if(parentNode != null){

                if(((Groupe)parentNode.getUserObject()).getType() == TypeGroupe.PARTITION){
                    JOptionPane.showMessageDialog(this.getParent(), "Vous ne pouvez pas retirer un sous-groupe d'un partition \nVeuillez supprimer la partition","Supprimer Groupe",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try{

                    int res = JOptionPane.showConfirmDialog(this.getParent(),"Voulez vous vraiment supprimer ce groupe ?", "Supprimer Groupe",JOptionPane.YES_NO_CANCEL_OPTION);    
                    if(res == JOptionPane.YES_OPTION){
                        groupeFactory.deleteGroupe((Groupe) currentNode.getUserObject());
                        this.treeModel.removeNodeFromParent(currentNode);
                        this.setSelectionPath(new TreePath(parentNode.getPath()));
                    }
                }
                catch(IllegalStateException e){

                    int res = JOptionPane.showConfirmDialog(this.getParent(),"Attention ce groupe contient des sous-groupes \nSupprimer ?", "Supprimer Groupe",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
                    if(res == JOptionPane.YES_OPTION){
                        ArrayList<Groupe> list = new ArrayList<Groupe>();
                        recurRemove((Groupe) currentNode.getUserObject(), list);
                        for(Groupe gr : list) {
                            groupeFactory.deleteGroupe(gr);  
                        }
                        groupeFactory.deleteGroupe((Groupe)currentNode.getUserObject());
                        this.treeModel.removeNodeFromParent(currentNode);
                        this.setSelectionPath(new TreePath(parentNode.getPath()));
                    }
                }
            }
            else{
                JOptionPane.showMessageDialog(this.getParent(),"Impossible de supprimer le root",
                "Erreur",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Ajoute dans la liste les groupes à supprimer dans l'ordre
     * @param g le groupe de départ 
     * @param list la liste dans 
     */
    private void recurRemove(Groupe g, ArrayList<Groupe> list){ 
        for(Groupe groupe : g.getSousGroupes()){
            recurRemove(groupe, list);
            list.add(groupe);
        }
    }

    /**
     * Permet de renommer un groupe.
     */
    public void rename(){
        TreePath curPath = this.getSelectionPath();
        if(curPath != null){
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) curPath.getLastPathComponent();
            Groupe groupe = (Groupe) currentNode.getUserObject();
            JTextField field1 = new JTextField();
            field1.setText(groupe.getName());
            Object[] message = {
                "Entrez le nouveau nom du groupe", field1,
            };
            int choix = JOptionPane.showConfirmDialog(this.getParent(), message, "Entrez le nom", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(choix != JOptionPane.OK_OPTION)
                return;
            while(field1.getText().trim().length() > Constants.LENGTH || field1.getText().trim().length() <= 0){
                JOptionPane.showMessageDialog(this.getParent(), "0 < nom <= " + Constants.LENGTH); 
                field1.setText(groupe.getName());   
                choix = JOptionPane.showConfirmDialog(this.getParent(), message, "Entrez le nom", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if(choix != JOptionPane.OK_OPTION)
                    return;
            }
            groupeFactory.rename(groupe, field1.getText().trim());
            this.treeModel.reload();
            this.setSelectionPath(curPath);
        }     
    }

    public DefaultMutableTreeNode getRoot() {
        return root;
    }

    public void reload() {
        TreePath temp = this.getSelectionPath();
        this.setSelectionPath(null);
        this.setSelectionPath(temp);
    }

    @Override
    public void whenBackground() {
        try {
            groupeFactory.createPartition(parentGroupe, field1.getText(), Integer.parseInt(field2.getText()));
        }   catch(Exception e){
            //this.dialog.setVisible(false);
            //Bug si l'on met this.getParent à la place de null
            JOptionPane.showMessageDialog(null,e.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void whenDone() {
        this.bar.setIndeterminate(false);
        this.dialog.setVisible(false);
    }
}
