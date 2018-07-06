package controllers;

import com.gilecode.yagson.com.google.gson.*;
//import com.google.gson.Gson;
import exceptions.*;
import models.Coordinate;
import models.Resource;
import models.Village;
import models.building.defence.*;
import models.building.village.*;
import view.Files;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConverterEnemyJsonToVillage {
    private Gson gson = new Gson();
    private LoadedAttackVillage loadedVillage;

    private Village village = new Village("EnemyVillage");
    private ArrayList<models.building.Building> buildings = new ArrayList<>();
    private ArrayList<Storage> storages = new ArrayList<>();
    private ArrayList<Mine> mines = new ArrayList<>(); // TODO: 5/5/2018 to be used later in phase 2 , we don't put it in village now but later we should do it.
    private ArrayList<DefenceBuilding> defenceBuildings = new ArrayList<>();
    private HashMap<Coordinate, models.building.Building> buildingsHashMap = new HashMap<>();



    public Village getEnemyVillage(String jsonPatch) throws JsonFileIsCorruptException, IOException {
        LoadVillageFromJson(jsonPatch);
        try {
            ConvertLoadedVillageToOurVillage();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return village;
    }

    private void ConvertLoadedVillageToOurVillage() throws JsonFileIsCorruptException, NoSuchFieldException, IllegalAccessException {
        setBuildings();//if we do all of this methods in 1 or 2 method it would have really better performance
        setStorages();
        setMines();
        setDefenceBuildings();
//        setBuildingsHashMap();

        moveListsToVillage();
//        addResourcesToVillage();
    }

    private void setDefenceBuildings() {
        for (models.building.Building building : buildings) {
            if (building.getClass() == ArcherTower.class
                    || building.getClass() == AirDefence.class
                    || building.getClass() == WizardTower.class
                    || building.getClass() == Cannon.class
                    || building.getClass() == GiantsCastle.class
                    || building.getClass() == Trap.class) {
                defenceBuildings.add(((DefenceBuilding) building));
            }
        }
    }

    private void addResourcesToVillage() {
        Resource resource = new Resource(loadedVillage.resources.gold, loadedVillage.resources.elixir);
        try {
            village.addResources(resource);
        } catch (StoragesIsFullException e) {
            e.printStackTrace();
            //logically should not happen!
        }


    }

    private void moveListsToVillage() throws NoSuchFieldException, IllegalAccessException {
        //could be refactored
        Class clazz = Village.class;

        Field buildingsField = clazz.getDeclaredField("buildings");
        buildingsField.setAccessible(true);
        buildingsField.set(village, buildings);
        buildingsField.setAccessible(false);

        Field storagesField = clazz.getDeclaredField("storages");
        storagesField.setAccessible(true);
        storagesField.set(village, storages);
        storagesField.setAccessible(false);

        Field minesField = clazz.getDeclaredField("mines");
        minesField.setAccessible(true);
        minesField.set(village, mines);
        minesField.setAccessible(false);

//        Field buildingsHashMapField = clazz.getDeclaredField("buildingsHashMap");
//        buildingsHashMapField.setAccessible(true);
//        buildingsHashMapField.set(village, buildingsHashMap);
//        buildingsHashMapField.setAccessible(false);

        Field defenceBuildingsField = clazz.getDeclaredField("defenceBuildings");
        defenceBuildingsField.setAccessible(true);
        defenceBuildingsField.set(village, defenceBuildings);
        defenceBuildingsField.setAccessible(false);
    }

//    private void setBuildingsHashMap() {
//        for (models.building.Building building : buildings) {
//            buildingsHashMap.put(building.getCoordinate(), building);
//        }
//    }

    private void setMines() {
        for (models.building.Building building : buildings) {
            if (building.getClass() == GoldMine.class || building.getClass() == ElixirMine.class) {
                mines.add(((Mine) building));
            }
        }
    }

    private void setStorages() {
        for (models.building.Building building : buildings) {
            if (building.getClass() == GoldStorage.class || building.getClass() == ElixirStorage.class) {
                storages.add(((Storage) building));
            }
        }
    }

    private void LoadVillageFromJson(String patch) throws IOException {
        String json;
        json = Files.getJsonFromFile(patch);
        loadedVillage = gson.fromJson(json, LoadedAttackVillage.class);
    }

    private void setBuildings() throws JsonFileIsCorruptException {
        addBuildings();
    }

    private void addBuildings() throws JsonFileIsCorruptException {
        for (Building building : loadedVillage.buildings) {
            addBuilding(building);
        }
        for (Wall wall : loadedVillage.walls) {
            Building building = wallToBuilding(wall);
            addBuilding(building);
        }
    }

    private Building wallToBuilding(Wall wall) {
        Building building = new Building();
        // TODO: 5/5/2018 refactor consts
        building.type = 12;
        building.level = wall.level;
        building.x = wall.x;
        building.y = wall.y;
        return building;
    }

    private void addBuilding(Building loadedBuilding) throws JsonFileIsCorruptException {
        models.building.Building building = getBuilding(loadedBuilding);
        building = setAmounts(building, loadedBuilding);
        this.buildings.add(building);
    }

    private models.building.Building setAmounts(models.building.Building building, Building loadedBuilding) throws JsonFileIsCorruptException {
        try {
            building.setCoordinate(loadedBuilding.x, loadedBuilding.y);
            building.setLevel(loadedBuilding.level);
            for (int i = 0; i < loadedBuilding.level; i++) {
                building.upgrade();
            }
            if (building.getClass() == GoldStorage.class) {
                GoldStorage goldStorage = ((GoldStorage) building);
                try {
                    goldStorage.addResource(loadedBuilding.amount);
                } catch (NotEnoughCapacityException e) {
                    e.printStackTrace();
                } catch (InvalidValueException e) {
                    e.printStackTrace();
                }
            } else if (building.getClass() == ElixirStorage.class) {
                ElixirStorage elixirStorage = (ElixirStorage) building;
                try {
                    elixirStorage.addResource(loadedBuilding.amount);
                } catch (InvalidValueException e) {
                    e.printStackTrace();
                } catch (NotEnoughCapacityException e) {
                    e.printStackTrace();
                }
            }

            return building;
        } catch (CoordinateOutOfBondException e) {
            throw new JsonFileIsCorruptException("Coordinate is out of band");
        }
    }

    private models.building.Building getBuilding(Building building) throws JsonFileIsCorruptException {
        // TODO: 5/5/2018 refactor consts?!!
        switch (building.type) {
            case 1:
                return new GoldMine();
            case 2:
                return new ElixirMine();
            case 3:
                return new GoldStorage();
            case 4:
                return new ElixirStorage();
            case 5:
                return new TownHall();
            case 6:
                return new Barracks();
            case 7:
                return new Camp();
            case 8:
                return new ArcherTower();
            case 9:
                return new Cannon();
            case 10:
                return new AirDefence();
            case 11:
                return new WizardTower();
            case 12:
                return new models.building.defence.Wall();
            case 13:
                return new Trap();
            case 14:
                return new GiantsCastle();
            default:
                throw new JsonFileIsCorruptException("Building Type Does Not Match any of our types");
        }

    }

    class LoadedAttackVillage {
        public List<Integer> size = null; // TODO: 5/4/2018 what the helll is it?!
        public List<Wall> walls = null;
        public Resources resources;
        public List<Building> buildings = null;
    }

    class Building {
        public Integer type;
        public Integer level;
        public Integer x;
        public Integer y;
        public Integer amount;
    }

    class Wall {
        public Integer level;
        public Integer x;
        public Integer y;
    }

    class Resources {
        public Integer gold;
        public Integer elixir;
    }
}