package view;

import enums.BuildingType;
import enums.SoldierType;
import models.Loot;
import models.Resource;
import models.building.Building;
import models.livingBeing.Attack.Soldier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AttackView {
    public void printAttackMenu() {
        System.out.println("----------Attack Menu-----");
        System.out.println("1. load map");
    }

    public void showRestOfMaps(ArrayList<String> fileNames, int startPoint) {
        for (int i = 0; i < fileNames.size(); i++) {
            String[] file = fileNames.get(i).split("\\\\");
            System.out.println((startPoint + i) + ". " + file[file.length - 1]);
        }
        System.out.println((fileNames.size() + startPoint) + ". back");
        System.out.println("--------------------------");
    }

    public void showLoadMap() {
        System.out.println("Enter map path:");
    }

    public void showUnavailableCell() {
        System.out.println("You can't put any soldiers here.");
    }

    public void invalidFile() {
        System.out.println("There is no valid file in this location.");
    }

    public void showLoadedMapMenu() {
        System.out.println("1. Map info");
        System.out.println("2. Attack map");
        System.out.println("3. Back");
    }

    public void inLoadMap() {
        System.out.println("in load map menu");
    }

    public void inAttackMenu() {
        System.out.println("in attack menu");
    }

    public void showMapInfo(Resource mapResource, ArrayList<Building> buildings) {
        System.out.println("Gold: " + mapResource.getGold());
        System.out.println("Elixir: " + mapResource.getElixir());
        HashMap<BuildingType, Integer> buildingNumbers = new HashMap<>();
        for (Building building :
                buildings) {
            BuildingType type = building.getExactType();
            if (buildingNumbers.containsKey(type)) {
                buildingNumbers.replace(type, buildingNumbers.get(type), buildingNumbers.get(type) + 1);
            } else {
                buildingNumbers.put(type, 1);
            }
        }
        for (BuildingType type :
                buildingNumbers.keySet()) {
            System.out.println(type + ": " + buildingNumbers.get(type));
        }
    }

    public void showUnavailableSoldiers(HashMap<SoldierType, Integer> leftSoldiers) {
        for (SoldierType type :
                leftSoldiers.keySet()) {
            if (type == null || leftSoldiers.get(type) == 0)
                continue;
            System.out.println("Not enough " + type + " in camps");
        }
    }

    public void showSoldierStatus(ArrayList<Soldier> soldiers, SoldierType type) {

        System.out.println("----------soldiers------");
        for (Soldier soldier :
                soldiers) {
            if ((type == null || soldier.getType() == type) && soldier.getLeftHealth() > 0) {
                System.out.println(soldier.getType() + " level=" + soldier.getLevel()
                        + " in (" + soldier.getCoordinate().getX() + "," + soldier.getCoordinate().getY()
                        + ") with health=" + soldier.getLeftHealth());
            }
        }
        System.out.println("------------------------");
    }

    public void inSoldiersInitializing() {
        System.out.println("in soldiers initializing menu");
    }

    public void inLoadSaveMap() {
        System.out.println("in loading saved map menu");
    }

    public void showBuildingStatus(ArrayList<Building> buildings, BuildingType type) {
        System.out.println("---------Buildings------");
        for (Building building :
                buildings) {
            if ((type == null || building.getExactType() == type) && building.getStrength() > 0) {
                System.out.println(building.getExactType() + " level=" + building.getLevel()
                        + " in (" + building.getCoordinate().getX() + "," + building.getCoordinate().getY()
                        + ") with health=" + building.getStrength());
            }
        }
        System.out.println("------------------------");
    }

    public void showResourceStatus(Loot loot, Resource left) {
        System.out.println("------------Resources----------");
        System.out.println("gold achieved: " + loot.getGold());
        System.out.println("Elixir achieved: " + loot.getElixir());
        System.out.println("gold remained in map: " + left.getGold());
        System.out.println("elixir remained in map: " + left.getElixir());
        System.out.println("-------------------------------");
    }

    public void warEnded(Loot loot) {
        System.out.println("The war ended with " + loot.getGold() + " golds, " + loot.getElixir()
                + " elixirs and " + loot.getScore() + " scores achieved!");
    }

    public void printLoadedList(ArrayList<String> loadedGames) {
        System.out.println("----------Load Maps---");
        System.out.println("1.Load Map");
        for (int i = 0; i < loadedGames.size(); i++) {
            System.out.println((i + 2) + ". " + getNameFromPath(loadedGames.get(i)));
        }
        System.out.println(loadedGames.size() + 2 + ".Back");
        System.out.println("----------------------");
    }

    public String getNameFromPath(String path) {
        String[] last = path.split("/+");
        last = last[last.length - 1].split(".json");
        return last[0];
    }

    public void startSelect() {
        System.out.println("start select");
    }

}