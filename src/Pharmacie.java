

import Model.Medicament;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Pharmacie {
    private HashMap<String, Medicament> medicaments;//

    public Pharmacie() {
        medicaments = new HashMap<>();
    }


    public void sauvegarderMedicaments() throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(medicaments);
        try (FileWriter writer = new FileWriter("medicaments.json")) {
            writer.write(json);
        }
    }
    public void chargerMedicaments() throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, Medicament>>(){}.getType();
        try (FileReader reader = new FileReader("medicaments.json")) {
            medicaments = gson.fromJson(reader, type);
        }
    }
    public void ajouterMedicament(Medicament medicament) throws MedicamentExistantException {
        if (medicaments.containsKey(medicament.getId())) {
            throw new MedicamentExistantException("Un médicament avec cet ID existe déjà.");
        } else {
            medicaments.put(medicament.getId(), medicament);
            System.out.println("Médicament ajouté avec succès.");
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