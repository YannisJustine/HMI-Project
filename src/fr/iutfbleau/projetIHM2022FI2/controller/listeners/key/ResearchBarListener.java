package fr.iutfbleau.projetIHM2022FI2.controller.listeners.key;

import fr.iutfbleau.projetIHM2022FI2.API.AbstractGroupeFactory;
import fr.iutfbleau.projetIHM2022FI2.API.Etudiant;
import javax.swing.DefaultListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Set;

/**
 * DocumentListener de la barre de recherche
 * @see fr.iutfbleau.projetIHM2022FI2.view.ResearchStudents
 */
public class ResearchBarListener implements DocumentListener {
    
    private DefaultListModel<Etudiant> listModel;
    private AbstractGroupeFactory agf;
    /**
     * Constructeur
     * @param listModel le modèle de la {@link fr.iutfbleau.projetIHM2022FI2.view.ResearchStudents#getListModel() liste}
     * @param agf la factory qui crée des groupes
     */
    public ResearchBarListener(DefaultListModel<Etudiant> listModel, AbstractGroupeFactory agf) {
       this.listModel = listModel;
        this.agf = agf;
    }

    /**
     * Lorsque l'on écrit dans la barre de recherche, affiche tous les étudiants dont le nom correspond
     * Si aucun étudiants ne correspond, supprime tous les accents
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        Document d = e.getDocument();
        if(d.getLength() == 0) {
            listModel.removeAllElements();
        } 
        else {
            try {
                String text = d.getText(0, d.getLength()).toLowerCase().trim();
                Set<Etudiant> etudiants = agf.getEtudiants(text);
                if(etudiants.size() == 0) { // Si sélection vide, suppression des accents
                    text = Normalizer.normalize(text, Form.NFD).replaceAll("[^\\p{ASCII}]", "");
                    etudiants = agf.getEtudiants(text);
                }
                listModel.removeAllElements();
                for(Etudiant etu : etudiants)
                    listModel.addElement(etu);
            } catch (Exception e1) {
                
            }
            
        }
        
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        changedUpdate(e);
        
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        changedUpdate(e);
        
    }
}
