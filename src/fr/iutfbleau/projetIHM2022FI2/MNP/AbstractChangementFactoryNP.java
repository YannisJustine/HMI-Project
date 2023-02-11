package fr.iutfbleau.projetIHM2022FI2.MNP;
import fr.iutfbleau.projetIHM2022FI2.API.*;
import java.util.*;
/**
 * Usine abstraite gérant l'ensemble des changements.
 * 
 */

public class AbstractChangementFactoryNP implements AbstractChangementFactory {

    // l'usine à groupe travaillant en tandem avec cette usine.
    private AbstractGroupeFactory agf;

    // On utilise une table de hachage pour retrouver facilement un changement (à partir de son id).
    // Si il y a beaucoup de changements c'est plus rapide que de parcourir toute une liste.
    private HashMap<Integer,Changement> brain;

    public AbstractChangementFactoryNP(AbstractGroupeFactory agf){
        Objects.requireNonNull(agf,"On ne peut pas créer une usine à changement dont l'usine à groupe parternaire est null");
        this.agf=agf;
        this.brain=new HashMap<Integer,Changement>();        
    }
                                        
    
    /**
     * permet de récupérer l'usine abstraite pour les groupes qui fonctionne en tandem avec cette usine abstraite
     * @return cette usine abstraite pour les groupes
     */
    public AbstractGroupeFactory getGroupeFactory(){
        return this.agf;
    }
    
    /**
     * permet de récupérer les changements 
     * @return l'ensemble de tous les changements en attente
     */
    public Set<Changement> getAllChangements(){
        // la méthode value() d'un hashmap retourne la collection des valeurs.
        // Il faut transformer la collection en Set.
        // Un constructeur de HashSet permet de faire cette opération.
        Set<Changement> out = new HashSet<Changement>(this.brain.values());
        return out;
    }

    /**
     * permet de mettre en oeuvre un changement connu de l'usine abstraite.
     *
     * @throws java.lang.NullPointerException si un argument est null
     * @throws java.lang.IllegalStateException si le changement n'a pas de sens en l'état actuel (e.g. étudiant pas dans le groupe de départ a, groupe b inconnu, groupe a inconnu, etc).
     * @throws java.lang.IllegalArgumentException si inconnu de l'usine abstraite
     */
    public void applyChangement(Changement c){
        Objects.requireNonNull(c,"On ne peut pas appliquer un changement qui est null");
        Etudiant e = c.getEtu();
        Groupe a = c.getA();
        Groupe b = c.getB();

        if (!agf.knows(a)) throw new IllegalStateException("Le groupe de départ du changement est inconnu. Impossible à mettre en oeuvre.");
        
        if (!agf.knows(b)) throw new IllegalStateException("Le groupe d'arrivée du changement est inconnu. Impossible à mettre en oeuvre.");
        // pas encore implanté.
        if(!agf.getGroupesOfEtudiant(e).contains(a)) throw new IllegalStateException("Le groupe de départ ne contient pas l'étudiant. Impossible à mettre en oeuvre.");
            
        agf.dropFromGroupe(a,e);
        agf.addToGroupe(b,e);
        // En cas de succès, on enlève le changement du cerveau
        this.brain.remove(Integer.valueOf(c.getId()));
    }

    
    /**
     * permet de supprimer un changement connu de l'usine abstraite.
     *
     * @throws java.lang.NullPointerException si un argument est null
     * @throws java.lang.IllegalArgumentException si inconnu de l'usine abstraite
     */
    public void deleteChangement(Changement c){
        Objects.requireNonNull(c,"On ne peut pas demander la suppression d'un changement qui est null");

        this.brain.remove(Integer.valueOf(c.getId()));

    }

    /**
     * permet d'ajouter un nouveau changement.
     *
     * @param A groupe actuel
     * @param B groupe demandé
     * @param e étudiant concerné par le changement
     * @param explication explication pour l'administrateur 
     * @throws java.lang.NullPointerException si un argument est null
     * @throws java.lang.IllegalArgumentException si les groupes ou l'étudiant ne sont pas connus de la factory partenaire, ou e n'appartient pas à A ou A et B ne sont pas frères dans l'arbre des groupes.
     *        
     */
    public void createChangement(Groupe A, Etudiant e, Groupe B, String explication){
        Objects.requireNonNull(A,"Le groupe d'origine ne peut pas être null");
        Objects.requireNonNull(B,"Le groupe d'arrivée ne peut pas être null");
        Objects.requireNonNull(e,"L'étudiant ne peut pas être null");
        if(!A.getPointPoint().equals(B.getPointPoint()))
            throw new IllegalArgumentException("Les groupes ne sont pas frères");
        Changement c = new ChangementNP(A,e,B,explication);
        this.brain.put(Integer.valueOf(c.getId()),c);   
    }
}
