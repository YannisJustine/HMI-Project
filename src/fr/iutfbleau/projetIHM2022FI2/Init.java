package fr.iutfbleau.projetIHM2022FI2;

import java.awt.ComponentOrientation;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.icons.FlatMenuArrowIcon;

import fr.iutfbleau.projetIHM2022FI2.API.AbstractChangementFactory;
import fr.iutfbleau.projetIHM2022FI2.API.AbstractGroupeFactory;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.buttons.Deconnexion;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.buttons.MoveStudentListener;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.comboBox.MoveComboBoxListener;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.jlist.PopUpStudentButtonListener;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.jlist.ResearchListMouseListener;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.jlist.PopUpStudentListener;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.key.ResearchBarListener;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.panel.TabbedPaneListener;
import fr.iutfbleau.projetIHM2022FI2.view.ResearchStudents;
import fr.iutfbleau.projetIHM2022FI2.view.admin.MoveStudentPanel;
import fr.iutfbleau.projetIHM2022FI2.view.custom.popupmenu.PopUpStudent;
import fr.iutfbleau.projetIHM2022FI2.view.custom.tree.CustomJTree;
import fr.iutfbleau.projetIHM2022FI2.view.etudiant.VoirChangements;
import fr.iutfbleau.projetIHM2022FI2.view.panel.VoirGroupes;


/**
 * Classe qui initialise le tabbedPane en fonction du choix
 */
public class Init {

    /**
     * Le tabbedPane qui est initialisé 
     */
    private JTabbedPane tabbedPane;

    /**
     * Le contructeur de la classe.
     * @param choix un choix entre ELEVE,PROF,ADMIN 
     * @param agf l'AbstractGroupeFactory permettant d'initialisé toutes les autres classes
     * @param acf l'AbstractChangementFactory permettant d'initialisé toutes les autres classes
     * @param fenetre la classe qui contient la fenetre et le cardLayout.
     */
    //Nous avons préférer donner en paramètre la classe contenant la JFrame et le cardLayout plutôt que de les donner en paramètre
    public Init(String choix , AbstractGroupeFactory agf, AbstractChangementFactory acf, Fenetre fenetre){
        //Le JTree
        CustomJTree tree = new CustomJTree(agf); 
        VoirGroupes groupePane = null;
        Fenetre f = fenetre;
        
        this.tabbedPane = new JTabbedPane();
        //Un listener qui permet de reload chaque Panel contenu dans le TabbedPane
        tabbedPane.addChangeListener(new TabbedPaneListener());

        JMenu menu = new JMenu();
        menu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JMenuItem e1 = new JMenuItem("Déconnexion");
        //Une icone de flèche pour la deconnexion
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        e1.setIcon(new FlatSVGIcon("res/img/deco.svg",25,25,loader));
        //Permet de se déconnecter en appuyant sur ECHAP
        e1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0));
        //Un listener qui permet de se déconnecter
        e1.addActionListener(new Deconnexion(f));
            
        switch (choix) {
            case Constants.ELEVE:{
                //Le panel permettant de voir les groupes
                groupePane = new VoirGroupes(tree);
                tabbedPane.addTab("Groupes", groupePane);

                //Le panel permettant de voir et d'effectuer les changements
                VoirChangements changementPane = new VoirChangements(acf, false);
                tabbedPane.addTab("Changement", changementPane); 

                //Affiche le nom de l'étudiant connecté dans le MenuBar
                menu.setText(Constants.loggedEtudiant.getNom().toUpperCase() + " " + Constants.loggedEtudiant.getPrenom() );

                break;
            }
                
            case Constants.PROF:{
                //Le panel permettant de voir les groupes
                groupePane = new VoirGroupes(tree);
                tabbedPane.addTab("Groupes", groupePane);

                //Le panel permettant de faire des recherches d'élèves 
                ResearchStudents recherche = new ResearchStudents();
                recherche.setResearchBarListener(new ResearchBarListener(recherche.getListModel(), agf));
                recherche.addListMouseListener(new ResearchListMouseListener(agf));
                tabbedPane.addTab("Rechercher un élève", recherche);

                //Affiche Professeur dans le MenuBar
                menu.setText("Professeur");
                break;
            }

            case Constants.ADMIN:{
                //Le panel permettant de voir les groupes
                groupePane = new VoirGroupes(tree, agf); 
                //Listener des boutons du JPopUpMenu
                ActionListener listenerButton = new PopUpStudentButtonListener(tabbedPane, groupePane.getList());
                //Le JPopUpMenu pour déplacer un étudiant
                JPopupMenu pop = new PopUpStudent(listenerButton);
                //Listener qui écoute un clic-droit sur un élève
                MouseListener listener = new PopUpStudentListener(pop);
                groupePane.setListener(listener);
                tabbedPane.addTab("Groupes", groupePane);

                //Le panel permettant de voir les changements
                VoirChangements changementPane = new VoirChangements(acf, true);
                tabbedPane.addTab("Changement", changementPane);  
                
                //Le panel permettant de faire des recherches d'élèves 
                ResearchStudents recherche = new ResearchStudents();
                recherche.setResearchBarListener(new ResearchBarListener(recherche.getListModel(), agf));
                recherche.addListMouseListener(new ResearchListMouseListener(agf));
                listenerButton = new PopUpStudentButtonListener(tabbedPane, recherche.getList());
                //Le JPopUpMenu pour déplacer un étudiant
                pop = new PopUpStudent(listenerButton);
                //Listener qui écoute un clic-droit sur un élève
                listener = new PopUpStudentListener(pop);
                recherche.addListMouseListener(listener);
                tabbedPane.addTab("Rechercher un élève", recherche);

                //Le panel permettant de déplacer un étudiant              
                MoveStudentPanel moveStudentPanel = new MoveStudentPanel();
                MoveComboBoxListener listenerComboMove = new MoveComboBoxListener(moveStudentPanel, agf);
                moveStudentPanel.setComboBoxListener(listenerComboMove);
                MoveStudentListener listenerButtonMove = new MoveStudentListener(moveStudentPanel, agf);
                moveStudentPanel.setMoveStudentListener(listenerButtonMove);
                moveStudentPanel.setEtudiant(agf.getPromotion().getEtudiants());
                tabbedPane.addTab("Déplacer Etudiant", moveStudentPanel); 

                //Affiche Administrateur dans le MenuBar
                menu.setText("Administrateur");
                break;
            }
        }
        menu.add(e1);
        f.addMenu(menu);
    }

    public JTabbedPane getTabbedPane(){
        return this.tabbedPane;
    }
}
