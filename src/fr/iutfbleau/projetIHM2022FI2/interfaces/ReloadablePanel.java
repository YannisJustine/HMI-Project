package fr.iutfbleau.projetIHM2022FI2.interfaces;

/**
 * Interface d'un panneau dont les informations peuvent être mis à jour (il peut être "rafraichit")
 */
public interface ReloadablePanel {
    /**
     * Met à jour les informations
     */
    public void reload();
}
