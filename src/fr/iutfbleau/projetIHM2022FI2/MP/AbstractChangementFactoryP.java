package fr.iutfbleau.projetIHM2022FI2.MP;
import fr.iutfbleau.projetIHM2022FI2.API.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
/**
 * Usine abstraite gérant l'ensemble des changements.
 * 
 */

public class AbstractChangementFactoryP implements AbstractChangementFactory {

    // l'usine à groupe travaillant en tandem avec cette usine.
    private AbstractGroupeFactory agf;

    // On utilise une table de hachage pour retrouver facilement un changement (à partir de son id).
    // Si il y a beaucoup de changements c'est plus rapide que de parcourir toute une liste.
    private HashMap<Integer,Changement> brain;

    public AbstractChangementFactoryP(AbstractGroupeFactory agf){
        Objects.requireNonNull(agf,"On ne peut pas créer une usine à changement dont l'usine à groupe parternaire est null");
        this.agf=agf;
        this.loadChangements();
    }
                                        

    private void loadChangements() {
        try {
            this.brain=new HashMap<Integer,Changement>();
            Connection conn = DriverManager.getConnection(AbstractGroupeFactoryP.URL, AbstractGroupeFactoryP.USER, AbstractGroupeFactoryP.MDP);
            try {
                PreparedStatement req = conn.prepareStatement("SELECT * FROM Changement");
                //------ Requete preparee -------- 
                ResultSet res = req.executeQuery();
                while(res.next()) {
                    int id = res.getInt(1);
                    Groupe A = findGroupe(res.getInt(2));
                    Groupe B = findGroupe(res.getInt(3));
                    Etudiant e = findEtudiant(res.getInt(4));
                    String explications = res.getString(5);
                    if(A != null && B != null && e != null) {
                        Changement c = new ChangementP(id,A,e,B,explications);
                        this.brain.put(Integer.valueOf(c.getId()),c);   
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

    private Groupe findGroupe(int id) {
        Queue<Groupe> file = new ArrayDeque<Groupe>();
        Groupe root = this.agf.getPromotion();
        file.offer(root);
        while (!file.isEmpty()) {
            root = file.poll();
            if(root.getId() == id)
                return root;
            for(Groupe child : root.getSousGroupes())
                file.offer(child);
        }
        return null;
    }

    private Etudiant findEtudiant(int id) {
        Groupe root = this.agf.getPromotion();
        for(Etudiant e : root.getEtudiants()) {
            if(e.getId() == id)
                return e;
        }
        return null;
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
     * @param c le changement
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

        if(!a.getEtudiants().contains(e)) throw new IllegalStateException("Le groupe de départ ne contient pas l'étudiant. Impossible à mettre en oeuvre.");
        
        try {
           
            Connection conn = DriverManager.getConnection(AbstractGroupeFactoryP.URL, AbstractGroupeFactoryP.USER, AbstractGroupeFactoryP.MDP);
            try {
                PreparedStatement req = conn.prepareStatement("DELETE FROM Changement WHERE id = ?");
                req.setInt(1, c.getId());
                req.executeUpdate();
                agf.dropFromGroupe(a,e);
                agf.addToGroupe(b,e);
                // En cas de succès, on enlève le changement du cerveau
                this.brain.remove(Integer.valueOf(c.getId()));
            } catch (Exception e1) {
                System.err.println("Probleme requete");
                e1.printStackTrace();
            } 
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    
    /**
     * permet de supprimer un changement connu de l'usine abstraite.
     * @param c le changement
     * @throws java.lang.NullPointerException si un argument est null
     * @throws java.lang.IllegalArgumentException si inconnu de l'usine abstraite
     */
    public void deleteChangement(Changement c){
        Objects.requireNonNull(c,"On ne peut pas demander la suppression d'un changement qui est null");
        try {
           
            Connection conn = DriverManager.getConnection(AbstractGroupeFactoryP.URL, AbstractGroupeFactoryP.USER, AbstractGroupeFactoryP.MDP);
            try {
                PreparedStatement req = conn.prepareStatement("DELETE FROM Changement WHERE id = ?");
                req.setInt(1, c.getId());
                int count = req.executeUpdate();
                if(count == 0) {
                    loadChangements();
                    throw new IllegalArgumentException("le changement est inconnu");
                }
                this.brain.remove(Integer.valueOf(c.getId()));
            } catch (SQLException e1) {
                System.err.println("Probleme requete");
                e1.printStackTrace();
            }
            conn.close();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }  
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
        if(!agf.knows(A) || !agf.knows(B))
            throw new IllegalArgumentException("Les groupes ne sont pas connus de la factory");
        if(!A.getEtudiants().contains(e))
            throw new IllegalArgumentException("Les groupes ne sont pas connus de la factory");
        if(!A.getPointPoint().equals(B.getPointPoint()))
            throw new IllegalArgumentException("Les groupes ne sont pas frères");
        try {
           
            Connection conn = DriverManager.getConnection(AbstractGroupeFactoryP.URL, AbstractGroupeFactoryP.USER, AbstractGroupeFactoryP.MDP);
            try {
                PreparedStatement req = conn.prepareStatement("INSERT INTO Changement(GroupeA, GroupeB, Etudiant, explications) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                //------ Requete preparee -------- 
                req.setInt(1, A.getId());
                req.setInt(2, B.getId());
                req.setInt(3, e.getId());
                req.setString(4, explication);
                req.executeUpdate();
                ResultSet res = req.getGeneratedKeys();
                if(res.next()) {
                    Changement c = new ChangementP(res.getInt(1), A, e, B, explication);
                    this.brain.put(c.getId(), c);
                }
            } catch (Exception e1) {
                System.err.println("Probleme requete");
                e1.printStackTrace();
            }
            conn.close();
        } catch (Exception e2) {
            e2.printStackTrace();
        }  
    }
    
}
