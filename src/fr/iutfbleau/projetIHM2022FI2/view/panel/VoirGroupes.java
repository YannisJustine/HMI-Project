package fr.iutfbleau.projetIHM2022FI2.view.panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.JPanel;

import fr.iutfbleau.projetIHM2022FI2.Constants;
import fr.iutfbleau.projetIHM2022FI2.API.AbstractGroupeFactory;
import fr.iutfbleau.projetIHM2022FI2.API.Etudiant;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.buttons.AddStudentListener;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.jtree.JTreeButtonsListener;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.jtree.PopUpGroupeListener;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.panel.SwitchAddStudentListener;
import fr.iutfbleau.projetIHM2022FI2.interfaces.ReloadablePanel;
import fr.iutfbleau.projetIHM2022FI2.view.admin.AddStudentPanel;
import fr.iutfbleau.projetIHM2022FI2.view.custom.popupmenu.PopUpGroupe;
import fr.iutfbleau.projetIHM2022FI2.view.custom.tree.CustomJTree;


/**
 * La classe représentant le panneau contenant les groupes et le panneau pour ajouter/retirer des étudiants. 
 */
public class VoirGroupes extends JPanel implements ReloadablePanel {
   
    private CardLayout card;
    private CustomJTree tree;
    private PanelGroupe mainPane;

    /**
     * Le constructeur du panneau pour le compte administrateur.
     * @param tree l'arbre des groupes.
     * @param factory la factory qui crée gère les groupes
     */
    public VoirGroupes(CustomJTree tree, AbstractGroupeFactory factory){
        super();
        this.card = new CardLayout();
        this.tree = tree;
        this.setLayout(card);

        //Le listener des boutons de l'arbre
        JTreeButtonsListener listenerRoot = new JTreeButtonsListener(tree);
        
        //Le panneau pour ajouter ou retirer des étudiants
        AddStudentPanel addStudentPane = new AddStudentPanel();

        //Le listener des boutons du panneau
        AddStudentListener listenerAddStudentListener = new AddStudentListener(addStudentPane, factory);
        addStudentPane.setAddStudentListener(listenerAddStudentListener);

        //Le listener pour changer de panneau
        SwitchAddStudentListener listener = new SwitchAddStudentListener(addStudentPane,this,tree);
        addStudentPane.setSwitchListener(listener);

        //Le PopUpMenu pour ajouter/retirer/renommer les groupes
        PopUpGroupe pop = new PopUpGroupe(listenerRoot);
        this.tree.addMouseListener(new PopUpGroupeListener(pop));
        this.mainPane = new PanelGroupe(tree,listenerRoot,listener);

        this.add(mainPane,Constants.HOME);
        this.add(addStudentPane,Constants.ELEVE);
    }

    /**
     * Le constructeur du panneau pour les autres comptes.
     * @param tree l'arbre des groupes.
     */
    public VoirGroupes(CustomJTree tree){
        super(new BorderLayout());
        this.tree = tree;
        PanelGroupe mainPane = new PanelGroupe(tree);
        this.add(mainPane);
    }

    /**
     * Met à jour les informations du panneau
     * @see fr.iutfbleau.projetIHM2022FI2.interfaces.ReloadablePanel
     */
    @Override
    public void reload() {
        this.tree.reload();
    }

    /**
     * Permet de passer du panneau pour le déplacement, au panneau pour voir les groupes.
     * @param txt le panneau à afficher {@link fr.iutfbleau.projetIHM2022FI2.Constants}
     */
    public void switchPanel(String txt){
        this.card.show(this, txt);
    }

    /**
     * Retourne la liste des étudiants.
     * @return la liste des étudiants.
     */
    public JList<Etudiant> getList(){
        return this.mainPane.getList();
    }

    /**
     * Set un listener pour la liste pour un PopUpMenu
     * @param listener un MouseListener
     */
    public void setListener(MouseListener listener){
        this.mainPane.getList().addMouseListener(listener);
    }
}
