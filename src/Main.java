import Model.Medicament;

import java.io.IOException;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static final String DB_URL = "jdbc:mysql://localhost:3306/pharmacie";
    static final String USER = "root";

    public static void main(String[] args) {

        Connection conn = null;

        //tester la connexion avec la base de données
        try {
            conn = DriverManager.getConnection(DB_URL, USER, "");
           // System.out.println("Connected to the database");
        } catch (SQLException e) {
            System.out.println("An error occurred. Maybe user/password is invalid");

            e.printStackTrace();
        }
        Pharmacie pharmacie = new Pharmacie();
        Scanner scanner = new Scanner(System.in);
        boolean continuer = true;

        while (continuer) {
            System.out.println("Menu:");
            System.out.println("1. Ajouter un médicament");
            System.out.println("2. Supprimer un médicament");
            System.out.println("3. Rechercher un médicament par ID");
            System.out.println("4. Afficher les médicaments par type");
            System.out.println("5. Modifier un médicament");
            System.out.println("6. Lister les médicaments par lettre");
            System.out.println("7. Afficher tous les médicaments");
            System.out.println("8. Afficher le nombre de médicaments");
            System.out.println("9. Sauvegarder les médicaments");
            System.out.println("10. Charger les médicaments");
            System.out.println("11. Quitter");
            System.out.print("Choisissez une option: ");

            try {
                int choix = scanner.nextInt();
                switch (choix) {
                    case 1:
                        //pour ajouter un médicament
                        insertData(conn, pharmacie);
                        break;
                    case 2:
                        //pour supprimer un médicament
                        System.out.print("Entrez l'ID du médicament à supprimer: ");
                        String idMedicament = scanner.next();
                        pharmacie.supprimerMedicament(idMedicament);
                        break;
                    case 3:
                        System.out.print("Entrez l'ID du médicament à rechercher: ");
                        String ida = scanner.next(); // Read the medication ID from the user
                        Medicament medicaments = pharmacie.rechercherParId(ida);
                        if (medicaments != null) {
                            System.out.println("Médicament trouvé: " + medicaments);
                        } else {
                            System.out.println("Aucun médicament trouvé avec l'ID: " + ida);
                        }
                        break;
                    case 4:
                        //pour afficher les médicaments par type
                        System.out.print("Entrez le type de médicament à afficher: ");
                        String typeMedicament = scanner.next();
                        pharmacie.afficherMedicamentsParType(typeMedicament);
                        break;
                    case 5:
                        //pour modifier un médicament
                        System.out.print("Entrez l'ID du médicament à modifier: ");
                        String idMedicamentModif = scanner.next();
                        System.out.print("Entrez le nouveau nom du médicament: ");
                        String nomMedicamentModif = scanner.next();
                        System.out.print("Entrez le nouveau type du médicament: ");
                        String typeMedicamentModif = scanner.next();
                        pharmacie.modifierMedicament(idMedicamentModif, nomMedicamentModif, typeMedicamentModif);

                        break;
                    case 6:
                        //pour lister les médicaments par lettre
                        System.out.print("Entrez la lettre du médicament à lister: ");
                        char lettre = scanner.next().charAt(0);
                        pharmacie.listerMedicamentsParLettre(lettre);

                        break;
                    case 7:
                        pharmacie.afficherTousLesMedicaments();
                        break;
                    case 8:
                        pharmacie.afficherNombreMedicaments();
                        break;
                    case 9:
                        pharmacie.sauvegarderMedicaments();
                        break;
                    case 10:
                        try {
                            pharmacie.chargerMedicaments();
                            System.out.println("Médicaments chargés avec succès.");
                        } catch (IOException e) {
                            System.out.println("Erreur lors du chargement des médicaments: " + e.getMessage());
                        }
                        break;
                    case 11:
                        continuer = false;
                        break;
                    default:
                        System.out.println("Option invalide. Veuillez réessayer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide. Veuillez entrer un nombre.");
                scanner.next(); // to consume the invalid input
            } catch (IOException e) {
                System.out.println("Erreur lors de la sauvegarde des médicaments.");
            }

            if (continuer) {
                System.out.println("Appuyez sur Entrée pour continuer...");
                scanner.nextLine(); // to catch the nextInt newline
                scanner.nextLine(); // to actually pause
            }
        }

    }
    public static void  insertData(Connection conn, Pharmacie pharmacie) {
        //fonction pour insérer les données dans la base de données
        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez l'ID du médicament: ");
        String id = scanner.next();
        System.out.print("Entrez le nom du médicament: ");
        String nom = scanner.next();
        System.out.print("Entrez le type du médicament: ");
        String type = scanner.next();
        Medicament medicament = new Medicament(id, nom, type);
        try {
            String query = "INSERT INTO medicament (id, nom, type_m) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, medicament.getId());
            preparedStatement.setString(2, medicament.getNom());
            preparedStatement.setString(3, medicament.getType());
            preparedStatement.executeUpdate();
            pharmacie.ajouterMedicament(medicament);
            System.out.println("Médicament ajouté avec succès");
        } catch (SQLException | MedicamentExistantException e) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            e.printStackTrace();
        }
    }
    public static void displayData(Connection conn) {
        //fonction pour afficher les données de la base de données
        try {
            String query = "SELECT * FROM medicament";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            //afficher les données dans un tableau
            System.out.println("ID\tNom\tType");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("id") + "\t" +
                        resultSet.getString("nom") + "\t" +
                        resultSet.getString("type_m"));
            }
        } catch (SQLException e) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            e.printStackTrace();
        }
    }
    public static void updateData(Connection conn) {
        //fonction pour mettre à jour les données de la base de données
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez l'ID du médicament à modifier: ");
            String id = scanner.next();
            System.out.print("Entrez le nouveau nom du médicament: ");
            String nom = scanner.next();
            System.out.print("Entrez le nouveau type du médicament: ");
            String type = scanner.next();
            String query = "UPDATE medicament SET nom = ?, type_m = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, id);
            preparedStatement.executeUpdate();
            System.out.println("Médicament mis à jour avec succès");
        } catch (SQLException e) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            e.printStackTrace();
        }
    }
    public static void deleteData(Connection conn) {
        //fonction pour supprimer les données de la base de données
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez l'ID du médicament à supprimer: ");
            String id = scanner.next();
            String query = "DELETE FROM medicament WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Médicament supprimé avec succès");
        } catch (SQLException e) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            e.printStackTrace();
        }
    }
}