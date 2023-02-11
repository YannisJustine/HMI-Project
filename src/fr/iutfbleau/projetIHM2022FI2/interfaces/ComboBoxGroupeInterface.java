package fr.iutfbleau.projetIHM2022FI2.interfaces;

import java.util.Set;
import fr.iutfbleau.projetIHM2022FI2.API.Groupe;

/**
 * Interface permettant de définir les groupes de départ et d'arrivée
 */
public interface ComboBoxGroupeInterface {
    /**
     * Permet de définir l'ensemble des groupes de départ
     * @param groupes l'ensemble des groupes
     */
    public void setGroupeDepart(Set<Groupe> groupes);
    /**
     * Permet de définir l'ensemble des groupes d'arrivée
     * @param groupes l'ensemble des groupes
     */
    public void setGroupeArrivee(Set<Groupe> groupes);

}
