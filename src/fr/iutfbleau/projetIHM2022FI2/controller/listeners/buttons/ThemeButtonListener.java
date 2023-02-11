package fr.iutfbleau.projetIHM2022FI2.controller.listeners.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import fr.iutfbleau.projetIHM2022FI2.Fenetre;

/**
 * Classe représentant le listener des JMenuItems de la JMenuBar de la fenêtre.
 */
public class ThemeButtonListener implements ActionListener {

    /**
     * Constructeur. 
     * Le listener est composé d'une liste de {@link fr.iutfbleau.projetIHM2022FI2.Themes Themes}
     */
    public ThemeButtonListener(){
    }

    /**
     * Permet de changer le thème avec une animation <a  href="https://www.formdev.com/flatlaf/themes/"> FlatLaf </a>
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem item = (JMenuItem) e.getSource();
        JPopupMenu pop = (JPopupMenu) item.getParent();
        JMenu menu = (JMenu) pop.getInvoker();
        for( java.awt.Component i : menu.getMenuComponents()){
            if(!i.equals(item))
                i.setEnabled(true);
        }
        item.setEnabled(false);
        String name = item.getText();
        Fenetre.updateTheme(name);
    }
} 

