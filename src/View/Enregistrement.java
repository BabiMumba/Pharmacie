package View;

import Model.User;
import Repository.DataBase;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;

public class Enregistrement extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("Inscription");

        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: #e6f2ff;");
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(15);

        Image image = new Image(new FileInputStream("src/ressource/logo.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        vbox.getChildren().add(imageView);

        Text scenetitle = new Text("Inscription");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 24));
        scenetitle.setFill(Color.DARKBLUE);
        Text subtitle = new Text("Remplissez les informations ci-dessous pour créer un compte");
        subtitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        subtitle.setFill(Color.DARKBLUE);

        vbox.getChildren().add(scenetitle);
        vbox.getChildren().add(subtitle);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: #e6f2ff;");

        Label matriculeLabel = new Label("Matricule:");
        matriculeLabel.setTextFill(Color.DARKBLUE);
        TextField matriculeTextField = new TextField();
        matriculeTextField.setPromptText("Entrez votre matricule");
        matriculeTextField.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 5px;");
        grid.add(matriculeLabel, 0, 0);
        grid.add(matriculeTextField, 1, 0);

        Label nomLabel = new Label("Nom:");
        nomLabel.setTextFill(Color.DARKBLUE);
        TextField nomTextField = new TextField();
        nomTextField.setPromptText("Entrez votre nom");
        nomTextField.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 5px;");
        grid.add(nomLabel, 0, 1);
        grid.add(nomTextField, 1, 1);

        Label posteLabel = new Label("Poste Nom:");
        posteLabel.setTextFill(Color.DARKBLUE);
        TextField posteTextField = new TextField();
        posteTextField.setPromptText("Entrez votre poste");
        posteTextField.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 5px;");
        grid.add(posteLabel, 0, 2);
        grid.add(posteTextField, 1, 2);

        Label motdepasseLabel = new Label("Mot de passe:");
        motdepasseLabel.setTextFill(Color.DARKBLUE);
        PasswordField motdepasseTextField = new PasswordField();
        motdepasseTextField.setPromptText("Entrez votre mot de passe");
        motdepasseTextField.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 5px;");
        grid.add(motdepasseLabel, 0, 3);
        grid.add(motdepasseTextField, 1, 3);

        Button btn = new Button("S'inscrire");
        btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5px;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5px;"));
        btn.setOnAction(event -> {
            String matriculeText = matriculeTextField.getText();
            String nomText = nomTextField.getText();
            String posteText = posteTextField.getText();
            String motdepasseText = motdepasseTextField.getText();
            if (matriculeText.isEmpty() || nomText.isEmpty() || posteText.isEmpty() || motdepasseText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs");
                return;
            }
            Connection connection = DataBase.connect();
            if (connection == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de se connecter à la base de données");
                return;
            } else {
                User user = new User(matriculeText, nomText, posteText, motdepasseText);
                if (DataBase.CreatUser(connection, user)) {
                    saveName(nomText, primaryStage);
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Utilisateur enregistré avec succès");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'enregistrer l'utilisateur");
                }
            }
        });

        HBox hBoxButton = new HBox(10);
        hBoxButton.setAlignment(Pos.BOTTOM_RIGHT);
        hBoxButton.getChildren().add(btn);
        grid.add(hBoxButton, 1, 4);


        vbox.getChildren().add(grid);
        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    //fonction pour sauvegarder le nom dans un fichier
    public void saveName(String nom, Stage primaryStage) {
        try {
            FileWriter myWriter = new FileWriter("src/ressource/nom.txt");
            myWriter.write(nom);
            myWriter.close();
            //si le nom est bien enregistré
            try {
                new HomePage().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //si on appui sur la touche echap on ferme la fenetre(la fonction est appelée dans la classe Login dans la fonction start)
    public void toucheEchap(Stage primaryStage) {
        primaryStage.close();
    }
}