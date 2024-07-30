package View;
import Repository.DataBase;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
import java.sql.Connection;

public class Login extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        primaryStage.setTitle("Pharmacie");

        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: #e6f2ff;"); // Couleur de fond bleu clair
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(15);

        Image image = new Image(new FileInputStream("src/ressource/logo.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        vbox.getChildren().add(imageView);
        Text scenetitle = new Text("Gestion de Pharmacie");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 24));
        scenetitle.setFill(Color.DARKBLUE);
        Text soustrie = new Text("Connectez-vous à votre compte pour continuer");
        soustrie.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
        soustrie.setFill(Color.DARKBLUE); // Couleur du titre en bleu foncé

        vbox.getChildren().add(scenetitle);
        vbox.getChildren().add(soustrie);

        //section de connexion
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: #e6f2ff;"); // Couleur de fond bleu clair
        Label userName = new Label("Votre Nom:");
        userName.setTextFill(Color.DARKBLUE); // Couleur du texte en bleu foncé
        grid.add(userName, 0, 0);

        TextField userTextField = new TextField();
        userTextField.setPromptText("Entrez votre nom"); // Ajoute un texte d'indication
        userTextField.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 5px;"); // Style du champ de texte
        grid.add(userTextField, 1, 0);

        Label pw = new Label("Mot de passe:");
        pw.setTextFill(Color.DARKBLUE); // Couleur du texte en bleu foncé
        grid.add(pw, 0, 1);

        PasswordField pwBox = new PasswordField();
        pwBox.setPromptText("Entrez votre mot de passe"); // Ajoute un texte d'indication
        pwBox.setStyle("-fx-background-color: #fff; -fx-border-color: #ccc; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 5px;"); // Style du champ de mot de passe
        grid.add(pwBox, 1, 1);

        Button btn = new Button("Connexion");
        btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5px;"); // Style du bouton Connexion
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        Text text = new Text("Vous n'avez pas de compte?");
        text.setFill(Color.BLACK); // Couleur du texte en bleu foncé

        Text text2 = new Text("Inscrivez-vous");
        text2.setFill(Color.BLUE); // Couleur du texte en bleu
        // Couleur du texte en bleu foncé quand la souris est dessus et agrandit la taille du texte
        text2.setOnMouseEntered(e -> {
            text2.setFill(Color.DARKBLUE);
        });

        text2.setOnMouseExited(e -> text2.setFill(Color.BLUE)); // Couleur du texte en bleu quand la souris n'est pas dessus
        text2.setOnMouseClicked(e -> {
            try {
                new Enregistrement().start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }); // Ouvre la page d'inscription quand on clique sur le texte
        hbBtn.getChildren().addAll(text, text2);

        grid.add(btn, 1, 3);// Ajoute le bouton Connexion à la grille à la position (ligne 1, colonne 3)
        grid.add(hbBtn, 1, 4);


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
                    boolean isUserExist = DataBase.LoginUser(conn, nom, mot_passe);
                    if (isUserExist) {
                        actiontarget.setText("Connexion réussie");
                        progressIndicator.setProgress(1);
                        Platform.runLater(() -> {
                            try {
                                new HomePage().start(primaryStage);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });
                    } else {
                        actiontarget.setFill(Color.RED);
                        actiontarget.setText("Nom d'utilisateur ou mot de passe incorrect");
                        progressIndicator.setProgress(0);
                    }
                } else {
                    actiontarget.setText("Connexion échouée");
                }
            }
        });

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
}
