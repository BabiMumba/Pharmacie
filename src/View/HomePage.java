package View;

import Model.Medicament;
import Repository.DataBase;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HomePage extends Application {

    private TableView<Medicament> table = new TableView<>();
    private ObservableList<Medicament> data = FXCollections.observableArrayList();
    private ProgressIndicator progressIndicator = new ProgressIndicator();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Accueil");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: #f0f4f7;");

        Text scenetitle = new Text("Gestion des Médicaments");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setFill(Color.DARKBLUE);
        scenetitle.setStyle("-fx-font-weight: bold;");
        HBox hbTitle = new HBox(10);
        hbTitle.setAlignment(Pos.CENTER);
        hbTitle.getChildren().add(scenetitle);
        grid.add(hbTitle, 0, 0);

        Button btnAdd = new Button("Ajouter");
        btnAdd.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        Button btnDelete = new Button("Supprimer");
        btnDelete.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        Button btnShow = new Button("Afficher");
        btnShow.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        Button btnSearch = new Button("Rechercher");
        btnSearch.setStyle("-fx-background-color: #FFC107; -fx-text-fill: white;");
        Button btnCount = new Button("Nombre");
        btnCount.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white;");
        Button btnUpdate = new Button("Modifier");
        btnUpdate.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white;");

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().addAll(btnAdd, btnDelete, btnShow, btnSearch, btnCount, btnUpdate);
        grid.add(hbBtn, 0, 1);

        TableColumn<Medicament, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Medicament, String> nameCol = new TableColumn<>("Nom");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        TableColumn<Medicament, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        table.getColumns().addAll(idCol, nameCol, typeCol);

        VBox vbox = new VBox(10, grid, table);
        vbox.setPadding(new Insets(10));
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(vbox, progressIndicator);
        progressIndicator.setVisible(false);

        showMedicaments();

        // Événements
        btnShow.setOnAction(e -> showMedicaments());
        btnCount.setOnAction(e -> showMedicamentCountDialog());
        btnAdd.setOnAction(e -> showAddMedicamentDialog());
        btnDelete.setOnAction(e -> showDeleteMedicamentDialog());
        btnSearch.setOnAction(e -> showSearchMedicamentDialog());
        btnUpdate.setOnAction(e -> showUpdateMedicamentDialog());

        Scene scene = new Scene(stackPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void showMedicaments() {
        progressIndicator.setVisible(true);
        data.clear();
        new Thread(() -> {
            Connection conn = DataBase.connect();
            if (conn != null) {
                try {
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM medicament");
                    while (rs.next()) {
                        data.add(new Medicament(rs.getString("id"), rs.getString("nom"), rs.getString("type_m")));
                    }
                    table.setItems(data);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de récupérer les médicaments\n" + ex.getMessage());

                } finally {
                    progressIndicator.setVisible(false);
                }
            }else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de se connecter à la base de données");
            }
        }).start();
    }
    private void showAddMedicamentDialog() {
        Dialog<Medicament> dialog = new Dialog<>();
        dialog.setTitle("Ajouter Médicament");
        dialog.setHeaderText("Entrez les détails du médicament");

        // Set the button types.
        ButtonType addButtonType = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        // Create the ID, Name, and Type fields.
        TextField idField = new TextField();
        idField.setPromptText("ID");
        TextField nameField = new TextField();
        nameField.setPromptText("Nom");
        TextField typeField = new TextField();
        typeField.setPromptText("Type");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().addAll(new Label("ID:"), idField, new Label("Nom:"), nameField, new Label("Type:"), typeField);
        dialog.getDialogPane().setContent(vbox);

        // Convert the result to a Medicament object when the add button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Medicament(idField.getText(), nameField.getText(), typeField.getText());
            }
            return null;
        });

        Optional<Medicament> result = dialog.showAndWait();
        result.ifPresent(medicament -> {
            Connection conn = DataBase.connect();
            if (conn != null) {
                String[] values = {medicament.getId(), medicament.getNom(), medicament.getType()};
                DataBase.insertData(conn, "medicament", values);
                data.add(medicament);
                table.setItems(data);
            }
        });
    }
    private void showDeleteMedicamentDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Supprimer Médicament");
        dialog.setHeaderText("Entrez l'ID du médicament à supprimer");

        // Set the button types.
        ButtonType deleteButtonType = new ButtonType("Supprimer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(deleteButtonType, ButtonType.CANCEL);

        // Create the ID field.
        TextField idField = new TextField();
        idField.setPromptText("ID");

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().addAll(new Label("ID:"), idField);
        dialog.getDialogPane().setContent(vbox);

        // Convert the result to an ID string when the delete button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == deleteButtonType) {
                return idField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(id -> {
            Connection conn = DataBase.connect();
            if (conn != null) {
                DataBase.deleteData(conn, "medicament", id);
                data.removeIf(medicament -> medicament.getId().equals(id));
                table.setItems(data);
            }
        });
    }
    private void showSearchMedicamentDialog() {
    Dialog<String[]> dialog = new Dialog<>();
    dialog.setTitle("Rechercher Médicament");
    dialog.setHeaderText("Entrez le nom ou l'ID du médicament à rechercher");

    // Set the button types.
    ButtonType searchButtonType = new ButtonType("Rechercher", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(searchButtonType, ButtonType.CANCEL);

    // Create the ID and Name fields.
    TextField idField = new TextField();
    idField.setPromptText("ID");
    TextField nameField = new TextField();
    nameField.setPromptText("Nom");

    VBox vbox = new VBox();
    vbox.setSpacing(10);
    vbox.getChildren().addAll(new Label("ID:"), idField, new Label("Nom:"), nameField);
    dialog.getDialogPane().setContent(vbox);

    // Convert the result to an array of strings when the search button is clicked.
    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == searchButtonType) {
            return new String[]{idField.getText(), nameField.getText()};
        }
        return null;
    });

    Optional<String[]> result = dialog.showAndWait();
    result.ifPresent(searchParams -> {
        String id = searchParams[0];
        String name = searchParams[1];
        Connection conn = DataBase.connect();
        if (conn != null) {
            data.clear();
            try {
                String query = "SELECT * FROM medicament WHERE id = ? OR nom = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, name);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    data.add(new Medicament(rs.getString("id"), rs.getString("nom"), rs.getString("type_m")));
                }
                table.setItems(data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    });
}
    private void showMedicamentCountDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Nombre de Médicaments en Stock");
        dialog.setHeaderText(null);

        // Set the button types.
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        // Retrieve the count of medicaments from the database.
        Connection conn = DataBase.connect();
        int count = 0;
        if (conn != null) {
            try {
                String query = "SELECT COUNT(*) AS count FROM medicament";
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    count = rs.getInt("count");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Display the count in the dialog.
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().add(new Label("Nombre de médicaments en stock: " + count));
        dialog.getDialogPane().setContent(vbox);

        dialog.showAndWait();
    }
    private void showUpdateMedicamentDialog() {
    Dialog<String[]> dialog = new Dialog<>();
    dialog.setTitle("Modifier Médicament");
    dialog.setHeaderText("Entrez l'ID du médicament à modifier et les nouvelles valeurs");

    // Set the button types.
    ButtonType updateButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
    dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

    // Create the ID, Name, and Type fields.
    TextField idField = new TextField();
    idField.setPromptText("ID");
    TextField nameField = new TextField();
    nameField.setPromptText("Nouveau Nom");
    TextField typeField = new TextField();
    typeField.setPromptText("Nouveau Type");

    VBox vbox = new VBox();
    vbox.setSpacing(10);
    vbox.getChildren().addAll(new Label("ID:"), idField, new Label("Nouveau Nom:"), nameField, new Label("Nouveau Type:"), typeField);
    dialog.getDialogPane().setContent(vbox);

    // Convert the result to an array of strings when the update button is clicked.
    dialog.setResultConverter(dialogButton -> {
        if (dialogButton == updateButtonType) {
            return new String[]{idField.getText(), nameField.getText(), typeField.getText()};
        }
        return null;
    });

    Optional<String[]> result = dialog.showAndWait();
    result.ifPresent(updateParams -> {
        String id = updateParams[0];
        String newName = updateParams[1];
        String newType = updateParams[2];
        Connection conn = DataBase.connect();
        if (conn != null) {
            try {
                String query = "UPDATE medicament SET nom = ?, type_m = ? WHERE id = ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, newName);
                preparedStatement.setString(2, newType);
                preparedStatement.setString(3, id);
                preparedStatement.executeUpdate();
                data.clear();
                ResultSet rs = preparedStatement.executeQuery("SELECT * FROM medicament");
                while (rs.next()) {
                    data.add(new Medicament(rs.getString("id"), rs.getString("nom"), rs.getString("type_m")));
                }
                table.setItems(data);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    });
}

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
