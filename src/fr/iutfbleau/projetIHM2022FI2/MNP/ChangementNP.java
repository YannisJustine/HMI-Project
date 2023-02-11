package fr.iutfbleau.projetIHM2022FI2.MNP;
import fr.iutfbleau.projetIHM2022FI2.API.*;
import java.util.*;
/**
 * Une demande de changement de groupe
 * concerne un étudiant, qui est dans un groupe A et veut aller dans un groupe B.
 * 
 * Implémentation non persistante fournie avec l'API.
 */

public class ChangementNP implements Changement {

    //auto-incrément des changements
    private static int nextId=0;

    private int id;
    private Groupe a,b;
    private Etudiant e;
    private String explication;

    public ChangementNP(Groupe a, Etudiant e, Groupe b, String explication){
        Objects.requireNonNull(a,"On ne peut pas créer un changement avec un groupe à quitter null");
        Objects.requireNonNull(b,"On ne peut pas créer un changement avec un groupe à rejoindre null");
        Objects.requireNonNull(e,"On ne peut pas créer un changement concernant un étudiant null");

        this.id=++this.nextId;
        this.a=a;
        this.b=b;
        this.e=e;
        this.explication = explication;
    }
    
    /**
     * permet de récupérer l'identifiant du changement (référence interne sans intérêt irl).
     * @return l'identifiant.
     */
    public int getId(){
        return this.id;
    }

    /**
     * permet de récupérer le groupe de depart
     * @return ce groupe.
     */
    public Groupe getA(){
        return this.a;
    }

    /**
     * permet de récupérer le groupe d'arrivée
     * @return ce groupe.
     */
    public Groupe getB(){
        return this.b;
    }

    /**
     * permet de récupérer l'étudiant demandant le changement
     * @return cet étudiant
     */
    public Etudiant getEtu(){
        return this.e;
    }

    public String getExplication() {
        return this.explication;
    }

}
