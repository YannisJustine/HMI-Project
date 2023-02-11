package fr.iutfbleau.projetIHM2022FI2;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import com.formdev.flatlaf.util.SystemInfo;
import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import fr.iutfbleau.projetIHM2022FI2.API.AbstractChangementFactory;
import fr.iutfbleau.projetIHM2022FI2.API.AbstractGroupeFactory;
import fr.iutfbleau.projetIHM2022FI2.API.Etudiant;
import fr.iutfbleau.projetIHM2022FI2.MNP.AbstractChangementFactoryNP;
import fr.iutfbleau.projetIHM2022FI2.MNP.AbstractGroupeFactoryNP;
import fr.iutfbleau.projetIHM2022FI2.MNP.EtudiantNP;
import fr.iutfbleau.projetIHM2022FI2.MP.AbstractGroupeFactoryP;


public class Main{


    public static void main(String[] args) {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            IntelliJTheme.setup( loader.getResourceAsStream("res/themes/Atom_One_Dark_Contrast.theme.json"));
            if( SystemInfo.isLinux ) {
                // enable custom window decorations
                JFrame.setDefaultLookAndFeelDecorated( true );
                JDialog.setDefaultLookAndFeelDecorated( true );
            }
            UIManager.put("TitlePane.centerTitle", true);
            UIManager.put("TitlePane.iconSize", new Dimension(25,25));
            UIManager.put("TitlePane.unifiedBackground", false);
            UIManager.put("Tree.rightChildIndent", 10);
            UIManager.put("Tree.leftChildIndent", 10);
            UIManager.put("Tree.rowHeight", 30);
            UIManager.put( "Label.font", UIManager.getFont( "h2.font" ) );
            Font font = UIManager.getFont("defaultFont");
            font = font.deriveFont((float) font.getSize() + 2);
            UIManager.put("defaultFont", font);
            FlatSVGIcon.ColorFilter.getInstance()
            .add( Color.black, Color.white , Color.black)
            .add( Color.white, Color.black , Color.white);
               

        } catch (Exception e) {

        }   

        AbstractGroupeFactory agf;
        AbstractChangementFactory acf;

        Object[] choix={"Modèle persistant","Modèle non persistant","Annuler"};
        int res = JOptionPane.showOptionDialog(null, "Choisir le modèle voulu", "Choix du modèle", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, choix, choix[0]);
        
        switch (res) {
            case 0:
                agf = new AbstractGroupeFactoryP();
                break;

            case 1:
                agf = new AbstractGroupeFactoryNP("BUT2 FI", 15, 92);
                populateGroupe(agf);
                break;
        
            default:
                return;
        }
        acf = new AbstractChangementFactoryNP(agf);
        
        Fenetre c = new Fenetre(agf,acf);
        c.visible();
    }
    
    public static void populateGroupe(AbstractGroupeFactory agf) {

        Etudiant e1=new EtudiantNP("Bade","Guy");
        Etudiant e2=new EtudiantNP("Boutet","Rémi");
        Etudiant e3=new EtudiantNP("Amoussa","Rayan");
        Etudiant e4=new EtudiantNP("Bernard","Théo");
        Etudiant e5=new EtudiantNP("Brezeky","Ethan");
        Etudiant e6=new EtudiantNP("Bailly","Joris");
        Etudiant e7=new EtudiantNP("Besson","Romain");
        Etudiant e8=new EtudiantNP("Chaaba","Hamza");
        Etudiant e9=new EtudiantNP("Bazillio","Titouan");
        Etudiant e10=new EtudiantNP("Bouarroudj","Yanis");

        
        
        Etudiant e11=new EtudiantNP("Charbonnel","Julien");
        Etudiant e12=new EtudiantNP("Chaigneau","Mathis");
        Etudiant e13=new EtudiantNP("Brinet","Félix");
        Etudiant e14=new EtudiantNP("De Sousa Alves","Etann");
        Etudiant e15=new EtudiantNP("Dagorne","Thomas");
        Etudiant e16=new EtudiantNP("Catanese","Simon");
        Etudiant e17=new EtudiantNP("Fouché","Joffrey");
        Etudiant e18=new EtudiantNP("Daniel","Gaël");
        Etudiant e19=new EtudiantNP("Dos Santos","Adrien");
        Etudiant e20=new EtudiantNP("Hamrouni","Yassine");


        
        Etudiant e21=new EtudiantNP("Descamps","Victor");
        Etudiant e22=new EtudiantNP("Gobert","Claire");
        Etudiant e23=new EtudiantNP("Hani","Ismaïl");
        Etudiant e24=new EtudiantNP("Horville","Ewen");
        Etudiant e25=new EtudiantNP("Guettaf","Adem");
        Etudiant e26=new EtudiantNP("Koffi","Ryan");
        Etudiant e27=new EtudiantNP("Justine","Lucas");
        Etudiant e28=new EtudiantNP("Haïssous","Kayyissa");
        Etudiant e29=new EtudiantNP("Lefeuvre","Ethan");
        Etudiant e30=new EtudiantNP("Justine","Yannis");

        
        
        Etudiant e31=new EtudiantNP("Mars","Faten");
        Etudiant e32=new EtudiantNP("Mardaci","Kamil");
        Etudiant e33=new EtudiantNP("Letabi","Valmont");
        Etudiant e34=new EtudiantNP("Sid Athmane","Fares");
        Etudiant e35=new EtudiantNP("Nevejeans","Baptiste");
        Etudiant e36=new EtudiantNP("Martins","Clément");
        Etudiant e37=new EtudiantNP("Zemmouri","Benamar");
        Etudiant e38=new EtudiantNP("Wattin","Lucas");
        Etudiant e39=new EtudiantNP("Moulin","Kilian");
        Etudiant e40=new EtudiantNP("Soussi","Yuness");

        
        
        Etudiant e41=new EtudiantNP("Monin","Tom");
        Etudiant e42=new EtudiantNP("Brenet","Hugo");

        agf.addToGroupe(agf.getPromotion(),e1);
        agf.addToGroupe(agf.getPromotion(),e2);
        agf.addToGroupe(agf.getPromotion(),e3);
        agf.addToGroupe(agf.getPromotion(),e4);
        agf.addToGroupe(agf.getPromotion(),e5);
        agf.addToGroupe(agf.getPromotion(),e6);
        agf.addToGroupe(agf.getPromotion(),e7);
        agf.addToGroupe(agf.getPromotion(),e8);
        agf.addToGroupe(agf.getPromotion(),e9);
        agf.addToGroupe(agf.getPromotion(),e10);


        agf.addToGroupe(agf.getPromotion(),e11);
        agf.addToGroupe(agf.getPromotion(),e12);
        agf.addToGroupe(agf.getPromotion(),e13);
        agf.addToGroupe(agf.getPromotion(),e14);
        agf.addToGroupe(agf.getPromotion(),e15);
        agf.addToGroupe(agf.getPromotion(),e16);
        agf.addToGroupe(agf.getPromotion(),e17);
        agf.addToGroupe(agf.getPromotion(),e18);
        agf.addToGroupe(agf.getPromotion(),e19);
        agf.addToGroupe(agf.getPromotion(),e20);

        
        
        agf.addToGroupe(agf.getPromotion(),e21);
        agf.addToGroupe(agf.getPromotion(),e22);
        agf.addToGroupe(agf.getPromotion(),e23);
        agf.addToGroupe(agf.getPromotion(),e24);
        agf.addToGroupe(agf.getPromotion(),e25);
        agf.addToGroupe(agf.getPromotion(),e26);
        agf.addToGroupe(agf.getPromotion(),e27);
        agf.addToGroupe(agf.getPromotion(),e28);
        agf.addToGroupe(agf.getPromotion(),e29);
        agf.addToGroupe(agf.getPromotion(),e30);
        agf.addToGroupe(agf.getPromotion(),e31);
        agf.addToGroupe(agf.getPromotion(),e32);
        agf.addToGroupe(agf.getPromotion(),e33);
        agf.addToGroupe(agf.getPromotion(),e34);
        agf.addToGroupe(agf.getPromotion(),e35);
        agf.addToGroupe(agf.getPromotion(),e36);
        agf.addToGroupe(agf.getPromotion(),e37);
        agf.addToGroupe(agf.getPromotion(),e38);
        agf.addToGroupe(agf.getPromotion(),e39);

        
        
        agf.addToGroupe(agf.getPromotion(),e40);
        agf.addToGroupe(agf.getPromotion(),e41);
        agf.addToGroupe(agf.getPromotion(),e42);

        agf.createPartition(agf.getPromotion(), "TD",3);
    }
}
