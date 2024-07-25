package Model;

public class Medicament {
    private final String id;
    private final String nom;
    private final String type;

    public Medicament(String id, String nom, String type) {
        this.id = id;
        this.nom = nom;
        this.type = type;
    }

    public String  getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }
    @Override
    public String toString() {
        return "ID: " + id + ", Nom: " + nom + ", Type: " + type;
    }
}