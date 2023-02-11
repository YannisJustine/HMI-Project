package fr.iutfbleau.projetIHM2022FI2.MNP;
import fr.iutfbleau.projetIHM2022FI2.API.*;
import java.util.*;
/**
 * Un groupe
 */

public class GroupeNP implements Groupe {

    //auto-incrément des groupes. (NB. inutile, mais ça fair un exemple d'attribut statique).
    private static int nextId=0;
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
     * Nouveau groupe vide de type ROOT sans étudiants, sans sous-Groupe
     * @param name le nom du groupe
     * @param min le minimum d'étudiant
     * @param max le maximum d'étudiant
     */
    public GroupeNP(String name, int min, int max){
        Objects.requireNonNull(name,"On ne peut pas créer un groupe dont le nom est null");
        this.id=++this.nextId;
        this.name=name;
        this.min=min;
        this.max=max;
        this.type=TypeGroupe.ROOT;
        this.pointPoint=this;
        this.sousGroupes=new LinkedHashSet<Groupe>();
        this.membresDuGroupe=new LinkedHashSet<Etudiant>();
    }
    
    /**
     * Nouveau groupe vide de type FREE sans étudiants, sans sous-Groupe
     * @param pere le groupe pere
     * @param name le nom du groupe
     * @param min le minimum d'étudiant
     * @param max le maximum d'étudiant
     */
    public GroupeNP(Groupe pere, String name, int min, int max){
        Objects.requireNonNull(pere,"On ne peut pas créer un groupe dont le père est null");
        Objects.requireNonNull(name,"On ne peut pas créer un groupe dont le nom est null");
        this.id=++this.nextId;
        this.name=name;
        this.min=min;
        this.max=max;
        this.type=TypeGroupe.FREE;
        this.pointPoint=pere;
        this.sousGroupes=new LinkedHashSet<Groupe>();
        this.membresDuGroupe=new LinkedHashSet<Etudiant>();
    }

    /**
     * Nouveau groupe de type PARTITION dupliquant le groupe passé en paramètre (pour servir de racine à une partition de ce groupe de type FREE passé en paramètre).
     * @param pere le groupe pere
     */
    public GroupeNP(Groupe pere){
        Objects.requireNonNull(pere,"On ne peut pas créer un groupe dont le père est null");
        this.id=++this.nextId;
        this.name=pere.getName()+"_PARTITION_"+ this.id;
        this.min=pere.getMin();
        this.max=pere.getMax();
        this.type=TypeGroupe.PARTITION;
        this.pointPoint=pere;
        this.sousGroupes= new LinkedHashSet<Groupe>();
        this.membresDuGroupe = new LinkedHashSet<Etudiant>(pere.getEtudiants()); //Ne duplique pas
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
