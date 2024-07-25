package Repository;

import java.sql.*;

public class DataBase {

    static final String DB_URL = "jdbc:mysql://localhost:3306/pharmacie";
    static final String USER = "root";
    //save user

   //fonction qui va permettre de se connecter à la base de données
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, "");
        } catch (SQLException e) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            e.printStackTrace();
        }
        return conn;
    }

    //fonction pour insérer les données dans la base de données

    public static boolean saveUser(Connection conn, String nom, String mot_passe) {
        boolean isUserExist;
        try {
            String query = "INSERT INTO PERSONNE (nom, motdepasee) VALUES (?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, mot_passe);
            preparedStatement.executeUpdate();
            System.out.println("Utilisateur ajouté avec succès");
            isUserExist = true;
        } catch (SQLException e) {
            System.out.println("Une erreur est survenue. Peut-être que le nom d'utilisateur ou le mot de passe est invalide.");
            e.printStackTrace();
            isUserExist = false;
        }
        return isUserExist;
    }
    public static void insertData(Connection conn, String table, String[] values) {
        try {
            String query = "INSERT INTO " + table + " VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setString(i + 1, values[i]);
            }
            preparedStatement.executeUpdate();
            System.out.println("Médicament ajouté avec succès");
        } catch (SQLException e) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            e.printStackTrace();
        }
    }

    //fonction pour afficher les données de la base de données
    public static void displayData(Connection conn, String table) {
        try {
            String query = "SELECT * FROM " + table;
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            //afficher les données dans un tableau
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

    //fonction pour mettre à jour les données de la base de données
    public static void updateData(Connection conn, String table, String[] values) {
        try {
            String query = "UPDATE " + table + " SET nom = ?, type_m = ? WHERE id = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, values[1]);
            preparedStatement.setString(2, values[2]);
            preparedStatement.setString(3, values[0]);
            preparedStatement.executeUpdate();
            System.out.println("Médicament mis à jour avec succès");
        } catch (SQLException e) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            e.printStackTrace();
        }
    }

}
