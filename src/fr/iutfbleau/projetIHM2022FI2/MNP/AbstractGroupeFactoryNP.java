package fr.iutfbleau.projetIHM2022FI2.MNP;
import fr.iutfbleau.projetIHM2022FI2.API.*;

import java.util.*;
/**
 * Usine abstraite gérant l'ensemble des groupes.
 * 
 */

public class AbstractGroupeFactoryNP implements AbstractGroupeFactory {

    // la racine (promotion)
    private Groupe promo;

    // On utilise une table de hachage pour retrouver facilement un groupe (à partir de son id).
    // Si il y a beaucoup de groupes c'est plus rapide que de parcourir toute une liste.
    private HashMap<Integer,Groupe> brain;

    /**
     * Le constructeur fabrique le groupe promotion vide.
     * Il faut ensuite y ajouter les étudiants.
     * @param name le nom du groupe à créer
     * @param min bornes indicatives sur la taille du groupe à créer
     * @param max bornes indicatives sur la taille du groupe à créer
     */
    public AbstractGroupeFactoryNP(String name, int min, int max){
        Objects.requireNonNull(name,"On ne peut pas créer une promotion  dont le nom est null");
        this.promo=new GroupeNP(name,min,max);
        this.brain=new HashMap<Integer,Groupe>();
        this.brain.put(Integer.valueOf(this.promo.getId()),this.promo);
    }
    
    /**
     * Test plutôt optimiste. Si la clé est identique alors on fait comme si c'était le bon groupe.
     * @param g le groupe
     * @return true iff g est connu de la factory
     */
    public Boolean knows(Groupe g){
        return this.brain.containsKey(Integer.valueOf(g.getId()));
    }

    
    
    /**
     * permet de récupérer le Groupe qui contient les étudiants de toute la promotion
     * @return la promo.
     */
    public Groupe getPromotion(){
        return this.promo;
    }

    /**
     * permet de supprimer un groupe connu de l'usine abstraite qui ne contient pas de groupes.
     * Pour détruire un groupe connu qui en contient d'autres il faut le faire récursivement.
     * @param g le groupe
     * @throws java.lang.NullPointerException si un argument est null
     * @throws java.lang.IllegalStateException si le groupe contient des groupes
     * @throws java.lang.IllegalArgumentException si le groupe n'est pas connu de l'usine abstraite ou bien si le groupe est celui de toute la promotion (renvoyé par getPromotion)
     */
    public void deleteGroupe(Groupe g){
        Objects.requireNonNull(g,"On ne peut pas enlever un groupe null car null n'est pas un groupe autorisé");
        if (!this.knows(g)){
            throw new IllegalArgumentException("Impossible d'enlever un groupe inconnu");
        }
        if (this.getPromotion().equals(g)){
            throw new IllegalArgumentException("Impossible de détruire le groupe de toute la promotion");
        }
        if (!g.getSousGroupes().isEmpty()){
            throw new IllegalStateException("Impossible de détruire un groupe contenant un groupe");
        }
        g.getPointPoint().removeSousGroupe(g);
        this.brain.remove(Integer.valueOf(g.getId()));
    }

    /**
     * permet d'ajouter un groupe vide de type FREE comme sous-groupe d'un groupe donné.
     * @param pere le groupe père du groupe à créer 
     * @param name le nom du groupe à créer
     * @param min bornes indicatives sur la taille du groupe à créer
     * @param max bornes indicatives sur la taille du groupe à créer
     *
     * @throws java.lang.NullPointerException si un argument est null
     * @throws java.lang.IllegalArgumentException si le groupe pere est de type PARTITION
     *                                            ou si il n'y a pas 0 &#60; min &#60;= max 
     */
    public void createGroupe(Groupe pere, String name, int min, int max){
        Objects.requireNonNull(pere,"Le groupe pere ne peut pas être null");
        Objects.requireNonNull(name,"Le nouveau groupe ne peut pas avoir null comme nom");
        if (!this.knows(pere)){
            throw new IllegalArgumentException("Interdit d'ajouter un fils à un groupe inconnu");
        }
        if (pere.getType().equals(TypeGroupe.PARTITION)){
            throw new IllegalArgumentException("Impossible d'ajouter un groupe à une partition.");
        }
        if ( min <= 0 || max < min){
            throw new IllegalArgumentException("Il faut que 0 < min <= max");
        }
        Groupe g = new GroupeNP(pere,name,min,max);
        pere.addSousGroupe(g);
        this.brain.put(Integer.valueOf(g.getId()),g);
    }

    /**
     * permet de créer une partition automatiquement sous un groupe donné.
     *
     * @param pere le groupe père du groupe à partitionner 
     * @param name le nom des groupe à créer (on ajoute à la suite un numéro de 1 à n  pour distinguer chaque groupe formant la partition)
     * @param n le nombre de partitions
     * @throws java.lang.NullPointerException si un argument est null
     * @throws java.lang.IllegalArgumentException si le groupe pere est de type PARTITION 
     *                                            ou n négatif ou nul
     *
     * NB. doit créer une "copie" de pere 
     *     sous pere de type Partition et ajouter sous ce groupe, n groupes de type "FREE". 
     *     les valeurs min et max de ces n groupes sont 
     *       min = 0 et 
     *       max = partie entière de N/n plus 1, où N est le nombre max du groupe pere.   
     */
    public void createPartition(Groupe pere, String name, int n){
        Objects.requireNonNull(pere,"Le groupe pere ne peut pas être null");
        Objects.requireNonNull(name,"Le nouveau groupe ne peut pas avoir null comme nom");
        if (!this.knows(pere)){
            throw new IllegalArgumentException("Impossible de partitionner ce groupe inconnu");
        }
        if (pere.getType().equals(TypeGroupe.PARTITION)){
            throw new IllegalArgumentException("Impossible de créer une partition à ce niveau. Il faut soit repartitionner le groupe au dessus, soit partitionner une partition en dessous.");
        }
        if ( n <= 0){
            throw new IllegalArgumentException("Le nombre de partitions doit être strictement positif");
        }
        //Création de la racine de la partition.
        Groupe copiePereRacinePartition = new GroupeNP(pere);
        pere.addSousGroupe(copiePereRacinePartition);
        this.brain.put(Integer.valueOf(copiePereRacinePartition.getId()),copiePereRacinePartition);
        // création des sous-groupes
        int min = 0;
        int max = ((int) Math.floor(pere.getMax()/n))+1;
        List<Groupe> groupes = new ArrayList<Groupe>(n);
        for(int i = 0; i<n; i++){
            Groupe g = new GroupeNP(copiePereRacinePartition,name+"_"+i,min,max);
            groupes.add(i,g);// ajout dans le tableau des groupes
            copiePereRacinePartition.addSousGroupe(g);
            this.brain.put(Integer.valueOf(g.getId()),g);
        }
        // Partage des étudiants (on ne prête pas attention aux min et max)
        int i=0;
        for (Etudiant s: pere.getEtudiants()){
            copiePereRacinePartition.addEtudiant(s);
            groupes.get(i).addEtudiant(s);
            i = (i+1) %n;
        }
    }

    /**
     * permet d'ajouter un étudiant à un groupe.
     * 
     * @param g le groupe dans lequel il faut ajouter l'étudiant
     * @param e l'étudiant à ajouter
     *
     * @throws java.lang.NullPointerException si un argument est null
     * @throws java.lang.IllegalArgumentException la factory ne connaît pas g
     * @throws java.lang.IllegalStateException le père de g ne contient pas e
     */
    public void addToGroupe(Groupe g, Etudiant e){
        Objects.requireNonNull(g,"Le groupe ne peut pas être null");
        Objects.requireNonNull(e,"L'étudiant ne peut pas être null");
        if (!this.knows(g)){
            throw new IllegalArgumentException("Impossible d'ajouter l'étudiant car le est groupe inconnu");
        }
        g.addEtudiant(e);
        for(Groupe sousGroupe :g.getSousGroupes()) {
            if(sousGroupe.getType() == TypeGroupe.PARTITION) {
                addSousGroupePartition(sousGroupe, e);
            }
        }
    }

    private void addSousGroupePartition(Groupe g, Etudiant student) {
        LinkedList<Groupe> groupes = new LinkedList<Groupe>(g.getSousGroupes());
        int min = groupes.get(0).getSize();
        boolean found = false;
        //Ajoute tous les élèves au groupe PARTITION
        this.addToGroupe(g, student);
        //Egalise le nombre d'élèves dans les enfants de la partition
        
        //Ajoute les élèves dans les sous-groupes si les enfants du groupe g ont des partitions
        for(Groupe sousGroupe : groupes) {
            if(sousGroupe.getSize() < min) {
                this.addToGroupe(sousGroupe,student);
                found = true;
                break;
            }
        }
        if(!found)
            this.addToGroupe(groupes.get(0),student);
    }

    /**
     * permet d'enlever un étudiant d'un groupe.
     *
     * @param g le groupe dans lequel il faut enlever l'étudiant
     * @param e l'étudiant à enlever
     *
     * @throws java.lang.NullPointerException si un argument est null
     * @throws java.lang.IllegalStateException g ne contient pas e
     * @throws java.lang.IllegalArgumentException la factory ne connaît pas g
     */
    public void dropFromGroupe(Groupe g, Etudiant e){
        Objects.requireNonNull(g,"Le groupe ne peut pas être null");
        Objects.requireNonNull(e,"L'étudiant ne peut pas être null");
        if (!this.knows(g)){
            throw new IllegalArgumentException("Impossible de supprimer l'étudiant car le est groupe inconnu");
        }
        // ------- Modifié --------
        // Il n'est pas possible de supprimer un élève d'une partition de la promotion.
        if(g.getPointPoint().equals(this.promo) && g.getType() == TypeGroupe.PARTITION) {
            throw new IllegalArgumentException("Impossible de supprimer l'étudiant car le groupe est une partition de la promotion");
        }
        for(Groupe sousgroupe : g.getSousGroupes())
            this.dropFromGroupe(sousgroupe, e);
        g.removeEtudiant(e);
    }

     /**
     * permet de retrouver un étudiant à partir d'un String.
     *
     * NB. dans une version simple il doit s'agir du nom exact.
     * dans une version un peu plus complexe, il s'agit des premières lettres du nom
     * dans une version avancée, on peut autoriser une expression régulière plus ou moins complexe qui est générée si la première recherche n'a pas renvoyé de candidat.
     *
     * @param nomEtu nomEtu le nom approximmatif de l'étudiant
     * @return Set&#60;Etudiant&#62; L'ensemble des étudiants connus de la factory ayant un nom "proche" de ce string au sens de la remarque ci-dessus.
     *
     * @throws java.lang.NullPointerException si le String est null.
     */
    public Set<Etudiant> getEtudiants(String nomEtu){
        // on cherche bêtement dans la promo.
        Set<Etudiant> out = new LinkedHashSet<Etudiant>();
        for (Etudiant e : getPromotion().getEtudiants()){
            // ------- Modifié --------
            // Version un peu plus complexe, il s'agit d'une sous-chaine
            if (e.getNom().toLowerCase().contains(nomEtu.toLowerCase())){
                    out.add(e);
            }
        }
        return out;
    }

    /**
     * permet de retrouver les groupes d'un étudiant.
     *
     * @param etu un étudiant
     * @return un Set de groupes l'étudiant connu de la factory ayant cet identifiant
     *
     * @throws java.lang.NullPointerException si le String est null.
     */
    public Set<Groupe> getGroupesOfEtudiant(Etudiant etu){
        // ------- Modifié --------
        Objects.requireNonNull(etu);
        // on cherche bêtement dans la promo.
        Set<Groupe> out = new LinkedHashSet<Groupe>();
        for (Groupe g : this.brain.values()){
            if (g.getEtudiants().contains(etu))
                out.add(g);
        }
        return out;
    }
    
    /**
     * permet de renommer un grouê
     *
     * @param groupe le groupe a renommé
     * @param nom le nouveau nom
     *
     * @throws java.lang.IllegalArgumentException si le groupe est inconnu
     * @throws java.lang.NullPointerException si le String ou le groupe est null.
     */
    public void rename(Groupe groupe, String nom) {
        Objects.requireNonNull(groupe);
        Objects.requireNonNull(nom);
        if(!this.knows(groupe)) 
            throw new IllegalArgumentException("Groupe Inconnu");
        
        groupe.setName(nom);
    }
}
