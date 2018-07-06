package graphic;

import controllers.VillageController;
import enums.SoldierType;
import exceptions.CoordinateOutOfBondException;
import exceptions.DontHaveFreeWorkerException;
import exceptions.NotEnoughLevelException;
import exceptions.NotEnoughResourceException;
import graphic.VillageUi.VillageUI;
import graphic.mainMenuUI.MainMenuUI;
import javafx.scene.CacheHint;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Coordinate;
import models.Worker;
import models.building.Building;
import models.building.defence.*;
import models.building.village.*;
import models.interfaces.Upgradeable;
import models.interfaces.VisibleEntity;
import models.livingBeing.Attack.*;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class ObjToPic {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;
    private static ObjToPic objToPic = new ObjToPic();
    private static Stage secondStage = new Stage();
    private VillageController villageController;
    private ObjToPic instance = null;


    // village buildings (not defence)
    private ArrayList<Image> townHall;
    private ArrayList<Image> goldStorage;
    private ArrayList<Image> elixirStorage;
    private ArrayList<Image> barracks;
    private ArrayList<Image> camp;
    private ArrayList<Image> goldMine;
    private ArrayList<Image> elixirMine;

    // defence buildings
    private ArrayList<Image> archerTower;
    private ArrayList<Image> airDefence;
    private ArrayList<Image> cannon;
    private ArrayList<Image> wizardTower;
    private ArrayList<Image> trap;
    private ArrayList<Image> wall;
    private ArrayList<Image> giantCastle; // TODO: 6/28/2018 dont have picture and not even made!

    // attackForces (for menus not for actual attackUI)
    private ArrayList<Image> guardianArtWork;
    private ArrayList<Image> archerArtWork;
    private ArrayList<Image> giantArtWork;
    private ArrayList<Image> dragonArtWork;
    private ArrayList<Image> wallBreakerArtWork;
    private ArrayList<Image> healerArtWork;
    private ArrayList<Image> wizardArtWork;

    // TODO: 6/28/2018 for attackUI -> @kimia


    private ObjToPic() {
    }

    //setting pictures of village buildings (not defence)
    {
        townHall = new ArrayList<>();
        townHall.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Town_Hall1.png").toURI().toString()));
        townHall.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Town_Hall3.png").toURI().toString()));
        townHall.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Town_Hall6.png").toURI().toString()));
        townHall.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Town_Hall7.png").toURI().toString()));
        townHall.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Town_Hall8.png").toURI().toString()));
        townHall.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Town_Hall9.png").toURI().toString()));
        townHall.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Town_Hall10.png").toURI().toString()));
        townHall.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Town_Hall11.png").toURI().toString()));

        goldStorage = new ArrayList<>();
        goldStorage.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Gold_Storage1B.png").toURI().toString()));
        goldStorage.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Gold_Storage2B.png").toURI().toString()));
        goldStorage.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Gold_Storage3B.png").toURI().toString()));
        goldStorage.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Gold_Storage5B.png").toURI().toString()));
        goldStorage.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Gold_Storage8B.png").toURI().toString()));

        elixirStorage = new ArrayList<>();
        elixirStorage.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Elixir_Storage1B.png").toURI().toString()));
        elixirStorage.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Elixir_Storage2B.png").toURI().toString()));
        elixirStorage.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Elixir_Storage3B.png").toURI().toString()));
        elixirStorage.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Elixir_Storage4B.png").toURI().toString()));
        elixirStorage.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Elixir_Storage7B.png").toURI().toString()));

        barracks = new ArrayList<>();
        barracks.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Builder_Barracks1.png").toURI().toString()));
        barracks.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Builder_Barracks5.png").toURI().toString()));
        barracks.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Builder_Barracks6.png").toURI().toString()));
        barracks.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Builder_Barracks7.png").toURI().toString()));
        barracks.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Builder_Barracks9.png").toURI().toString()));
        barracks.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Builder_Barracks10.png").toURI().toString()));

        camp = new ArrayList<>();
        camp.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Army_Camp2.png").toURI().toString()));
        camp.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Army_Camp3.png").toURI().toString()));
        camp.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Army_Camp4.png").toURI().toString()));
        camp.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Army_Camp6.png").toURI().toString()));
        camp.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Army_Camp7.png").toURI().toString()));
        camp.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Army_Camp8.png").toURI().toString()));
        camp.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Army_Camp9.png").toURI().toString()));

        goldMine = new ArrayList<>();
        goldMine.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Gold_Mine1.png").toURI().toString()));
        goldMine.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Gold_Mine4.png").toURI().toString()));
        goldMine.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Gold_Mine6.png").toURI().toString()));
        goldMine.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Gold_Mine8.png").toURI().toString()));

        elixirMine = new ArrayList<>();
        elixirMine.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Elixir_Collector1B.png").toURI().toString()));
        elixirMine.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Elixir_Collector3B.png").toURI().toString()));
        elixirMine.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Elixir_Collector5B.png").toURI().toString()));
        elixirMine.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Elixir_Collector6B.png").toURI().toString()));
        elixirMine.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Elixir_Collector8B.png").toURI().toString()));
    }

    //setting pictures of defence buildings
    {
        airDefence = new ArrayList<>();
        airDefence.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Air_Defense1.png").toURI().toString()));
        airDefence.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Air_Defense2.png").toURI().toString()));
        airDefence.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Air_Defense3.png").toURI().toString()));
        airDefence.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Air_Defense4.png").toURI().toString()));
        airDefence.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Air_Defense7.png").toURI().toString()));
        airDefence.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Air_Defense9.png").toURI().toString()));

        archerTower = new ArrayList<>();
        archerTower.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Archer_Tower1B.png").toURI().toString()));
        archerTower.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Archer_Tower3B.png").toURI().toString()));
        archerTower.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Archer_Tower5B.png").toURI().toString()));
        archerTower.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Archer_Tower7B.png").toURI().toString()));
        archerTower.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Archer_Tower8B.png").toURI().toString()));

        cannon = new ArrayList<>();
        cannon.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Cannon1B.png").toURI().toString()));
        cannon.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Cannon2B.png").toURI().toString()));
        cannon.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Cannon4B.png").toURI().toString()));
        cannon.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Cannon7B.png").toURI().toString()));
        cannon.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Cannon8B.png").toURI().toString()));

        wizardTower = new ArrayList<>();
        wizardTower.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Wizard_Tower3.png").toURI().toString()));
        wizardTower.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Wizard_Tower7.png").toURI().toString()));
        wizardTower.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Wizard_Tower7.png").toURI().toString()));
        wizardTower.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Wizard_Tower8.png").toURI().toString()));
        wizardTower.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Wizard_Tower10.png").toURI().toString()));

        trap = new ArrayList<>();
        trap.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Push_Trap1.png").toURI().toString()));
        trap.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Push_Trap3.png").toURI().toString()));
        trap.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Push_Trap6.png").toURI().toString()));
        trap.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Push_Trap8.png").toURI().toString()));

        wall = new ArrayList<>();
        wall.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Wall1B.png").toURI().toString()));
        wall.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Wall2B.png").toURI().toString()));
        wall.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Wall3B.png").toURI().toString()));
        wall.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Wall5B.png").toURI().toString()));
        wall.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Wall7B.png").toURI().toString()));
        wall.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\building and tower\\Wall8B.png").toURI().toString()));

        // TODO: 6/15/2018 to be complete giant castle
    }

    //setting pictures of artWorks
    {
        guardianArtWork = new ArrayList<>();
        guardianArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Barbarian1.png").toURI().toString()));
        guardianArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Barbarian3.png").toURI().toString()));
        guardianArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Barbarian5.png").toURI().toString()));
        guardianArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Barbarian6.png").toURI().toString()));
        guardianArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Barbarian7.png").toURI().toString()));

        archerArtWork = new ArrayList<>();
        archerArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\archer1.png").toURI().toString()));
        archerArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\archer3.png").toURI().toString()));
        archerArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\archer5.png").toURI().toString()));
        archerArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\archer6.png").toURI().toString()));

        giantArtWork = new ArrayList<>();
        giantArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Giant1.png").toURI().toString()));
        giantArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Giant3.png").toURI().toString()));
        giantArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Giant5.png").toURI().toString()));
        giantArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Giant6.png").toURI().toString()));

        dragonArtWork = new ArrayList<>();
        dragonArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Dragon1.png").toURI().toString()));
        dragonArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Dragon2.png").toURI().toString()));
        dragonArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Dragon3.png").toURI().toString()));
        dragonArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Dragon6.png").toURI().toString()));

        wallBreakerArtWork = new ArrayList<>();
        wallBreakerArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Wall_Breaker1.png").toURI().toString()));
        wallBreakerArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Wall_Breaker3.png").toURI().toString()));
        wallBreakerArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Wall_Breaker5.png").toURI().toString()));
        wallBreakerArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Wall_Breaker6.png").toURI().toString()));

        healerArtWork = new ArrayList<>();
        healerArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Healer1.png").toURI().toString()));
        healerArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Healer3.png").toURI().toString()));
        healerArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Healer5.png").toURI().toString()));
/*
        wizardTower = new ArrayList<>();
        wizardArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Wizard1.png").toURI().toString()));
        wizardArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Wizard5.png").toURI().toString()));
        wizardArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Wizard6.png").toURI().toString()));
        wizardArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Wizard7.png").toURI().toString()));
        wizardArtWork.add(new Image(new File(System.getProperty("user.dir") + "\\assets\\soldiers\\Wizard8.png").toURI().toString()));
*/

    }


    public ImageView getPic(Object object) {

       /* if (object.getClass().isInstance(Building.class)) {
            return makeImageViewOfBuilding(((Building) object));
    }
       */

        if (object.getClass() == TownHall.class) {
            ImageView temp = makeImageViewOfEntities(((Building) object), townHall);
            temp.setOnMouseClicked(event -> {
                secondStage.setTitle("TownHall menu");
                secondStage.setScene(getTownHallScene((TownHall) object));
                secondStage.show();
            });
            setHealthLisener(temp, (Building) object);
            return temp;
        } else if (object.getClass() == GoldStorage.class) {
            ImageView temp = makeImageViewOfEntities(((Building) object), goldStorage);
            temp.setOnMouseClicked(event -> {
                secondStage.setTitle("GoldStorage menu");
                secondStage.setScene(getStorageScene((Storage) object));
                secondStage.show();
            });
            setHealthLisener(temp, (Building) object);
            return temp;
        } else if (object.getClass() == ElixirStorage.class) {
            ImageView temp = makeImageViewOfEntities(((Building) object), elixirStorage);
            temp.setOnMouseClicked(event -> {
                secondStage.setTitle("Elixir Storage menu");
                secondStage.setScene(getStorageScene((ElixirStorage) object));
                secondStage.show();
            });
            setHealthLisener(temp, (Building) object);
            return temp;
        } else if (object.getClass() == Barracks.class) {
            ImageView temp = makeImageViewOfEntities(((Building) object), barracks);
            temp.setOnMouseClicked(event -> {
                secondStage.setTitle("Barracks menu");
                secondStage.setScene(getBarracksScene((Barracks) object));
                secondStage.show();
            });
            setHealthLisener(temp, (Building) object);
            return temp;
        } else if (object.getClass() == Camp.class) {
            ImageView temp = makeImageViewOfEntities(((Building) object), camp);
            temp.setOnMouseClicked(event -> {
                secondStage.setTitle("Camp menu");
                secondStage.setScene(getCampScene((Camp) object));
                secondStage.show();
            });
            setHealthLisener(temp, (Building) object);
            return temp;
        } else if (object.getClass() == GoldMine.class) {
            ImageView temp = makeImageViewOfEntities(((Building) object), goldMine);
            temp.setOnMouseClicked(event -> {
                secondStage.setTitle("mine menu");
                secondStage.setScene(getMineScene((Mine) object));
                secondStage.show();
            });
            setHealthLisener(temp, (Building) object);
            return temp;


        } else if (object.getClass() == ElixirMine.class) {
            ImageView temp = makeImageViewOfEntities(((Building) object), elixirMine);
            temp.setOnMouseClicked(event -> {
                secondStage.setTitle("mine menu");
                secondStage.setScene(getMineScene((Mine) object));
                secondStage.show();
            });
            setHealthLisener(temp, (Building) object);
            return temp;


        } else if (object instanceof ArcherTower) {
            ImageView temp = makeImageViewOfEntities(((Building) object), archerTower);
            temp.setOnMouseClicked(event -> {
                secondStage.setTitle("defence menu");
                secondStage.setScene(getDefenceScene((DefenceBuilding) object));
                secondStage.show();
            });
            setHealthLisener(temp, (Building) object);
            return temp;


        } else if (object.getClass() == AirDefence.class) {
            ImageView temp = makeImageViewOfEntities(((Building) object), airDefence);
            temp.setOnMouseClicked(event -> {
                secondStage.setTitle("defence menu");
                secondStage.setScene(getDefenceScene((DefenceBuilding) object));
                secondStage.show();
            });
            setHealthLisener(temp, (Building) object);
            return temp;


        } else if (object.getClass() == Cannon.class) {
            ImageView temp = makeImageViewOfEntities(((Building) object), cannon);
            temp.setOnMouseClicked(event -> {
                secondStage.setTitle("defence menu");
                secondStage.setScene(getDefenceScene((DefenceBuilding) object));
                secondStage.show();
            });
            setHealthLisener(temp, (Building) object);
            return temp;

        } else if (object.getClass() == GiantsCastle.class) {
            ImageView temp = makeImageViewOfEntities(((Building) object), giantCastle);
            temp.setOnMouseClicked(event -> {
                secondStage.setTitle("defence menu");
                secondStage.setScene(getDefenceScene((DefenceBuilding) object));
                secondStage.show();
            });
            setHealthLisener(temp, (Building) object);
            return temp;

        } else if (object.getClass() == Trap.class) {
            ImageView temp = makeImageViewOfEntities(((Building) object), trap);
            temp.setOnMouseClicked(event -> {
                secondStage.setTitle("defence menu");
                secondStage.setScene(getDefenceScene((DefenceBuilding) object));
                secondStage.show();
            });
            setHealthLisener(temp, (Building) object);
            return temp;

        } else if (object.getClass() == Wall.class) {
            ImageView temp = makeImageViewOfEntities(((Building) object), wall);
            temp.setOnMouseClicked(event -> {
                secondStage.setTitle("defence menu");
                secondStage.setScene(getDefenceScene((DefenceBuilding) object));
                secondStage.show();
            });
            setHealthLisener(temp, (Building) object);
            return temp;

        } else if (object.getClass() == WizardTower.class) {
            ImageView temp = makeImageViewOfEntities(((Building) object), goldMine);
            temp.setOnMouseClicked(event -> {
                secondStage.setTitle("defence menu");
                secondStage.setScene(getDefenceScene((DefenceBuilding) object));
                secondStage.show();
            });
            setHealthLisener(temp, (Building) object);
            return temp;
        }
        //todo soldiers
//        return makeImageViewOfEntities(((VisibleEntity) entity), temp); // TODO: 6/28/2018 put a fucking picture here for debugging!
        return null;
    }


    public Scene getDefenceScene(DefenceBuilding defenceBuilding) {
        Label levelLabel = new Label("level : " + defenceBuilding.getLevel());
        Button upgradeButton = new Button("upgrade");
        upgradeButton.setOnAction(event -> {
            secondStage.setScene(upgradeScene(defenceBuilding));
        });
        Button backButton = new Button("back");
        backButton.setOnAction(event -> {
            secondStage.close();
        });
        HBox buttons = new HBox();
        buttons.getChildren().addAll(backButton, upgradeButton);
        VBox vBox = new VBox(levelLabel, buttons);
        return new Scene(vBox, WIDTH, HEIGHT);
    }

    public ImageView getPicOfMovingEntities(Object entity) { //for attack forces
        // TODO: 6/28/2018 @kimia
        return null;
    }

    private ImageView makeImageViewOfEntities(VisibleEntity entity, ArrayList<Image> imagesOfEntity) {
        ImageView temp = new ImageView();
        if (entity.getLevel() > imagesOfEntity.size()) {
            temp.setImage(imagesOfEntity.get(imagesOfEntity.size() - 1));
        } else {
            temp.setImage(imagesOfEntity.get((entity.getLevel())));
        }
        temp.setScaleX(0.4);
        temp.setScaleY(0.4);
        temp.setX(((double) entity.getCoordinate().getX()) / 30 * GraphicConstants.MAP_WIDTH);
        temp.setY(((double) entity.getCoordinate().getY()) / 30 * GraphicConstants.MAP_HEIGHT);
        return temp;
    }

    private static ImageView makeImageView(VisibleEntity entity, ArrayList<Image> imagesOfEntity) {
        ImageView temp = new ImageView();
        if (entity.getLevel() > imagesOfEntity.size()) {
            temp.setImage(imagesOfEntity.get(imagesOfEntity.size() - 1));
        } else {
            temp.setImage(imagesOfEntity.get((entity.getLevel())));
        }
        temp.setScaleX(.5);
        temp.setScaleY(.5);
        return temp;
    }

    public Scene getMineScene(Mine mine) {
        Label levelLabel = new Label("level : " + mine.getLevel());
        Label goldLabel = new Label("resource : " + mine.getStorage());
        Button upgradeButton = new Button("upgrade");
        upgradeButton.setOnAction(event -> {
            secondStage.setScene(upgradeScene(mine));
        });
        Button backButton = new Button("back");
        backButton.setOnAction(event -> {
            secondStage.close();
        });
        Button mineButton = new Button("mine");
        mineButton.setOnAction(event -> {
            villageController.mine(mine);
        });
        HBox buttons = new HBox();
        buttons.getChildren().addAll(backButton, upgradeButton, mineButton);
        VBox vBox = new VBox(levelLabel, goldLabel, buttons);
        return new Scene(vBox, WIDTH, HEIGHT);
    }

    private Scene getStorageScene(Storage storage) {
        Label levelLabel = new Label("level : " + storage.getLevel());
        Label goldLabel = new Label("resource : " + storage.getResource().getGold());
        Label elixirLabel = new Label("resource : " + storage.getResource().getElixir());
        Label capacityLabel = new Label("capacity : " + storage.getCapacity());
        Button upgradeButton = new Button("upgrade");
        upgradeButton.setOnAction(event -> {
            secondStage.setScene(upgradeScene(storage));
        });
        Button backButton = new Button("back");
        backButton.setOnAction(event -> {
            secondStage.close();
        });
        HBox buttons = new HBox();
        buttons.getChildren().addAll(backButton, upgradeButton);
        VBox vBox = new VBox(levelLabel, goldLabel, elixirLabel, capacityLabel, buttons);
        return new Scene(vBox, WIDTH, HEIGHT);
    }

    private Scene getCampScene(Camp camp) {
        Label levelLabel = new Label("level : " + camp.getLevel());
        Label capacityLabel = new Label("capacity : " + camp.getCapacity());
        Button soldiersButton = new Button("soldiers");//todo
        Button backButton = new Button("back");
        backButton.setOnAction(event -> {
            secondStage.close();
        });
        HBox buttons = new HBox();
        buttons.getChildren().addAll(backButton, soldiersButton);
        VBox vBox = new VBox(levelLabel, capacityLabel, buttons);
        return new Scene(vBox, WIDTH, HEIGHT);
    }

    private Scene getBarracksScene(Barracks barracks) {
        Label levelLabel = new Label("level : " + barracks.getLevel());
        Button upgradeButton = new Button("upgrade");
        upgradeButton.setOnAction(event -> {
            secondStage.setScene(upgradeScene(barracks));
        });
        Button buildButton = new Button("build");
        buildButton.setOnAction(event -> {
            //todo
            secondStage.setScene(buildSoldierScene(barracks));
            secondStage.show();
        });
        Button backButton = new Button("back");
        backButton.setOnAction(event -> {
            secondStage.close();
        });
        HBox buttons = new HBox();
        buttons.getChildren().addAll(backButton, upgradeButton, buildButton);
        VBox vBox = new VBox(levelLabel, buttons);
        return new Scene(vBox, WIDTH, HEIGHT);
    }

    private Pane getBuildPane() {
        MainMenuUI.getVillageUI().setImages();
        GridPane pane = new GridPane();
        int count = 0;
        HashMap<Integer, Building> availableBuildingsList = villageController.availableBuildingsList();
        ArrayList<ImageView> imageViews = new ArrayList<>();
        for (Integer i :
                availableBuildingsList.keySet()) {
            Object object = availableBuildingsList.get(i);
            ImageView temp = getImageViewForBuilding(object);
            if (temp != null) {
                addImageLisenerTo((Building) object, temp);
                pane.add(temp, (i - 1) % 4, (i - 1) / 4);
            }
        }
        pane.setHgap(.25);
        pane.setVgap(.25);
        return pane;
    }

    private void addImageLisenerTo(Building object, ImageView temp) {
        ImageView finalTemp = temp;
        temp.setOnMouseClicked((MouseEvent event) -> {
            finalTemp.setOnMouseDragged((MouseEvent e) -> {
                finalTemp.setLayoutX(e.getSceneX()-30);
                finalTemp.setLayoutY(e.getSceneY() -30);
                finalTemp.setOnMouseReleased((eevent -> {
                    try {//todo make x , y
                        villageController.build(object, new Coordinate((int) (finalTemp.getLayoutX() + 10) / 30 , (int) (finalTemp.getLayoutY() + 50) / 30));
                    } catch (DontHaveFreeWorkerException ee) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "You don't have enough workers", ButtonType.CLOSE);
                        alert.setTitle("Error Dialog");
                        alert.showAndWait();
                    } catch (CoordinateOutOfBondException e1) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "this cell is full", ButtonType.CLOSE);
                        alert.setTitle("Error Dialog");
                        alert.showAndWait();
                    }
                    VillageUI.zoom(MainMenuUI.getVillageUI().getPane());
                    finalTemp.setOnMouseDragged(null);
                    finalTemp.setOnMouseReleased(null);
                    finalTemp.setVisible(false);
                    MainMenuUI.getVillageUI().setImages();
                }));
            });
            finalTemp.setLayoutX(0);
            finalTemp.setLayoutY(0);
            MainMenuUI.getVillageUI().getPane().getChildren().add(finalTemp);
            MainStage.getStage().setScene(MainMenuUI.getVillageUI().getScene());
            finalTemp.setOnMouseClicked(null);
            MainMenuUI.getVillageUI().getPane().setOnMouseDragged(null);
            secondStage.close();
        });
    }

    private ImageView getImageViewForBuilding(Object object) {
        ImageView temp = null;
        if (object.getClass() == TownHall.class) {
            temp = makeImageView(((Building) object), townHall);
        } else if (object.getClass() == GoldStorage.class) {
            temp = makeImageView(((Building) object), goldStorage);
        } else if (object.getClass() == ElixirStorage.class) {
            temp = makeImageView(((Building) object), elixirStorage);
        } else if (object.getClass() == Barracks.class) {
            temp = makeImageView(((Building) object), barracks);
        } else if (object.getClass() == Camp.class) {
            temp = makeImageView(((Building) object), camp);
        } else if (object.getClass() == GoldMine.class) {
            temp = makeImageView(((Building) object), goldMine);

        } else if (object.getClass() == ElixirMine.class) {
            temp = makeImageView(((Building) object), elixirMine);

        } else if (object instanceof ArcherTower) {
            temp = makeImageView(((Building) object), archerTower);

        } else if (object.getClass() == AirDefence.class) {
            temp = makeImageView(((Building) object), airDefence);

        } else if (object.getClass() == Cannon.class) {
            temp = makeImageView(((Building) object), cannon);

        } else if (object.getClass() == GiantsCastle.class) {
            temp = makeImageView(((Building) object), giantCastle);

        } else if (object.getClass() == Trap.class) {
            temp = makeImageView(((Building) object), trap);

        } else if (object.getClass() == Wall.class) {
            temp = makeImageView(((Building) object), wall);

        } else if (object.getClass() == WizardTower.class) {
//                temp = makeImageViewOfEntities(((Building) object), wizardTower);
        }
        return temp;
    }

    private Scene getUncompleteScene() {
        ArrayList<Building> incompleteBuildings = villageController.townHallStatus();
        Text text = new Text("\n\n incomplete buildings:\n");
        for (Building b :
                incompleteBuildings) {
            text.setText(text.getText() + b.getClass().getSimpleName() + " time remained: " + b.getTimeToComplete() + "\n");
        }
        Pane pane = new Pane();
        pane.getChildren().add(text);
        return new Scene(pane, WIDTH, HEIGHT);
    }

    private Scene getWorkersScene() {
        Text text = new Text("\n\n number of workers:"+villageController.numberOfWorkers() +"\n");
        int i = 0;
        for (Worker worker :
                villageController.getvillage().getWorkers()) {
            i++;
            if(worker.isFree()) {
                text.setText(text.getText() + "worker " + i + "is working on nothing. \n" );
            }else {
                text.setText(text.getText() + "worker " + i + "is working on" + worker.getBuilding().getClass().getSimpleName() + " and  " + worker.getTimeToComplete() + " left to complete\n");
            }

        }
        Button button = new Button("add new worker");
        button.setOnAction(event -> {
            try {
                villageController.getvillage().addNewWorker();
            } catch (NotEnoughLevelException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR , "you don't have enough level" , ButtonType.CLOSE);
                alert.showAndWait();
            }
        });
        HBox pane = new HBox();
        pane.getChildren().add(text);
        pane.getChildren().add(button);
        return new Scene(pane, WIDTH, HEIGHT);
    }

    private Scene getTownHallScene(TownHall townHall) {
        Label levelLabel = new Label("level : " + townHall.getLevel());
        Button upgradeButton = new Button("upgrade");
        upgradeButton.setOnAction(event -> {
            secondStage.setScene(upgradeScene(townHall));
        });
        Button uncompleteButton = new Button("uncomplete");
        uncompleteButton.setOnAction(event -> {
            secondStage.setScene(getUncompleteScene());
        });

        Button worker = new Button("workers");
        worker.setOnAction(event -> {
            secondStage.setScene(getWorkersScene());
        });

        Button buildButton = new Button("build");
        buildButton.setOnAction(event -> {
//            MainMenuUI.getVillageUI().getPane().getChildren().add(getBuildPane());
//            MainStage.getStage().setScene(MainMenuUI.getVillageUI().getScene());//todo make drag and drop
            secondStage.setScene(new Scene(getBuildPane(), WIDTH, HEIGHT));
            secondStage.show();
            MainStage.getStage().show();
        });
        Button backButton = new Button("back");
        backButton.setOnAction(event -> {
            secondStage.close();
        });
        HBox buttons = new HBox();
        buttons.getChildren().addAll(backButton, upgradeButton, buildButton, uncompleteButton , worker);
        VBox vBox = new VBox(levelLabel, buttons);
        return new Scene(vBox, WIDTH, HEIGHT);
    }

    private void setHealthLisener(ImageView imageView, Building building) {
        final Rectangle[] rectangle = {new Rectangle((int) imageView.getX() + 20, (int) imageView.getY() + 5, (building.getStrength() / 1000) * 20, 5)};
        final Rectangle[] rectangle2 = {new Rectangle((int) imageView.getX() + 20, (int) imageView.getY() + 5, (building.getStrength() / 1000) * 20, 4)};
        imageView.setOnMouseEntered(event -> {
            rectangle[0] = new Rectangle((int) imageView.getX() + 20, (int) imageView.getY() + 5, (building.getStrength() / 100) * 20, 5);
            rectangle[0].setFill(Color.BLACK);
            rectangle2[0] = new Rectangle((int) imageView.getX() + 20, (int) imageView.getY() + 5, (building.getStrength() / 100) * 20, 4);
            rectangle2[0].setFill(Color.RED);
            MainMenuUI.getVillageUI().getPane().getChildren().addAll(rectangle[0], rectangle2[0]);
        });
        imageView.setOnMouseExited(event -> {
            MainMenuUI.getVillageUI().getPane().getChildren().removeAll(rectangle[0], rectangle2[0]);
        });
    }

    private Scene upgradeScene(Upgradeable upgradeable) {
        Text text = new Text("Do you want to upgrade " + upgradeable.getClass().getSimpleName() + " ?");
        Building building = (Building) upgradeable;
        Text resourceText = new Text(building.getResourceNeedToUpgrade().getGold() + " gold and " + building.getResourceNeedToUpgrade().getElixir() + " elixir nead to uppgrade");
        Button upgrade = new Button("upgrade");
        Label logLabel = new Label("");
        upgrade.setOnAction(event -> {
            //todo check
            if (villageController.canUpgrade((Building) upgradeable)) {
                try {
                    villageController.getvillage().spendResources(building.getResourceNeedToUpgrade());
                } catch (NotEnoughResourceException e) {
                    //this is not happend because of if
                    return;
                }
                villageController.getvillage().upgrade((Upgradeable) building);
                MainMenuUI.getVillageUI().setImages();
                logLabel.setText(building.getClass().getSimpleName() + " is upgrade to " + (building.getLevel() + 1));
            } else {
                logLabel.setText("can not upgrade this buildings");
            }
        });
        Button cancel = new Button("cancel");
        cancel.setOnAction(event -> {
            secondStage.close();
        });
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        hBox.getChildren().addAll(upgrade, cancel);
        vBox.getChildren().addAll(text, resourceText, hBox, logLabel);
        return new Scene(vBox, WIDTH, HEIGHT);
    }

    public static ObjToPic getInstance(VillageController villageController) {
        objToPic.villageController = villageController;
        return objToPic;
    }

    private Scene buildSoldierScene(Barracks barracks) {
        HashMap<ImageView, SoldierType> soldiersImage = getSoldiersImage(barracks);
        ComboBox<ImageView> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(soldiersImage.keySet());
        HBox hBox = new HBox();
        hBox.getChildren().addAll(comboBox);
        TextField number = new TextField();
        number.setPromptText("how many soldier?");
        hBox.getChildren().addAll(number);
        return new Scene(hBox, WIDTH, HEIGHT);
    }

    private HashMap<ImageView, SoldierType> getSoldiersImage(Barracks barracks) {
        HashMap<Integer, SoldierType> integerSoldierTypeHashMap = villageController.listOfSoldiersToBuild();
        HashMap<ImageView, SoldierType> soldiers = new HashMap<>();
        for (SoldierType type :
                integerSoldierTypeHashMap.values()) {
            ImageView temp = new ImageView();
            switch (type) {
                case DRAGON:
                    temp = makeImageView(new Dragon(barracks.getLevel()), dragonArtWork);
                    temp.setScaleX(0.5);
                    temp.setScaleY(0.5);
                    soldiers.put(temp, SoldierType.DRAGON);
                    break;
                case HEALER:
                    temp = makeImageView(new Healer(barracks.getLevel()), healerArtWork);
                    temp.setScaleX(0.5);
                    temp.setScaleY(0.5);
                    soldiers.put(temp, SoldierType.HEALER);
                    break;
                case GIANT:
                    temp = makeImageView(new Giant(barracks.getLevel()), giantArtWork);
                    temp.setScaleX(0.5);
                    temp.setScaleY(0.5);
                    soldiers.put(temp, SoldierType.GIANT);
                    break;
                case GAURDIAN:
                    temp = makeImageView(new Gaurdian(barracks.getLevel()), guardianArtWork);
                    temp.setScaleX(0.5);
                    temp.setScaleY(0.5);
                    soldiers.put(temp, SoldierType.GAURDIAN);
                    break;
                case WALLBREAKER:
                    temp = makeImageView(new WallBreaker(barracks.getLevel()), wallBreakerArtWork);
                    temp.setScaleX(0.5);
                    temp.setScaleY(0.5);
                    soldiers.put(temp, SoldierType.WALLBREAKER);
                    break;
                case ARCHER:
                    temp = makeImageView(new Archer(barracks.getLevel()), archerArtWork);
                    temp.setScaleX(0.5);
                    temp.setScaleY(0.5);
                    soldiers.put(temp, SoldierType.ARCHER);
                    break;

            }

        }
        return soldiers;
    }
}
