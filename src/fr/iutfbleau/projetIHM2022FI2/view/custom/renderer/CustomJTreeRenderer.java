package fr.iutfbleau.projetIHM2022FI2.view.custom.renderer;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import fr.iutfbleau.projetIHM2022FI2.API.Groupe;
import fr.iutfbleau.projetIHM2022FI2.API.TypeGroupe;

/**
 * Classe représentant le Renderer du JTree 
 */
public class CustomJTreeRenderer extends DefaultTreeCellRenderer {

    private static FlatSVGIcon closeIcon;
    private static FlatSVGIcon leafIcon;
    private static FlatSVGIcon openIcon;
    private static FlatSVGIcon partition;
    private static FlatSVGIcon partition_ouvert;
    /**
     * Charge les icones
     */
    static{
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        closeIcon = new FlatSVGIcon("res/img/noeud.svg",25,25,loader);
        leafIcon =  new FlatSVGIcon("res/img/feuille.svg",25,25,loader);
        openIcon =  new FlatSVGIcon("res/img/ouvert.svg",25,25,loader);
        partition =  new FlatSVGIcon("res/img/partition.svg",25,25,loader);
        partition_ouvert =  new FlatSVGIcon("res/img/partition_ouvert.svg",25,25,loader);
    }

    /**
     * le constructeur.
     * Initialise les icones
     */
    public CustomJTreeRenderer(){
        super();
        setLeafIcon(leafIcon);
    }

    @Override
    /**
     * affiche le nom du groupe et set un ToolTip avec son nombre d'élèves.
     */
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                  boolean leaf, int row, boolean hasFocus) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        if (node.getUserObject() instanceof Groupe) {
            Groupe groupe = (Groupe) node.getUserObject();
            if(groupe.getType().equals(TypeGroupe.PARTITION)) {
                setOpenIcon(partition_ouvert);
                setClosedIcon(partition);
            }
            else {
                setOpenIcon(openIcon);
                setClosedIcon(closeIcon);
            }
            //https://stackoverflow.com/questions/14096725/jtree-set-custom-open-closed-icons-for-individual-groups
            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row,hasFocus);

            this.setText(groupe.getName());
            this.setToolTipText("Nombre d'élèves : " + groupe.getSize() + "/ " + groupe.getMax());
        }
        return this;
    }
}
