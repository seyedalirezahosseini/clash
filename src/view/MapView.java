package view;

import enums.DictionaryIndexType;
import models.Coordinate;
import models.Dictionary;
import models.Village;
import models.building.Building;
import models.building.defence.*;
import models.building.village.*;

import java.util.HashMap;

public class MapView {
    private static int MAX_COORDINATES = Dictionary.getInstance().getConst(DictionaryIndexType.MAP_MAX_COORDINATE);
    private static HashMap<Coordinate, Building> buildingsHashMap;

    public void printAvailablePlaces(Village village) {
        buildingsHashMap = village.getBuidingsHashMap();

        for (int x = 1; x <= MAX_COORDINATES; x++) {
            for (int y = 1; y <= MAX_COORDINATES; y++) {
                if (buildingsHashMap.containsKey(new Coordinate(x, y))) {
                    System.out.print(" # ");
                } else {
                    System.out.print(" . ");
                }
            }
            System.out.println();
        }
    }

    public void printVillageMap(Village village) {
        buildingsHashMap = village.getBuidingsHashMap();
        for (int x = 1; x <= MAX_COORDINATES; x++) {
            for (int y = 1; y <= MAX_COORDINATES; y++) {
                if (buildingsHashMap.containsKey(new Coordinate(x, y))) {
                    System.out.format("%.16s ", getBuildingName(buildingsHashMap.get(new Coordinate(x, y))));
                } else {
                    System.out.print("\t\t");
                }
            }
            System.out.println();
        }
    }

    private String getBuildingName(Building building) {

        Class clazz;
        if (building.getClass() == GoldMine.class) {
            clazz = GoldMine.class;
        } else if (building.getClass() == ElixirMine.class) {
            clazz = ElixirMine.class;
        } else if (building.getClass() == GoldStorage.class) {
            clazz = GoldStorage.class;
        } else if (building.getClass() == ElixirStorage.class) {
            clazz = ElixirStorage.class;
        } else if (building.getClass() == TownHall.class) {
            clazz = TownHall.class;
        } else if (building.getClass() == Barracks.class) {
            clazz = Barracks.class;
        } else if (building.getClass() == Camp.class) {
            clazz = Camp.class;
        } else if (building.getClass() == ArcherTower.class) {
            clazz = ArcherTower.class;
        } else if (building.getClass() == Cannon.class) {
            clazz = Cannon.class;
        } else if (building.getClass() == AirDefence.class) {
            clazz = AirDefence.class;
        } else if (building.getClass() == WizardTower.class) {
            clazz = WizardTower.class;
        } else if (building.getClass() == Wall.class) {
            clazz = Wall.class;
        } else if (building.getClass() == Trap.class) {
            clazz = Trap.class;
        } else if (building.getClass() == GiantsCastle.class) {
            clazz = GiantsCastle.class;
        } else {
            return "No Such Builing (just fo test)";
        }
        return clazz.getName();


    }

    public void printAttackMap(Village village) {

    }

}