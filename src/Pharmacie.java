import java.util.HashMap;
import java.util.Scanner;

public class Pharmacie {
    private HashMap<String, Medicament> medicaments;//

    public Pharmacie() {
        medicaments = new HashMap<>();
    }

    public void ajouterMedicament(Medicament medicament) {
        // Ajouter un médicament à la pharmacie en utilisant l'ID comme clé si l'ID n'existe pas déjà
        if (!medicaments.containsKey(medicament.getId())) {
            medicaments.put(medicament.getId(), medicament);
            System.out.println("Médicament ajouté avec succès.");
        } else {
            System.out.println("Un médicament avec cet ID existe déjà.");
        }
    }

    public void supprimerMedicament(String id) {
        medicaments.remove(id);
    }

    public Medicament rechercherParId(String id) {
        return medicaments.get(id);
    }

    public void afficherMedicamentsParType(String type) {
        medicaments.values().stream()
                .filter(medicament -> medicament.getType().equals(type))
                .forEach(System.out::println);
    }

    public void modifierMedicament(String id, String nouveauNom, String nouveauType) {
        Medicament medicament = medicaments.get(id);
        if (medicament != null) {
            medicament.setNom(nouveauNom);
            medicament.setType(nouveauType);
        }
    }

    public void listerMedicamentsParLettre(char lettre) {
        medicaments.values().stream()
                .filter(medicament -> medicament.getNom().charAt(0) == lettre)
                .forEach(System.out::println);
    }

    public void afficherNombreMedicaments() {
        System.out.println("Nombre total de médicaments: " + medicaments.size());
    }
    public void afficherTousLesMedicaments() {
        medicaments.values().forEach(System.out::println);
    }

    public void rechercherParNom(String nom) {
        medicaments.values().stream()
                .filter(medicament -> medicament.getNom().equalsIgnoreCase(nom))
                .forEach(System.out::println);
    }
}