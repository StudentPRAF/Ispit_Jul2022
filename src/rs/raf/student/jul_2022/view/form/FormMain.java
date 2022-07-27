package rs.raf.student.jul_2022.view.form;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import rs.raf.student.jul_2022.database.Database;
import rs.raf.student.jul_2022.model.Package;
import rs.raf.student.jul_2022.model.Status;

public class FormMain extends TilePane {

    private Button buttonFilter   = new Button("Filtriraj");
    private Button buttonSend     = new Button("Posalji");
    private Button buttonReceived = new Button("Potvrdi prijem");
    private Button buttonReturned = new Button("Potvrdi vracanje");
    private Button buttonSave     = new Button("Ispis");

    private ComboBox<String> boxStatus = new ComboBox<>();
    private ComboBox<String> boxCities = new ComboBox<>();

    private Label labelSearch = new Label("Pretraga:");
    private Label labelError  = new Label("");

    private TextField textFilter = new TextField();

    private TableView<Package>           tablePackages         = new TableView<>();
    private TableColumn<Package, String> columnPackageID       = new TableColumn<>("ID");
    private TableColumn<Package, String> columnPackageFromCity = new TableColumn<>("Od");
    private TableColumn<Package, String> columnPackageToCity   = new TableColumn<>("Za");
    private TableColumn<Package, Status> columnPackageStatus   = new TableColumn<>("Status");

    public FormMain() {
        configure();
        initialize();
        associate();
    }

    private void configure() {
        setAlignment(Pos.CENTER);

        columnPackageID.setPrefWidth(90);
        columnPackageFromCity.setPrefWidth(150);
        columnPackageToCity.setPrefWidth(150);
        columnPackageStatus.setPrefWidth(90);
        tablePackages.setPrefWidth(495);

        boxStatus.setItems(FXCollections.observableArrayList(Status.toStringList()));
        boxStatus.getItems().add(0, "Svi statusi");
        boxStatus.getSelectionModel().selectFirst();

        boxCities.setItems(FXCollections.observableArrayList(Database.Cities.get()));
        boxCities.getItems().add(0, "Svi gradovi");
        boxCities.getSelectionModel().selectFirst();

        columnPackageID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        columnPackageStatus.setCellValueFactory(new PropertyValueFactory<>("Status"));
        columnPackageToCity.setCellValueFactory(new PropertyValueFactory<>("ToCity"));
        columnPackageFromCity.setCellValueFactory(new PropertyValueFactory<>("FromCity"));
        tablePackages.getColumns().addAll(columnPackageID, columnPackageFromCity, columnPackageToCity, columnPackageStatus);

        tablePackages.setItems(FXCollections.observableArrayList(Database.Packages.get()));
    }

    private void initialize() {
        HBox main = new HBox();
        main.setSpacing(15);
        main.setPadding(new Insets(15));

        VBox left = new VBox();
        left.setAlignment(Pos.CENTER_LEFT);
        left.setSpacing(15);

        HBox leftFirstRow = new HBox();
        leftFirstRow.setAlignment(Pos.CENTER_LEFT);
        leftFirstRow.setSpacing(15);
        leftFirstRow.getChildren().addAll(labelSearch, textFilter, boxStatus, buttonFilter);

        left.getChildren().addAll(leftFirstRow, tablePackages, labelError);

        VBox right = new VBox();
        right.setAlignment(Pos.CENTER);
        right.setSpacing(15);

        HBox rightLastRow = new HBox();
        rightLastRow.setAlignment(Pos.CENTER);
        rightLastRow.setSpacing(15);
        rightLastRow.setPadding(new Insets(25, 0, 0, 0));
        rightLastRow.getChildren().addAll(boxCities, buttonSave);

        right.getChildren().addAll(buttonSend, buttonReceived, buttonReturned, rightLastRow);

        main.getChildren().addAll(left, right);

        getChildren().add(main);
    }

    private void associate() {
        buttonFilter.setOnAction(event -> {
            tablePackages.getItems().clear();
            tablePackages.getItems().addAll(Database.Packages.filter(textFilter.getText(), boxStatus.getSelectionModel().getSelectedItem()));
        });

        buttonSend.setOnAction(event -> {
            labelError.setText(Database.Packages.setStatusToSent(tablePackages.getSelectionModel().getSelectedItem()));
            tablePackages.refresh();
        });

        buttonReceived.setOnAction(event -> {
            labelError.setText(Database.Packages.setStatusToReceived(tablePackages.getSelectionModel().getSelectedItem()));
            tablePackages.refresh();
        });

        buttonReturned.setOnAction(event -> {
            labelError.setText(Database.Packages.setStatusToReturned(tablePackages.getSelectionModel().getSelectedItem()));
            tablePackages.refresh();
        });

        buttonSave.setOnAction(event -> Database.Packages.save(boxCities.getSelectionModel().getSelectedItem()));
    }

}
