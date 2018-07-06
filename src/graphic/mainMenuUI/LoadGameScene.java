package graphic.mainMenuUI;

import graphic.MainStage;
import graphic.VillageUi.VillageUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Village;
import view.Files;

import java.util.ArrayList;

public class LoadGameScene {

    private static Scene scene;
    private static ArrayList<Village> savedVillages;

    static {
        TableColumn<Village, String> nameColumn = new TableColumn<>("Village Name");
        nameColumn.setMinWidth(300);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Village, Integer> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setMinWidth(100);
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        TableView<Village> table = new TableView<>();
        table.getColumns().addAll(nameColumn, scoreColumn);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        table.setItems(getSavedVillages());

        Button back = new Button("Back");
        Button load = new Button("Load");
        Button delete = new Button("Delete");

        load.setDisable(true);
        delete.setDisable(true);

        back.setOnAction(e -> MainStage.getStage().setScene(MainMenuUI.getScene()));
        load.setOnAction(e -> {
            Village village = table.getSelectionModel().getSelectedItem();
            VillageUI villageUI = new VillageUI();
            villageUI.buildTheScene(village);
            MainStage.getStage().setScene(villageUI.getScene());
        });
        table.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            load.setDisable(false);
            delete.setDisable(false);
        });


        HBox hBox = new HBox();
        hBox.getChildren().addAll(back, load, delete);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, hBox);

        scene = new Scene(vBox);
    }

    public static Scene getScene() {
        return scene;
    }

    private static ObservableList<Village> getSavedVillages() {
        ObservableList<Village> savedVillages = FXCollections.observableArrayList();
        LoadGameScene.savedVillages = Files.getSavedVillages();
        savedVillages.addAll(LoadGameScene.savedVillages);
        return savedVillages;
    }

}
