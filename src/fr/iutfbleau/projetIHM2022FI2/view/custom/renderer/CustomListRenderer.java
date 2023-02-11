package fr.iutfbleau.projetIHM2022FI2.view.custom.renderer;

import javax.swing.DefaultListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.*;

import fr.iutfbleau.projetIHM2022FI2.Constants;
import fr.iutfbleau.projetIHM2022FI2.API.Etudiant;
import fr.iutfbleau.projetIHM2022FI2.API.Groupe;

import com.formdev.flatlaf.extras.FlatSVGIcon;

/**
 * Classe qui change l'affichage des éléments d'une liste (Groupe ou Étudiant)
 */
public class CustomListRenderer extends DefaultListCellRenderer{

    private static ImageIcon groupes;
    private static ImageIcon eleve;
    private boolean tooltip;

    /**
     * Charge les images
     */
    static{
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        eleve = new FlatSVGIcon("res/img/feuille.svg",25,25,loader);
        groupes =  new FlatSVGIcon("res/img/noeud.svg",25,25,loader);
    }

    /**
     * Constructeur
     * @param tooltip les tooltips doivent être activé ? 
     */
    public CustomListRenderer(boolean tooltip) {
        super();
        this.tooltip = tooltip;
    }

    /**
     * Constructeur.
     * Les tooltips sont activés
     */
    public CustomListRenderer() {
        this(true);
    }

    @Override
   /**
    * Si l'élément est un étudiant, on afiche son nom en majuscule puis son prénom.
    *   Le tooltip affiche son nom, prénom et identifiant.
    * Si l'élément est un groupe on affiche son nom et sa taille entre parenthèse.
    *   Le tooltip affiche le nom de père.
    */
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof Etudiant) {
            Etudiant etudiant = (Etudiant)value;
            setIcon(CustomListRenderer.eleve);
            setText(etudiant.getNom().toUpperCase() + " " + etudiant.getPrenom());
            if(tooltip)
                setToolTipText(etudiant.monPrint());
            if(etudiant == Constants.loggedEtudiant && !isSelected){
                setBackground(UIManager.getColor("Component.focusColor"));
                setForeground(Color.black);
            }   
        }
        
        else if (value instanceof Groupe) {
            Groupe groupe = (Groupe)value;
            setIcon(CustomListRenderer.groupes);
            setText(groupe.getName() + " (" + groupe.getSize() + ")");
            if(tooltip)
                setToolTipText("Père : " + groupe.getPointPoint().getName());
        }
        setBorder(new EmptyBorder(5,5,5,5));
        return this;
    }
    
}
