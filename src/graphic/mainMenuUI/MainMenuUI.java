package graphic.mainMenuUI;

import graphic.GraphicConstants;
import graphic.MainStage;
import graphic.VillageUi.VillageNameUI;
import graphic.VillageUi.VillageUI;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Village;

import java.io.File;
import java.net.URL;

public class MainMenuUI {
    private static VillageUI villageUI = new VillageUI();
    private static Scene scene;

    public static final ImageView BACKGROUND = new ImageView(new File(System.getProperty("user.dir") + "\\assets\\clashofclans.jpg").toURI().toString());
    static {
        Button newGame = new Button("New Game");
        newGame.setOnAction(event -> {
            Village village = new Village();
            villageUI = new VillageUI();
            villageUI.buildTheScene(village);
            VillageNameUI.openSetNameStage(village);
            MainStage.getStage().setScene(villageUI.getScene());
        });

        Button loadGame = new Button("Load Game");
        loadGame.setOnAction(event -> MainStage.getStage().setScene(LoadGameScene.getScene()));

        Button exit = new Button("Exit");
        exit.setOnAction(e -> MainStage.getStage().close());


        VBox vBox = new VBox();
        vBox.getChildren().addAll(newGame, loadGame, exit);
        StackPane stackPane = new StackPane();
        ImageView imageView = BACKGROUND;
        imageView.setFitHeight(GraphicConstants.STAGE_HEIGHT);
        imageView.setFitWidth(GraphicConstants.STAGE_WIDTH);
        stackPane.getChildren().add(imageView);
        stackPane.getChildren().add(vBox);
        vBox.alignmentProperty().set(Pos.CENTER);
        scene = new Scene(stackPane , GraphicConstants.STAGE_WIDTH, GraphicConstants.STAGE_HEIGHT);
//        File f = new File("Viper.css");
//        scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
    }

    public static Scene getScene() {
        return scene;
    }

    public static VillageUI getVillageUI() {
        return villageUI;
    }

    public static void setScene(Scene scene) {
        MainMenuUI.scene = scene;
    }
}
