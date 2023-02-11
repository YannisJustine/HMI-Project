package fr.iutfbleau.projetIHM2022FI2.MP;
import fr.iutfbleau.projetIHM2022FI2.API.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import org.mariadb.jdbc.MariaDbPoolDataSource;
/**
 * Usine abstraite gérant l'ensemble des groupes.
 * 
 */


public class AbstractGroupeFactoryP implements AbstractGroupeFactory {

    // la racine (promotion)
    private Groupe promo;

    // On utilise une table de hachage pour retrouver facilement un groupe (à partir de son id).
    // Si il y a beaucoup de groupes c'est plus rapide que de parcourir toute une liste.
    private HashMap<Integer,Groupe> brain;
    private HashMap<Integer,Etudiant> studentBrain;
    private MariaDbPoolDataSource pool;

    public static String URL = "jdbc:mariadb://localhost";
    public static String USER = "root";
    public static String MDP = "admin";

    /**
     * Le constructeur charge les données depuis la base de données
     */
    public AbstractGroupeFactoryP(){
        this.pool = new MariaDbPoolDataSource();
        try {
            pool.setUser(USER); //Utilisateur
            pool.setPassword(MDP); //Mot de passe
            pool.setUrl(URL); //Url de la base
            pool.initialize(); //Initialisation
            this.loadFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private void loadFactory() {
        try {
            Connection conn = this.pool.getConnection();
            try {
                PreparedStatement req = conn.prepareStatement("SELECT * FROM Groupe WHERE TYPE = 'ROOT' LIMIT 1");
                //------ Requete preparee -------- 
                ResultSet res = req.executeQuery();
                if(res.next()) {
                    this.promo = new GroupeP(res.getInt(1), res.getString(2), res.getInt(3), res.getInt(4));
                    this.brain = new HashMap<Integer,Groupe>();
                    this.studentBrain = new HashMap<Integer,Etudiant>();
                    this.brain.put(Integer.valueOf(this.promo.getId()),this.promo);
                    try {
                        conn.close();
                    } catch (Exception e) {}
                    this.loadEtudiants();
                    this.loadGroupes();
                }
                else {
                    System.err.println("Il n'y a pas de ROOT"); //Ne devrait pas arriver
                }
            } catch (Exception e) {
              System.err.println("Probleme requete");
              e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

    private void loadGroupes() {
        try {
           
            Connection conn = this.pool.getConnection();
      
            try {
                PreparedStatement req = conn.prepareStatement("SELECT * FROM Groupe WHERE TYPE <> 'ROOT' ORDER BY Pere ASC");
                //------ Requete preparee -------- 
                ResultSet res = req.executeQuery();
                while(res.next()) {
                    TypeGroupe type;
                    switch(res.getString(5)) {
                        case "PARTITION" :
                            type = TypeGroupe.PARTITION;
                            break;
                        default :
                            type = TypeGroupe.FREE;
                    }
                    Groupe g = new GroupeP(res.getInt(1), res.getString(2), res.getInt(3), res.getInt(4), type, this.brain.get(res.getInt(6)));
                    this.brain.put(Integer.valueOf(g.getId()),g);
                }
                try {
                    conn.close();
                } catch (Exception e) {}
                this.addSousGroupes();
                this.addEtudiants();
            } catch (Exception e) {
              System.err.println("Probleme requete");
              e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

    private void addSousGroupes() {
        try {
           
            Connection conn = this.pool.getConnection();
            try {
                PreparedStatement req = conn.prepareStatement("SELECT id FROM Groupe WHERE TYPE <> 'ROOT' AND Pere = ?");
                //------ Requete preparee -------- 
                for(Groupe groupe : this.brain.values()) {
                    req.setInt(1, groupe.getId());
                    ResultSet res = req.executeQuery();
                    while(res.next()) {
                        groupe.addSousGroupe(this.brain.get(res.getInt(1)));
                    }
                }
            } catch (Exception e) {
              System.err.println("Probleme requete");
              e.printStackTrace();
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

    private void loadEtudiants() {
        try {
           
            Connection conn = this.pool.getConnection();
            try {
                PreparedStatement req = conn.prepareStatement("SELECT * FROM Etudiant");
                //------ Requete preparee -------- 
                ResultSet res = req.executeQuery();
                while(res.next()) {
                    this.studentBrain.put(res.getInt(1), new EtudiantP(res.getInt(1), res.getString(2), res.getString(3)));
                }
            } catch (Exception e) {
              System.err.println("Probleme requete");
              e.printStackTrace();
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

    private void addEtudiants() {
        try {
            Connection conn = this.pool.getConnection();
            try {
                PreparedStatement req = conn.prepareStatement("SELECT idEtudiant FROM Groupe_Etudiants WHERE idGroupe = ?");
                //------ Requete preparee -------- 
                for(Groupe groupe : this.brain.values()) {
                    req.setInt(1, groupe.getId());
                    ResultSet res = req.executeQuery();
                    while(res.next()) {
                        groupe.addEtudiant(this.studentBrain.get(res.getInt(1)));
                    }
                }
            } catch (Exception e) {
              System.err.println("Probleme requete");
              e.printStackTrace();
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

    private void addToDB(Groupe pere, String name, int min, int max){
        try {
           
            Connection conn = this.pool.getConnection();
            try {
              
                PreparedStatement req = conn.prepareStatement("INSERT INTO Groupe(Nom,Min,Max,Pere,Type) VALUES (?,?,?,?,'FREE') ",Statement.RETURN_GENERATED_KEYS);
                //------ Requete preparee -------- 
                req.setString(1,name);
                req.setInt(2, min);
                req.setInt(3, max);
                req.setInt(4, pere.getId());
                req.executeUpdate();
                ResultSet generatedKeys = req.getGeneratedKeys();
                if (generatedKeys.next()) {
                    Groupe g = new GroupeP(generatedKeys.getInt(1),name,min,max,TypeGroupe.FREE,pere);
                    pere.addSousGroupe(g);
                    this.brain.put(Integer.valueOf(g.getId()),g);   
                }
                req.close();
            } catch (Exception e) {
              System.err.println("Probleme requete");
              e.printStackTrace();
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }   
    }

    /**
     * Test plutôt optimiste. Si la clé est identique alors on fait comme si c'était le bon groupe.
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
     *
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
        try {
           
            Connection conn = this.pool.getConnection();
            try {
                PreparedStatement req = conn.prepareStatement("DELETE FROM Groupe WHERE id = ?");
                //------ Requete preparee -------- 
                req.setInt(1, g.getId());
                req.executeQuery();
                g.getPointPoint().removeSousGroupe(g);
                this.brain.remove(Integer.valueOf(g.getId()));
            } catch (Exception e) {
              System.err.println("Probleme requete");
              e.printStackTrace();
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }   
        
    }

    /**
     * permet d'ajouter un groupe vide de type FREE comme sous-groupe d'un groupe donné.
     * @param pere le groupe père du groupe à créer 
     * @param name le nom du groupe à créer
     * @param min,max bornes indicatives sur la taille du groupe à créer
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
            throw new IllegalArgumentException("Impossible d'ajouter un groupe à une parition. Il faut utiliser createPartition pour créer une partition");
        }
        if ( min <= 0 || max < min){
            throw new IllegalArgumentException("Il faut que 0 < min <= max");
        }
        this.addToDB(pere, name, min, max);
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
        int min = pere.getMin();
        int max = pere.getMax();
        String nameG = pere.getName() + "_PARTITION";
        try {
           
            Connection conn = this.pool.getConnection();
            try {
                PreparedStatement req1 = conn.prepareStatement("SELECT auto_increment FROM INFORMATION_SCHEMA.TABLES WHERE table_name = 'Groupe'");
                ResultSet res = req1.executeQuery();
                int numero = 0;
                if (res.next()){
                    numero = res.getInt(1);
                } 
                req1.close();
                PreparedStatement req = conn.prepareStatement("INSERT INTO Groupe(Nom,Min,Max,Pere,Type) VALUES (?,?,?,?,'PARTITION') ",Statement.RETURN_GENERATED_KEYS);
                //------ Requete preparee -------- 
                req.setString(1,nameG + "_" + numero);
                req.setInt(2,min);
                req.setInt(3,max);
                req.setInt(4,pere.getId());
                req.executeUpdate();
                ResultSet generatedKeys = req.getGeneratedKeys();
                if (generatedKeys.next()) {
                    req.close();
                    Groupe copiePereRacinePartition = new GroupeP(generatedKeys.getInt(1),nameG + "_" + numero,min,max,TypeGroupe.PARTITION,pere);
                    pere.addSousGroupe(copiePereRacinePartition);
                    this.brain.put(Integer.valueOf(copiePereRacinePartition.getId()),copiePereRacinePartition); 

                    min = 0;
                    max = ((int) Math.floor(pere.getSize()/n))+1;

                    for(int i = 0; i<n; i++){
                        this.addToDB(copiePereRacinePartition, name + "_" + i, min, max);
                    }

                    List<Groupe> groupes = new ArrayList<Groupe>(copiePereRacinePartition.getSousGroupes());

                    // Partage des étudiants (on ne prête pas attention aux min et max)
                    int i=0;
                    for (Etudiant s: pere.getEtudiants()){
                        this.addToGroupe(copiePereRacinePartition, s); 
                        this.addToGroupe( groupes.get(i), s);
                        i = (i+1) %n;
                    }
                }
                req.close();
            } catch (Exception e) {
              System.err.println("Probleme requete");
              e.printStackTrace();
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
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
        try {
           
            Connection conn = this.pool.getConnection();

            try {
                PreparedStatement req = conn.prepareStatement("INSERT INTO Groupe_Etudiants VALUES (?,?)");
                //------ Requete preparee -------- 
                req.setInt(1, g.getId());
                req.setInt(2, e.getId());
                req.executeQuery();
                g.addEtudiant(e);
                req.close();
                conn.close();
                for(Groupe sousGroupe :g.getSousGroupes()) {
                    if(sousGroupe.getType() == TypeGroupe.PARTITION) {
                        addSousGroupePartition(sousGroupe, e);
                    }
                }
            } catch (java.sql.SQLIntegrityConstraintViolationException cve) {
                throw new IllegalArgumentException("Cet élève est déjà présent dans ce groupe");
            }  catch (Exception e1) {
              System.err.println("Probleme requete");
              e1.printStackTrace();
            }
        } catch (Exception e2) {
            System.err.println("Probleme connexion");
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
        try {
           
            Connection conn = this.pool.getConnection();
            try {
                PreparedStatement req = conn.prepareStatement("DELETE FROM Groupe_Etudiants WHERE idGroupe = ? AND idEtudiant = ?");
                //------ Requete preparee -------- 
                req.setInt(1, g.getId());
                req.setInt(2, e.getId());
                req.executeQuery();
                g.removeEtudiant(e);
                req.close();
            }  catch (Exception e1) {
                System.err.println("Probleme requete");
                e1.printStackTrace();
            }
            conn.close();
        } catch (Exception e2) {
            e2.printStackTrace();
        }  
    }

     /**
     * permet de retrouver un étudiant à partir d'un String.
     *
     * NB. dans une version simple il doit s'agir du nom exact.
     * dans une version un peu plus complexe, il s'agit des premières lettres du nom
     * dans une version avancée, on peut autoriser une expression régulière plus ou moins complexe qui est générée si la première recherche n'a pas renvoyé de candidat.
     *
     * @param nomEtu nomEtu le nom approximmatif de l'étudiant
     * @return Set&#60;Etudiant&#62; l'ensemble des étudiants connus de la factory ayant un nom "proche" de ce string au sens de la remarque ci-dessus.
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
     * @return Etudiant l'étudiant connu de la factory ayant cet identifiant
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
        if(!this.knows(groupe)) throw new IllegalArgumentException("Groupe Inconnu");
        try {
           
            Connection conn = this.pool.getConnection();
            try {
                PreparedStatement req = conn.prepareStatement("UPDATE Groupe SET nom = ? WHERE id = ?");
                ResultSet res;
                //------ Requete preparee -------- 
                req.setString(1, nom);
                req.setInt(2, groupe.getId());
                req.executeUpdate();
                req = conn.prepareStatement("SELECT nom FROM Groupe WHERE id = ?");
                req.setInt(1, groupe.getId());
                res = req.executeQuery();
                if(res.next())
                    groupe.setName(res.getString(1));
         
            } catch (java.sql.SQLIntegrityConstraintViolationException cve) {
                System.err.println("Probleme requete");
                cve.printStackTrace();
            }
            conn.close();
        } catch (Exception e1) {
            System.err.println("Probleme connection");
            e1.printStackTrace();
        }  
    }

}
