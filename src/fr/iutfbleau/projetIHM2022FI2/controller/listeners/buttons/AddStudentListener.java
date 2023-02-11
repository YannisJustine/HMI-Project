package fr.iutfbleau.projetIHM2022FI2.controller.listeners.buttons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import fr.iutfbleau.projetIHM2022FI2.Constants;
import fr.iutfbleau.projetIHM2022FI2.API.AbstractGroupeFactory;
import fr.iutfbleau.projetIHM2022FI2.API.Etudiant;
import fr.iutfbleau.projetIHM2022FI2.API.Groupe;
import fr.iutfbleau.projetIHM2022FI2.view.admin.AddStudentPanel;
import fr.iutfbleau.projetIHM2022FI2.view.custom.worker.CustomWorker;
import fr.iutfbleau.projetIHM2022FI2.controller.handlers.MyTransferHandler;
import fr.iutfbleau.projetIHM2022FI2.interfaces.SwingWorkerInterface;


/**
 * Classe représentant le listener des boutons pour ajouter/retirer un étudiant
 */
public class AddStudentListener implements ActionListener, SwingWorkerInterface{

    private JList<Etudiant> listParent;
    private JList<Etudiant> list;
    private List<Etudiant> newStudents;
    private List<Etudiant> removeStudents;
    private AddStudentPanel panel;
    private AbstractGroupeFactory factory;
    private Groupe groupe;
    private JProgressBar bar;
    private JDialog dialog;


    /**
     * Un contrusteur pour le listener des boutons pour ajouter/retirer un groupe.
     * @param panel le panneau pour ajouter/retirer des étudiants
     * @param factory la factory qui gère les groupes 
     * @see fr.iutfbleau.projetIHM2022FI2.API.AbstractGroupeFactory Factory
     */
    public AddStudentListener(AddStudentPanel panel , AbstractGroupeFactory factory){
        this.listParent = panel.getListStudentRoot();
        this.list = panel.getListStudent();
        this.newStudents = panel.getNewStudents();
        this.removeStudents = panel.getRemoveStudents();
        this.panel = panel;
        this.factory = factory;
        this.dialog = new JDialog((JFrame)null," Modifications en cours" ,true);
        this.dialog.setLayout(new BorderLayout());
        this.bar = new JProgressBar(0,100);
        this.dialog.add(new JLabel("Chargement",JLabel.CENTER),BorderLayout.NORTH);
        this.dialog.add(bar,BorderLayout.CENTER);
        this.dialog.pack();
        this.dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }

    /**
     * Permet de set le groupe auquel ajouter ou retirer.
     * @param groupe le groupe choisi
     */
    public void setGroupe(Groupe groupe){
        this.groupe = groupe;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        DefaultListModel<Etudiant> model = (DefaultListModel<Etudiant>) list.getModel();
        DefaultListModel<Etudiant> modelParent = (DefaultListModel<Etudiant>) listParent.getModel();
        switch(command){
            case Constants.ADD : {
                List<Etudiant> selected = this.listParent.getSelectedValuesList(); 
                Integer i = null;
                if(e.getSource() instanceof MyTransferHandler ){
                    i = e.getID();
                    int i2 = e.getID();
                    for(Etudiant student : selected){
                        newStudents.add(student);
                        removeStudents.remove(student);
                        model.insertElementAt(student, i);
                        modelParent.removeElement(student);  
                        i += 1;                
                    }
                    list.setSelectedIndices(moveHandler(selected.size(), i2));
                }  
                else{
                    for(Etudiant student : selected){
                        newStudents.add(student);
                        removeStudents.remove(student);
                        model.addElement(student);
                        modelParent.removeElement(student); 
                    }                 
                    list.setSelectedIndices(moveButton(selected.size(), model.getSize()));
                }
                break;
            }

            case Constants.REMOVE : {
                List<Etudiant> selected = this.list.getSelectedValuesList(); 
                Integer i = null;
                if(e.getSource() instanceof MyTransferHandler){
                    i = e.getID();
                    int i2 = e.getID();
                    for(Etudiant student : selected ){
                        newStudents.remove(student);
                        removeStudents.add(student);
                        model.removeElement(student);
                        modelParent.insertElementAt(student, i);
                        i += 1;
                    }
                    listParent.setSelectedIndices(moveHandler(selected.size(), i2));
                }  
                else{
                    for(Etudiant student : selected ){
                        newStudents.remove(student);
                        removeStudents.add(student);
                        model.removeElement(student);
                        modelParent.addElement(student);
                        listParent.setSelectedValue(student, true);
                    }
                    listParent.setSelectedIndices(moveButton(selected.size(), model.getSize()));
                }

                break;
            }
            
            case Constants.CANCEL :
                if(newStudents.isEmpty() && removeStudents.isEmpty())
                    return;
                panel.cancel();
                break;
            
            case Constants.CONFIRMED :
                //Taille des listes
                int sizeNew = newStudents.size();
                int sizeRemove = removeStudents.size();
                bar.setMaximum(sizeNew + sizeRemove);
                if(sizeNew == 0 && sizeRemove == 0)
                    return;
                //Positionnement de la fenêtre modale
                this.dialog.setLocationRelativeTo(panel);
                // Création du SwingWorker
                SwingWorker<Void,Void> worker = new CustomWorker(this);
                worker.execute();
                //Affichage de la fenêtre modale
                this.dialog.setVisible(true);
        }
        this.panel.revalidate();
    }

    @Override
    public void whenBackground() {
        for(Etudiant student : newStudents){
            factory.addToGroupe(groupe, student);
            bar.setValue(bar.getValue() + 1);
        }
        for(Etudiant student : removeStudents){
            factory.dropFromGroupe(groupe, student);
            bar.setValue(bar.getValue() + 1);
        }
        removeStudents.clear();
        newStudents.clear();   
    }

    @Override
    public void whenDone() {
        bar.setValue(0);
        dialog.setVisible(false);
    }

    /**
     * Permet de sélectionner la liste d'étudiant au bon endroit si c'est un Drag and Drop.
     * @param selected le nombre d'étudiant ajouté 
     * @param index l'index où a été ajouté la liste
     * @return un tableau d'indice pour sélectionner les étudiants
     */
    private int[] moveHandler(int selected, int index){
        //Permet de selectionner les bons étudiant
        int[] tab = new int[selected];
        for(int i = index ; i < selected + index; i++) {
            tab[(i % selected)] = i;
        }
        return tab;
    }

    /**
     * Permet de sélectionner la liste d'étudiant au bon endroit si c'est appuie sur le bouton.
     * @param selected le nombre d'étudiant ajouté 
     * @param modelSize la taille de la liste entière
     * @return un tableau d'indice pour sélectionner les étudiants
     */
    private int[] moveButton(int selected, int modelSize){
        //Permet de selectionner les bons étudiant
        int[] tab = new int[selected];
        for(int i = modelSize - selected; i < modelSize; i++) {
            tab[i % selected] = i;
        }
        return tab;
    }
}
