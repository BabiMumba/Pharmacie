package Model;


public class User {
    private String matricule;
    private String nom;
    private String poste_nom;
    private String motdepasse;

    public User(String matricule, String nom, String poste_nom, String motdepasse) {
        this.matricule = matricule;
        this.nom = nom;
        this.poste_nom = poste_nom;
        this.motdepasse = motdepasse;
    }

    public String getMatricule() {
        return matricule;
    }

    public String getNom() {
        return nom;
    }

    public String getPoste_nom() {
        return poste_nom;
    }

    public String getMotdepasse() {
        return motdepasse;
    }
}
