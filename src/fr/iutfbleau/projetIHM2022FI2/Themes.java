package fr.iutfbleau.projetIHM2022FI2;

/**
 * Classe représentant un thème. Un thème est constitué de son nom et du chemin relatif de son fichier JSON
 */
public class Themes {
    private String name;
    private String URL;

    /**
     * Constructeur
     * @param name le nom du thème
     * @param URL le chemin relatif pour le fichier JSON
     */
    public Themes(String name, String URL){
        this.URL = URL;
        this.name = name;
    }

    /**
     * Renvoie le nom du thème
     * @return le nom du thème
     */
    public String getName() {
        return name;
    }

    /**
     * Renvoie le chemin relatif du thème 
     * @return le chemin relatif du thème
     */
    public String getURL() {
        return URL;
    }
}
