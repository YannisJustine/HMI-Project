package fr.iutfbleau.projetIHM2022FI2.MP;
import fr.iutfbleau.projetIHM2022FI2.API.*;
import java.util.*;
/**
 * Un groupe
 */

public class GroupeP implements Groupe {

    // attributs naturels induits par getter de l'interface Groupe
    private int id;
    private String name;
    private int min,max;
    private TypeGroupe type;
    private Groupe pointPoint;    
    // On utilise une interface set pour les sous-groupes et pour les membres (ce sont bien des ensembles en pratique).
    private Set<Groupe> sousGroupes;
    private Set<Etudiant> membresDuGroupe;

   /**
    * La racine des groupes de type ROOT
    * @param id l'id de la racine
    * @param name le nom de la racine
    * @param min le minimum d'étudiant
    * @param max le maximum d'étudiant
    */
    public GroupeP(int id, String name, int min, int max){
        Objects.requireNonNull(name,"On ne peut pas créer un groupe dont le nom est null");
        this.id= id;
        this.name=name;
        this.min=min;
        this.max=max;
        this.type=TypeGroupe.ROOT;
        this.pointPoint=this;
        this.sousGroupes=new LinkedHashSet<Groupe>();
        this.membresDuGroupe=new LinkedHashSet<Etudiant>();
    }
    
    /**
     * Un groupe de type FREE ou PARTITION
     * @param id l'id de la groupe
     * @param name le nom du groupe
     * @param min le minimum d'étudiant
     * @param max le maximum d'étudiant
     * @param type le type de groupe
     * @param pere le groupe père
     */
    public GroupeP(int id, String name, int min, int max, TypeGroupe type, Groupe pere){
        Objects.requireNonNull(name,"On ne peut pas créer un groupe dont le nom est null");
        this.id=id;
        this.name=name;
        this.min=min;
        this.max=max;
        this.pointPoint = pere;
        this.type=type;
        this.sousGroupes=new LinkedHashSet<Groupe>();
        this.membresDuGroupe=new LinkedHashSet<Etudiant>();
    }
    
    /**
     * Ajoute un étudiant. Se comporte comme add de l'interface Set.
     * @param e l'étudiant
     * @return true iff e est ajouté
     */
    public boolean addEtudiant(Etudiant e){
        Objects.requireNonNull(e,"On ne peut pas ajouter un Étudiant qui est null");
        return this.membresDuGroupe.add(e);
    }

    /**
     * Enlève un étudiant. Se comporte comme remove de l'interface Set.
     * @param e l'étudiant
     * @return true iff e est enlevé
     */
    public boolean removeEtudiant(Etudiant e){
        Objects.requireNonNull(e,"On ne peut pas enlever un Étudiant qui est null");
        return this.membresDuGroupe.remove(e);
    }

    /**
     * Ajoute un sous-groupe. Se comporte comme add de l'interface Set.
     * vérifie que le groupe passé en argument a bien renseigné this comme son père.
     * @param g le groupe
     * @return true iff g est ajouté
     */
     public boolean addSousGroupe(Groupe g){
        Objects.requireNonNull(g,"On ne peut pas ajouter un sous-groupe qui est null");
        if (this.equals(g.getPointPoint()))
            return this.sousGroupes.add(g);
        else throw new IllegalArgumentException("on ne peut pas ajouter un sous-groupe ont le père n'est pas this");
    }

    /**
     * Enlève un groupe. Se comporte comme remove de l'interface Set.
     * @param g le groupe
     * @return true iff e est enlevé
     */
    public boolean removeSousGroupe(Groupe g){
        Objects.requireNonNull(g,"On ne peut pas enlever un Étudiant qui est null");
        return this.sousGroupes.remove(g);
    }

    
    /**
     * permet de récupérer l'identifiant d'un groupe (référence interne sans intérêt irl).
     * @return l'identifiant.
     */
    public int getId(){
        return this.id;
    }

    /**
     * permet de récupérer le nom d'un groupe (utile irl).
     * @return le nom.
     */
    public String getName(){
        return this.name;
    }

    /**
     * permet de récupérer le nombre minimum d'étudiants souhaités dans le groupe.
     * @return le minimum souhaité
     */
    public int getMin(){
        return this.min;
    }

    /**
     * permet de récupérer le nombre maximum d'étudiants souhaités dans un groupe.
     * @return le maximum souhaité
     */
    public int getMax(){
        return this.max;
    }

    /**
     * permet de récupérer le nombre d'étudiants dans ce groupe.
     * @return le nombre de places prises (pas forcément limité entre Min et Max, mais c'est le but)
     */
    public int getSize(){
        return this.membresDuGroupe.size();
    }
    
    /**
     * permet de récupérer la nature du groupe
     * @return le type du groupe
     */
    public TypeGroupe getType(){
        return type;
    }

    /**
     * permet de récupérer le groupe père
     * un groupe racine devrait retourner lui-même
     *
     * @return le père
     */
    public Groupe getPointPoint(){
        return this.pointPoint;
    }

    /**
     * Potentiellement "vide"
     * Attention nous renvoyons l'ensemble sans le copier
     *
     * @return l'ensemble des sous-groupes.
     */
    public Set<Groupe> getSousGroupes(){
        return this.sousGroupes;
    }

    public void setName(String name){
        this.name = name;
    }
    
    /**
     * Potentiellement "vide"
     * Attention nous renvoyons l'ensemble sans le copier
     *
     * @return l'ensemble des étudiants.
     */
    public Set<Etudiant> getEtudiants(){
        return this.membresDuGroupe;
    }

}
