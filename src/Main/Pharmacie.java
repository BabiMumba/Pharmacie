package Main;

import Model.Medicament;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Pharmacie {

    public Pharmacie() {
        //
        HashMap<String, Medicament> medicaments = new HashMap<>();
    }

    static void  insertData(Connection conn) {
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
            System.out.println("Médicament ajouté avec succès");
        } catch (SQLException e) {
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