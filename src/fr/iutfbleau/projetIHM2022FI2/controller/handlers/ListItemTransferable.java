package fr.iutfbleau.projetIHM2022FI2.controller.handlers;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import fr.iutfbleau.projetIHM2022FI2.API.Etudiant;

/**
 * Classe représentant un objet transferable (étudiant)
 */
public class ListItemTransferable implements Transferable {

    public static final DataFlavor LIST_ITEM_DATA_FLAVOR = new DataFlavor(Etudiant.class, "java/ListItem");
    private Etudiant listItem;

    /**
     * Le constructeur.
     * @param listItem l'étudiant
     */
    public ListItemTransferable(Etudiant listItem) {
        this.listItem = listItem;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{LIST_ITEM_DATA_FLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(LIST_ITEM_DATA_FLAVOR);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return listItem;
    }
}