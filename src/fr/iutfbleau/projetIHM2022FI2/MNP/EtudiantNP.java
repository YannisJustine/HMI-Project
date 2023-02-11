package fr.iutfbleau.projetIHM2022FI2.MNP;
import fr.iutfbleau.projetIHM2022FI2.API.*;
import java.util.*;
/**
 * Un étudiant
 */

public class EtudiantNP implements Etudiant{

    private static int nextId=0;
    private int id;
    private String nom, prenom;

    /**
     * Un étudiant
     * @param nom le nom de l'étudiant
     * @param prenom le prénom de l'étudiant
     */
    public EtudiantNP(String nom, String prenom){
        Objects.requireNonNull(nom,"On ne peut pas créer un étudiant avec un nom null");
        Objects.requireNonNull(prenom,"On ne peut pas créer un étudiant avec un nom null");
        // auto incrément de l'id
        this.id=++this.nextId;
        this.nom=nom;
        this.prenom=prenom;
    }
    
    /**
     * permet de récupérer l'identifiant de l'étudiant.
     * @return l'identifiant.
     */
    public int getId(){
        return this.id;
    }

    /**
     * permet de récupérer 
     * @return le nom de l'étudiant.
     */
    public String getNom(){
        return this.nom;
    }

    /**
     * permet de récupérer
     * @return le prénom de l'étudiant
     */
    public String getPrenom(){
        return this.prenom;
    }


}
