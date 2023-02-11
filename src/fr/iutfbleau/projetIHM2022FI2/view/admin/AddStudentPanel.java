package fr.iutfbleau.projetIHM2022FI2.view.admin;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import fr.iutfbleau.projetIHM2022FI2.Constants;
import fr.iutfbleau.projetIHM2022FI2.API.Etudiant;
import fr.iutfbleau.projetIHM2022FI2.API.Groupe;
import fr.iutfbleau.projetIHM2022FI2.controller.handlers.MyTransferHandler;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.buttons.AddStudentListener;
import fr.iutfbleau.projetIHM2022FI2.view.custom.renderer.CustomListRenderer;


 /**
  * Une classe représentant le panneau permettant d'ajouter ou retirer des étudiants.
  */

public class AddStudentPanel extends JSplitPane{
    
  
    private JList<Etudiant> listStudentRoot;
    private JList<Etudiant> listStudent;
    private DefaultListModel<Etudiant> listModelRoot;
    private DefaultListModel<Etudiant> listModel;
    private JLabel label;
    private JLabel labelPere;
    private List<Etudiant> newStudents;
    private List<Etudiant> removeStudents;
    private JButton home;
    private Groupe tmpGroupe;
    private Groupe tmpParent;
    private JButton addStudent;
    private JButton remove;
    private JButton confirmed;
    private JButton clear;
    private AddStudentListener listener;


    /**
     * Le constructeur.
     * Initialise un panel avec des boutons et 2 JList
     */
    public AddStudentPanel(){
        super(JSplitPane.HORIZONTAL_SPLIT);
        this.setResizeWeight(0.5);
        JPanel right = new JPanel(new BorderLayout());
        JPanel left = new JPanel(new BorderLayout());

        //Le bouton d'accueil
        this.home = new JButton("Accueil");
        this.home.setActionCommand(Constants.HOME);

        //Le bouton pour ajouter un étudiant
        addStudent = new JButton("Ajouter Etudiant");
        addStudent.setActionCommand(Constants.ADD);

        //Le bouton pour confirmer un changement
        confirmed = new JButton("Confirmer");
        confirmed.setActionCommand(Constants.CONFIRMED);

        //Le bouton pour retirer un étudiant
        remove = new JButton("Retirer Etudiant");
        remove.setActionCommand(Constants.REMOVE);

        //Le bouton pour annuler un changement
        clear = new JButton("Annuler");
        clear.setActionCommand(Constants.CANCEL);

        JPanel panelButton = new JPanel(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(5,0,0,0);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panelButton.add(addStudent,gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panelButton.add(confirmed,gbc);
      
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panelButton.add(remove,gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10,0,10,0);
        panelButton.add(clear,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0,0,0,0);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        panelButton.add(home,gbc);

        CustomListRenderer renderer = new CustomListRenderer();
        this.newStudents = new ArrayList<Etudiant>();
        this.removeStudents = new ArrayList<Etudiant>();

        this.listModel = new DefaultListModel<>();
        this.listModelRoot = new DefaultListModel<>();

        this.listStudent = new JList<>(listModel);
        this.listStudent.putClientProperty("List.isFileList", Boolean.TRUE);

        this.listStudentRoot = new  JList<>(listModelRoot);
        this.listStudentRoot.putClientProperty("List.isFileList", Boolean.TRUE);

        this.listStudent.setLayoutOrientation(JList.VERTICAL_WRAP);
        this.listStudent.setVisibleRowCount(0);

        this.listStudentRoot.setLayoutOrientation(JList.VERTICAL_WRAP);
        this.listStudentRoot.setVisibleRowCount(0);

        this.listStudent.setCellRenderer(renderer);
        this.listStudentRoot.setCellRenderer(renderer);
        
        this.listStudent.setDragEnabled(true);
        this.listStudentRoot.setDragEnabled(true);

        listStudent.setDropMode(DropMode.INSERT);
        listStudentRoot.setDropMode(DropMode.INSERT);

        JScrollPane rightScroll = new JScrollPane(listStudent);
        JScrollPane leftScroll = new JScrollPane(listStudentRoot);

        labelPere = new JLabel("",JLabel.CENTER);
        labelPere.setFont(UIManager.getFont("h1"));

        label = new JLabel("",JLabel.CENTER);
        label.setFont(UIManager.getFont("h1"));

        right.add(label, BorderLayout.NORTH);
        right.add(rightScroll, BorderLayout.CENTER);
        right.add(panelButton,BorderLayout.SOUTH);

        
        left.add(labelPere, BorderLayout.NORTH);
        left.add(leftScroll, BorderLayout.CENTER);
        
        this.setLeftComponent(left);
        this.setRightComponent(right);
    }

    /**
     * Retourne la JList des étudiants.
     * @return la Jlist des étudiants
     */
    public JList<Etudiant> getListStudent() {
        return listStudent;
    }

    /**
     * Retourne la JList des étudiants du groupe Parent.
     * @return la Jlist des étudiants du groupe Parent
     */
    public JList<Etudiant> getListStudentRoot() {
        return listStudentRoot;
    }

    /**
     * Retourne la JList des étudiants à ajouter.
     * @return la JList des étudiants à ajouter.
     */
    public List<Etudiant> getNewStudents() {
        return newStudents;
    }

    /**
     * Retourne la JList des étudiants à retirer.
     * @return la JList des étudiants à retirer.
     */
    public List<Etudiant> getRemoveStudents() {
        return removeStudents;
    }

    /**
     * Initialise les listeners des boutons.
     * @param listener le listener des boutons
     */
    public void setAddStudentListener(AddStudentListener listener){
        this.listener = listener;
        this.addStudent.addActionListener(listener);
        this.confirmed.addActionListener(listener);
        this.remove.addActionListener(listener);
        this.clear.addActionListener(listener);
        this.listStudent.setTransferHandler(new MyTransferHandler(false, listener));
        this.listStudentRoot.setTransferHandler(new MyTransferHandler(true, listener));
    }

    /**
     * Initialise le listener pour le bouton accueil.
     * @param listener le listener
     */
    public void setSwitchListener(ActionListener listener){
        this.home.addActionListener(listener);
    }

    /**
     * Permet de changer le groupe auquel ajouter ou retirer des étudiants.
     * Pour eviter de recréer un listener à chaque fois.
     * @param groupe le groupe voulu
     */
    public void setGroupe(Groupe groupe){
        this.listener.setGroupe(groupe);
    }

    /**
     * Permet de changer la liste des étudiants du groupe parent et du groupe choisi.
     * @param parent le groupe parent.
     * @param groupe le groupe choisi.
     */
    public void choice(Groupe parent, Groupe groupe){
        this.tmpParent = parent;
        this.tmpGroupe = groupe;
        this.label.setText(groupe.getName());
        this.labelPere.setText(parent.getName());
        this.newStudents.clear();
        this.removeStudents.clear();
        listModel.removeAllElements();
        listModelRoot.removeAllElements();
        Set<Etudiant> parentEtudiant = new LinkedHashSet<Etudiant>(parent.getEtudiants());
        parentEtudiant.removeAll(groupe.getEtudiants());
        for(Etudiant etu : groupe.getEtudiants())
            this.listModel.addElement(etu);
        for(Etudiant etu : parentEtudiant)
            this.listModelRoot.addElement(etu);
        this.revalidate();
    }

    /**
     * Permet d'annuler un changement
     */
    public void cancel(){
        choice(this.tmpParent, this.tmpGroupe);
    }
}
