package fr.iutfbleau.projetIHM2022FI2.API;
public enum TypeGroupe {
    ROOT ("Tous les étudiants"), PARTITION ("partition"), FREE ("libre") ;

    private final String name;       

    private TypeGroupe(String s) {
        name = s;
    }
}
