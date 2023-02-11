package fr.iutfbleau.projetIHM2022FI2.controller.handlers;


import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import fr.iutfbleau.projetIHM2022FI2.Constants;
import fr.iutfbleau.projetIHM2022FI2.API.Etudiant;


/**
 * Classe représentant le Drag and Drop.
 */
public class MyTransferHandler extends TransferHandler {
    
  private boolean add;
  private ActionListener listener;
  
  /**
   * Un constructeur.
   * Initialise le transferHandler avec un boolean et un listner.
   * @param add un boolean pour savoir si l'on doit ajouter
   * @param listener un listener pour simuler l'appuie d'un bouton
   */
  public MyTransferHandler(boolean add, ActionListener listener){
    super();
    this.add = add;
    this.listener = listener;
  }


/**
  * Méthode permettant à l'objet de savoir si les données reçues
  * via un drop sont autorisées à être importées
  * @param info le Transfert support
  * @return boolean
  */
  public boolean canImport(TransferHandler.TransferSupport info) {
    //Nous contrôlons si les données reçues sont d'un type autorisé, ici un Étudiant    
    if (!info.isDrop()) {
      return false;
    }
    
    return info.isDataFlavorSupported(Constants.DATA_FLAVOR) ;
  }

  /**
  * C'est ici que l'insertion des données dans notre composant est réalisée
  * @param support le Transfert support
  * @return boolean
  * Ne fait que simuler l'appuie sur un bouton
  */
  public boolean importData(TransferHandler.TransferSupport support){
    if(!canImport(support))
      return false;

      JList.DropLocation dl = (JList.DropLocation)support.getDropLocation();
      int index = dl.getIndex();
    
    if(!add){
      ActionEvent e = new ActionEvent(this,index,Constants.ADD,0);
      listener.actionPerformed(e);
    }
    else{
      ActionEvent e = new ActionEvent(this,index,Constants.REMOVE,0);
      listener.actionPerformed(e);
    }
    return true;
  }


  protected void exportDone(JComponent c, Transferable t, int action){
    
  }

  /**
  * Dans cette méthode, nous allons créer l'objet utilisé par le système de drag'n drop
  * afin de faire circuler les données entre les composants
  * Vous pouvez voir qu'il s'agit d'un objet de type Transferable
  * @param c Le composant
  * @return un objet Transferable 
  */
  protected Transferable createTransferable(JComponent c) {
      Transferable t = null;
      if (c instanceof JList) {
          @SuppressWarnings("unchecked")
          JList<Etudiant> liste = (JList<Etudiant>) c;
          Object value = liste.getSelectedValue();
          if (value instanceof Etudiant) {
            Etudiant li = (Etudiant) value;
              t = new ListItemTransferable(li);
          }
      }
      return t;
  }

  /**
  * Cette méthode est utilisée afin de déterminer le comportement 
  * du composant vis-à-vis du drag'n drop : nous retrouverons
  * nos variables statiques COPY, MOVE, COPY_OR_MOVE, LINK ou NONE 
  * @param c le composant
  * @return int
  */
  public int getSourceActions(JComponent c) {

    return MOVE;
  }
  
}