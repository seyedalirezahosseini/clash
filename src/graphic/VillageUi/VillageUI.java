package graphic.VillageUi;

import com.sun.tools.javac.Main;
import controllers.VillageController;
import graphic.GraphicConstants;
import graphic.ObjToPic;
import graphic.mainMenuUI.MainMenuUI;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.Village;
import models.building.Building;
import models.building.village.*;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;

public class VillageUI {

    private static Scene scene;
    Village village;

    private static double startX;
    private static double startY;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    Pane pane; //CONTAINS scrollable(zoomable) map, buttons and other stuff
    Pane zoomablePain;
    ScrollPane scrollPane;  //zoomable stuff is in here
    // TODO: 6/15/2018 we can also use stackpane to stack every thing in the order that we want

    private ArrayList<ImageView> buildingsImages = new ArrayList<>();

    private Timeline mainTimeline;
    private Timeline massageBoxesTimeline = new Timeline();


    /*why we don't use an arrayList of images : The ImageView is a Node used for painting images loaded with Image class.
    This class allows resizing the displayed image (with or without preserving the original aspect ratio) and specifying a viewport into the source image for restricting the pixels displayed by this ImageView.
*/

    public Pane getPane() {
        return pane;
    }

    public Pane getZoomablePain() {
        return zoomablePain;
    }

    public void buildTheScene(Village village) {
        this.village = village;
        zoomablePain = new Pane();
//        scrollPane.setPrefSize(GraphicConstants.MAP_WIDTH/2,GraphicConstants.MAP_HEIGHT/1.3);
        zoomablePain.setStyle("-fx-background-color: green");
        pane =zoomablePain;
        this.zoom(zoomablePain);
        setImages();
        StackPane stackPane = new StackPane();
        ImageView imageView = MainMenuUI.BACKGROUND;
        imageView.setFitHeight(GraphicConstants.MAP_HEIGHT);
        imageView.setFitWidth(GraphicConstants.MAP_WIDTH);
        stackPane.getChildren().addAll(imageView);
        stackPane.getChildren().addAll(zoomablePain);
        scene = new Scene(stackPane, GraphicConstants.MAP_WIDTH, GraphicConstants.MAP_HEIGHT);
//        scene.getStylesheets().add(System.getProperty("user.dir") + "Viper.css");
    }

    public Scene getScene() {
        return scene;
    }


    public void setImages() {
        zoomablePain.getChildren().add(new ImageView(new File(System.getProperty("user.dir") + "\\assets\\maap.png").toURI().toString() ));
        //this method makes image for each asset in map

        for (ImageView buildingImage : buildingsImages) {
            zoomablePain.getChildren().remove(buildingImage);
        }

        buildingsImages = new ArrayList<>();

        //setting buildings images
        for (Building building : village.getBuildings()) {
            buildingsImages.add(ObjToPic.getInstance(new VillageController(village)).getPic(building));
        }

/*
        //test:
        File file = new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Air_Defense1.png");
        Image image = new Image(file.toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setX(GraphicConstants.MAP_WIDTH / 2);
        imageView.setY(GraphicConstants.MAP_HEIGHT / 2);
        buildingsImages.add(imageView);
        //test ends!
*/

        for (ImageView buildingImage : buildingsImages) {
            zoomablePain.getChildren().add(buildingImage);
        }
        addGoldAndElixirToPane();

    }

    private void addGoldAndElixirToPane() {
        ImageView goldImage = new ImageView(new Image(new File(  "assets/kisspng-money-gold-coin-icon-money-coins-5a8e30bcc3cc44.931351751519268028802.png").toURI().toString()));
        goldImage.setX(200);
        goldImage.setScaleX(0.5);
        goldImage.setScaleY(0.5);
        goldImage.setY(-10);
        ImageView elixirImage = new ImageView(new Image(new File( "assets/elixir_big_icon.png").toURI().toString()));
        elixirImage.setScaleX(0.5);
        elixirImage.setScaleY(0.5);
        elixirImage.setY(-10);
        zoomablePain.getChildren().addAll(goldImage, elixirImage);
        Text gold = new Text(" " + village.getGoldResourceInStorages());
        gold.setX(300);
        gold.setY(30);
        gold.setFill(Color.WHITE);
        gold.setStyle("-fx-font-size: 24px ; -fx-color-label-visible: #ffffff");
        Text elixir = new Text(" " + village.getElixirResourceInStorages());
        elixir.setFill(Color.WHITE);
        elixir.setStyle("-fx-font-size: 24px ;  -fx-color-label-visible: #ffffff");
        elixir.setX(80);
        elixir.setY(30);
        zoomablePain.getChildren().addAll(gold , elixir);
    }

    public static void zoom(Pane pane) {
        pane.setOnScroll(
                new EventHandler<ScrollEvent>() {
                    @Override
                    public void handle(ScrollEvent event) {
                        double zoomFactor = 1.1;
                        double deltaY = event.getDeltaY();

                        if (deltaY < 0) {
                            zoomFactor = 0.9;
                        }
                        pane.setScaleX(pane.getScaleX() * zoomFactor);
                        pane.setScaleY(pane.getScaleY() * zoomFactor);
                        event.consume();
                    }
                });

        pane.setOnMouseDragged(event -> {
            scene.setCursor(Cursor.MOVE);
//            if (pane.getTranslateX() > 0) {
//                pane.setTranslateX(0);
//                pane.setTranslateY(pane.getTranslateY() - (startY - event.getSceneY())/pane.getScaleY()/30);
//                return;
//            }
//            if (pane.getTranslateY() > 0) {
//                pane.setTranslateX(pane.getTranslateX() - (startX - event.getSceneX())/pane.getScaleX()/30);
//                pane.setTranslateY(0);
//                return;
//            }
            pane.setTranslateX(pane.getTranslateX() - (startX - event.getSceneX())/30);
            pane.setTranslateY(pane.getTranslateY() - (startY - event.getSceneY())/30);
        });
        pane.setOnMousePressed((MouseEvent event) -> {
            startX = event.getSceneX();
            startY = event.getSceneY();
        });
        pane.setOnMouseReleased(event -> {
            scene.setCursor(Cursor.DEFAULT);
        });
//        pane.setOnMouseDragged((MouseEvent event) -> {
//            double deltaX = event.getSceneX() - startX + pane.getLayoutX();
//            double deltaY = event.getSceneY() - startY + pane.getLayoutY();
//            pane.setLayoutX(deltaX);
//            pane.setLayoutY(deltaY);
//        });

    }

}
