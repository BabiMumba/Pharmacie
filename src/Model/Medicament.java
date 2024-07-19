package Model;

public class Medicament {
    private String id;
    private String nom;
    private String type; // "vente libre" ou "sur ordonnance"

    public Medicament(String id, String nom, String type) {
        this.id = id;
        this.nom = nom;
        this.type = type;
    }

    // Getters et setters
    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nom: " + nom + ", Type: " + type;
    }
}