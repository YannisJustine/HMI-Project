package fr.iutfbleau.projetIHM2022FI2.view.admin;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import fr.iutfbleau.projetIHM2022FI2.Constants;
import fr.iutfbleau.projetIHM2022FI2.API.Etudiant;
import fr.iutfbleau.projetIHM2022FI2.API.Groupe;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.buttons.MoveStudentListener;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.comboBox.ComboJTreeListener;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.comboBox.MoveComboBoxListener;
import fr.iutfbleau.projetIHM2022FI2.interfaces.ComboBoxGroupeInterface;
import fr.iutfbleau.projetIHM2022FI2.interfaces.ReloadablePanel;
import fr.iutfbleau.projetIHM2022FI2.view.custom.renderer.CustomJTreeRenderer;
import fr.iutfbleau.projetIHM2022FI2.view.custom.renderer.CustomListRenderer;


/**
 * Classe représentant le panneau pour déplacer un étudiant.
 */
public class MoveStudentPanel extends JPanel implements ComboBoxGroupeInterface, ReloadablePanel{
    
    private JComboBox<Groupe> groupeDepart;
    private JComboBox<Groupe> groupeArrivee;
    private JComboBox<Etudiant> studentlist;
    private JButton accueilButton;
    private JButton confirm;

    /**
     * Le constructeur du panneau.
     */
    public MoveStudentPanel(){
        super();
        this.setLayout(new GridBagLayout());
        this.groupeArrivee = new JComboBox<>();
        this.groupeDepart = new JComboBox<>();
        this.studentlist = new JComboBox<>();
        this.accueilButton = new JButton("Accueil");
        this.accueilButton.setActionCommand(Constants.HOME);

        JLabel labelGroupeDepart = new JLabel("Groupe de départ",JLabel.CENTER);
        JLabel labelGroupeArrive = new JLabel("Groupe d'arrivée",JLabel.CENTER);
        JPanel panelGroupeDepart = new JPanel(new BorderLayout());
        JPanel panelGroupeArrive = new JPanel(new BorderLayout());

        panelGroupeDepart.add(labelGroupeDepart, BorderLayout.NORTH);
        panelGroupeDepart.add(groupeDepart, BorderLayout.CENTER);

        panelGroupeArrive.add(labelGroupeArrive, BorderLayout.NORTH);
        panelGroupeArrive.add(groupeArrivee, BorderLayout.CENTER);

        this.groupeArrivee.setRenderer(new CustomListRenderer(false));
        this.groupeDepart.setRenderer(new CustomListRenderer(false));
        this.studentlist.setRenderer(new CustomListRenderer());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        
        this.add(studentlist,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        this.add(panelGroupeDepart,gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        
        this.add(panelGroupeArrive,gbc);

        this.confirm = new JButton("Confirmer le déplacement");

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        this.add(this.confirm,gbc);


        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        FlatSVGIcon fleche =  new FlatSVGIcon("res/img/fleche.svg",80,56,loader);
        JLabel changementPanel = new JLabel(fleche,JLabel.CENTER);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(changementPanel,gbc);

        JTree tree = new JTree();
        tree.setCellRenderer(new CustomJTreeRenderer());
        tree.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        tree.setEnabled(false);

        JTree tree2 = new JTree();
        tree2.setCellRenderer(new CustomJTreeRenderer());
        tree2.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        tree2.setEnabled(false);

        this.groupeDepart.addItemListener(new ComboJTreeListener(tree));
        this.groupeArrivee.addItemListener(new ComboJTreeListener(tree2));

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0,20,0,0);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(tree,gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0,0,0,20);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        this.add(tree2,gbc);

    
    }

    public void setComboBoxListener(MoveComboBoxListener listener){
        this.studentlist.addItemListener(listener);
        this.groupeDepart.addItemListener(listener);
    }

    public void setGroupeDepart(Set<Groupe> groupe){
        this.groupeDepart.removeAllItems();
        for(Groupe g : groupe){
            this.groupeDepart.addItem(g);
        }
    }

    public void setGroupeArrivee(Set<Groupe> groupe){
        this.groupeArrivee.removeAllItems();
        for(Groupe g : groupe){
            this.groupeArrivee.addItem(g);
        }
    }

    public void setEtudiant(Set<Etudiant> etudiants){
        if(!etudiants.isEmpty()){
            for(Etudiant s : etudiants){
                this.studentlist.addItem(s);
            }
        }
    }

    public void setSelectedEtudiant(Etudiant etu){
        this.studentlist.setSelectedItem(etu);
    }

    public void setMoveStudentListener(MoveStudentListener listener){
        this.confirm.addActionListener(listener);
    }

    public Etudiant getEtudiant(){
        return (Etudiant) this.studentlist.getSelectedItem();
    }

    public Groupe getGroupeDepart(){
        return (Groupe) this.groupeDepart.getSelectedItem();
    }

    public Groupe getGroupeArrivee(){
        return (Groupe) this.groupeArrivee.getSelectedItem();
    }

    @Override
    public void reload(){
        Etudiant etudiant = (Etudiant) this.studentlist.getSelectedItem();
        this.studentlist.setSelectedItem(null);
        this.studentlist.setSelectedItem(etudiant);
    }

}
