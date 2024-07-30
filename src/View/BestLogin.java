package View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BestLogin extends Application {

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        // Labels and fields
        Label emailLabel = new Label("Email or mobile phone number");
        TextField emailField = new TextField();
        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        Hyperlink forgotPasswordLink = new Hyperlink("Forgot Password");

        // Login button
        Button loginButton = new Button("Login");

        // Keep me signed in checkbox
        CheckBox keepSignedIn = new CheckBox("Keep me signed in.");
        Hyperlink detailsLink = new Hyperlink("Details");

        // New to Amazon section
        Label newToAmazonLabel = new Label("New to Amazon?");
        Button createAccountButton = new Button("Create your Amazon account");

        // Layout setup
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);
        //ajoute le logo
        Image image = new Image(new FileInputStream("src/ressource/logo.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);
        mainLayout.getChildren().add(imageView);


        VBox emailBox = new VBox(5, emailLabel, emailField);
        VBox passwordBox = new VBox(5, passwordLabel, passwordField, forgotPasswordLink);
        HBox keepSignedInBox = new HBox(5, keepSignedIn, detailsLink);
        VBox newToAmazonBox = new VBox(10, newToAmazonLabel, createAccountButton);

        mainLayout.getChildren().addAll(emailBox, passwordBox, loginButton, keepSignedInBox, newToAmazonBox);

        // Styling
        emailField.setPrefWidth(300);
        passwordField.setPrefWidth(300);
        loginButton.setPrefWidth(300);
        createAccountButton.setPrefWidth(300);
        emailLabel.setStyle("-fx-font-size: 14;");
        passwordLabel.setStyle("-fx-font-size: 14;");
        newToAmazonLabel.setStyle("-fx-font-size: 14;");
        mainLayout.setStyle("-fx-background-color: white; -fx-border-color: lightgrey; -fx-border-width: 1;");

        // Scene setup
        Scene scene = new Scene(mainLayout, 400, 400);
        primaryStage.setTitle("Amazon Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
