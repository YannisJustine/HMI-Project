package fr.iutfbleau.projetIHM2022FI2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.json.Json;
import com.formdev.flatlaf.util.LoggingFacade;

import fr.iutfbleau.projetIHM2022FI2.API.AbstractChangementFactory;
import fr.iutfbleau.projetIHM2022FI2.API.AbstractGroupeFactory;
import fr.iutfbleau.projetIHM2022FI2.controller.listeners.buttons.ThemeButtonListener;
import fr.iutfbleau.projetIHM2022FI2.view.panel.PanelChoix;
import fr.iutfbleau.projetIHM2022FI2.view.panel.PanelChoixEleves;

/**
 * Classe représentant la fenêtre principale 
 */
public class Fenetre extends JFrame {

    private JPanel panel;
    private JMenuBar menuBar;
    public static List<Themes> bundledThemes = new ArrayList<>();;
    

    /***
     * Initialise la fenêtre principale.
     * @param agf un AbstractGroupeFactory pour créer tous les panneaux.
     * @param acf un AbstractChangementFactory pour créer tous les panneaux.
     */
    public Fenetre(AbstractGroupeFactory agf,AbstractChangementFactory acf){
        super("Gestion de groupes");

        //Change l'icone de la fenêtre 
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        FlatSVGIcon img = new FlatSVGIcon("res/img/logo.svg",50,50,loader);
        this.setIconImage(img.getImage());

        CardLayout card = new CardLayout();
        this.getContentPane().setLayout(card);

        //La barre de menu
        menuBar = new JMenuBar();
        menuBar.add(Box.createHorizontalGlue());

        //Un menu pour choisir le theme
        JMenu menu = new JMenu("Thèmes");
        menu.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        ThemeButtonListener listener = new ThemeButtonListener();

        //Fonction qui load tous les listeners,items et la liste de Themes
        this.loadBundledThemes(menu, listener);

        //Ajoute la barre de menu
        menuBar.add(menu);
        this.setJMenuBar(menuBar); 

        //Le panel pour choisir entre ELEVE, PROF et ADMIN
        JPanel panelChoix = new PanelChoix(this,agf,acf);

        //Le panel pour choisir un ELEVE
        JPanel panelChoixEleve = new PanelChoixEleves(this,agf,acf);

        this.add(panelChoix,Constants.HOME);
        this.add(panelChoixEleve,Constants.ELEVE);
     
        //Le panel contenant le JTabbedPane 
        this.panel = new JPanel(new BorderLayout());
        this.add(panel, Constants.PANEL);
        
        //Les dimensions de la fenêtre
        this.setMinimumSize(new Dimension(1280,720));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Permet de remplacer le JTabbedPane du panel principal.
     * Cela est utile lors de la deconnexion
     * @param tabbed le JTabbedPane à placer
     */
    public void change(JTabbedPane tabbed){
        this.panel.removeAll();
        this.panel.add(tabbed);
    }

    /**
     * Permet de rendre visible la fenêtre principale.
     */
    public void visible(){
        this.setVisible(true);
    }

    /**
     * Permet d'ajouter un JMenu à la barre de menu existante.
     * @param menu le JMenu à ajouter 
     */
    public void addMenu(JMenu menu){
        menuBar.add(menu);
    }

    /**
     * Permet de charger tous les thèmes.
     * @param menu le JMenu auquel ajouter tous les noms des thèmes.
     * @param lisBoxTheme le listener à ajouter à chaque JMenuItem.
     */
    @SuppressWarnings( "unchecked" )
    private void loadBundledThemes(JMenu menu, ThemeButtonListener listenerBoxTheme) {

		// load le fichier json
		Map<String, Object> json;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
	    try( Reader reader = new InputStreamReader( loader.getResourceAsStream( "res/themes/themes.json" ), StandardCharsets.UTF_8 ) ) {
	    		json = (Map<String, Object>) Json.parse( reader );
		} catch( IOException ex ) {
			LoggingFacade.INSTANCE.logSevere( null, ex );
			return;
		}
		// Cherche tous les noms et chemin et ajoute au JMenu
		for( Map.Entry<String, Object> e : json.entrySet() ) {
			Map<String, String> value = (Map<String, String>) e.getValue();
			String n = value.get( "name" );
            String name = n.replace("_", " ");
            String URL = value.get("URL");
            JMenuItem  item = new JMenuItem(name);
            item.addActionListener(listenerBoxTheme);
            menu.add(item);
			bundledThemes.add(new Themes(name, URL));
		}
        menu.getItem(0).doClick();
	}

    public static void updateTheme(String name) {
        String URL = null;
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        //Cherche dans la liste le theme correspondant au nom
        for(Themes t : bundledThemes){
            if(t.getName() == name){
                URL = t.getURL();
                break;
            }
        }   
        if(URL != null){
            try {
                FlatAnimatedLafChange.showSnapshot();
                IntelliJTheme.setup(loader.getResourceAsStream(URL));
                FlatLaf.updateUI();
                FlatAnimatedLafChange.hideSnapshotWithAnimation();
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            } 
        }
    }
}
