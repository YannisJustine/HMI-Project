package fr.iutfbleau.projetIHM2022FI2.view.etudiant;

import fr.iutfbleau.projetIHM2022FI2.Constants;
import fr.iutfbleau.projetIHM2022FI2.API.*;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.buttons.ChangementButtonListener;
import fr.iutfbleau.projetIHM2022FI2.interfaces.ReloadablePanel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;
import javax.swing.Scrollable;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;

/**
 * Classe qui représente le panneau qui affiche les changements
 */
public class ShowChangementPanels extends JPanel implements ReloadablePanel, Scrollable{
    private ChangementButtonListener cl;
    private boolean isAdmin;

    /**
     * Constructeur
     * @param isAdmin l'utilisateur est-il un administrateur ?
     */
    public ShowChangementPanels(boolean isAdmin) {
        super();
        this.isAdmin = isAdmin;
        this.setLayout(new GridBagLayout());
        this.setOpaque(false);
    }

    /**
     * Permet de définir le Listener des boutons présent sur les changements
     * @param cl le listener a définir
     * @see fr.iutfbleau.projetIHM2022FI2.controller.listeners.buttons.ChangementButtonListener
     * @see fr.iutfbleau.projetIHM2022FI2.view.etudiant.ChangementPanel#addButtonsListener(java.awt.event.ActionListener)
     */
    public void setListener(ChangementButtonListener cl) {
        this.cl = cl;
        this.reload();
    }

    /**
     * Met à jour la liste des changements
     * @see fr.iutfbleau.projetIHM2022FI2.interfaces.ReloadablePanel
     */
    public void reload() {
        GridBagConstraints gbc = new GridBagConstraints();
        int x = 0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(15,15,15,15);
        gbc.fill = GridBagConstraints.BOTH;

        this.removeAll();
        for(Changement c : this.cl.getAllChangements()) {
            ChangementPanel vc = new ChangementPanel(c,this.isAdmin);
            gbc.gridy = x;
            vc.addButtonsListener(this.cl);  
            if(!isAdmin && c.getA().getSize() < c.getB().getSize() && Constants.loggedEtudiant != c.getEtu()) //Demande de type 2
                continue;
            this.add(vc,gbc);
            x++;
        }
        this.revalidate();
        this.repaint();
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        
        return null;
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        
        return 10;
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        
        return 10;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        
        return false;
    }

}
