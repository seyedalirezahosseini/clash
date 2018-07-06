package graphic.VillageUi;

import graphic.GraphicConstants;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Village;

public class VillageNameUI {

    static Stage stage;
    static Scene scene;

    public static void openSetNameStage(Village village) {
        TextField nameTextField = new TextField();
        nameTextField.setPromptText("Choose a name for your village");

        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(event -> {
            village.setName(nameTextField.getText());
            stage.close();
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(nameTextField, confirmButton);

        scene = new Scene(vBox, GraphicConstants.VillageNewNameSceneWidth, GraphicConstants.VillageNewNameSceneHeight);

        stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);

        stage.show();
    }

    public static void openChangeNameStage(Village village) {
        // TODO: 6/11/2018
    }
}
