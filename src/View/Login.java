package View;
import Repository.DataBase;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;

public class Login extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pharmacie");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: #0eebe3;"); // Background color

        Text scenetitle = new Text("Bienvenue à la pharmacie");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setFill(Color.DARKBLUE); // Text color
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("Votre Nom:");
        userName.setTextFill(Color.DARKBLUE); // Label color
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Mot de passe:");
        pw.setTextFill(Color.DARKBLUE); // Label color
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Connexion");
        Button btn2 = new Button("Inscription");
        //changer la couleur du bouton 2
        btn2.setStyle("-fx-background-color: #ebdc0e;"); // Background color
        BorderPane bp = new BorderPane();
        bp.setLeft(btn);
        bp.setRight(btn2);

        grid.add(bp, 1, 4);

        final Text actiontarget = new Text();
        final ProgressIndicator progressIndicator = new ProgressIndicator();
        grid.add(actiontarget, 1, 6);

        btn.setOnAction(e -> {
            if (userTextField.getText().isEmpty() || pwBox.getText().isEmpty()) {
                showAlert(AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            } else {
                progressIndicator.setProgress(0.5);
                actiontarget.setFill(Color.GREEN);
                String nom = userTextField.getText();
                String mot_passe = pwBox.getText();
                Connection conn = DataBase.connect();
                if (conn != null) {
                    boolean isUserExist = DataBase.saveUser(conn, nom, mot_passe);
                    if (isUserExist) {
                        actiontarget.setText("Connexion réussie");
                        progressIndicator.setProgress(1);
                    } else {
                        actiontarget.setText("Nom d'utilisateur ou mot de passe incorrect");
                        progressIndicator.setProgress(0);
                    }
                } else {
                    actiontarget.setText("Connexion échouée");
                }
            }
        });

        Scene scene = new Scene(grid, 600, 400);
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
}
