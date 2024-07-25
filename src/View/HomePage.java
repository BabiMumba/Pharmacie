package View;

import Model.Medicament;
import Repository.DataBase;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        grid.setStyle("-fx-background-color: #0eebe3;");

        Text scenetitle = new Text("Gérer les médicaments");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        scenetitle.setFill(Color.DARKBLUE); // Text color
        grid.add(scenetitle, 0, 0, 2, 1);

        Button btnAdd = new Button("Ajouter Médicament");
        Button btnDelete = new Button("Supprimer Médicament");
        btnDelete.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-background-color: #eb240e;");
        Button btnShow = new Button("Afficher Médicaments");

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.getChildren().addAll(btnAdd, btnDelete, btnShow);
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

        btnShow.setOnAction(e -> {
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
                    } finally {
                        progressIndicator.setVisible(false);
                    }
                }
            }).start();
        });

        Scene scene = new Scene(stackPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
