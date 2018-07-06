package view;

import enums.SoldierType;
import models.Village;
import models.building.Building;
import models.building.defence.DefenceBuilding;
import models.building.village.GoldStorage;
import models.building.village.Storage;
import models.livingBeing.Attack.Soldier;

import java.util.ArrayList;
import java.util.HashMap;

public class VillageView {

    public void printBuilding(Building building) {
        System.out.println(building.toString());
    }

    public void invalidBuildingName() {
        System.out.println("invalid building name ");
    }

    public void printWhereAmI(Building building) {
        System.out.println("you are in the " + building.getClass().getSimpleName() + building.getID());
    }

    public void printWhereAmI(Village building) {
        System.out.println("you are in the " + building.getClass().getSimpleName());
    }

    public void printWhereAmI(String s) {
        System.out.println("you are in the" + s);
    }

    public void printCampMenu() {
        System.out.println("1. Info");
        System.out.println("2. Soldiers");
        System.out.println("3. back");
    }

    public void printStorageMenu() {
        System.out.println("1. Info");
        System.out.println("2. back");
    }

    public void printBarrackMenu() {
        System.out.println("1. Info");
        System.out.println("2. Build soldiers");
        System.out.println("3. Status");
        System.out.println("4. back");
    }

    public void printVillageMenu() {
        System.out.println("1. Info");
        System.out.println("2. Available building");
        System.out.println("3. Status");
        System.out.println("4. back");
    }

    public void printMineMenu() {
        System.out.println("1. Info");
        System.out.println("2. Mine");
        System.out.println("3. back");
    }


    public void printAvailableBuilding(HashMap<Integer, Building> list) {
        for (Integer integer :
                list.keySet()) {
            System.out.println((integer.toString() + ". " + list.get(integer).getClass().getSimpleName()));
        }
    }

    public void printDontHaveWorkerError() {
        System.out.println("You don’t have any worker to build this building.");
    }

    public void printInvalidCoordinateError() {
        System.out.println("You can’t build this building here.Please choose another cell.");
    }

    public void printIncompleteBuildings(ArrayList<Building> buildings) {
        for (Building b :
                buildings) {
            System.out.println((b.getClass().getSimpleName() + " " + b.getTimeToComplete()));
        }
    }

    public void printStorageInfoMenu() {
        System.out.println("1. Overall info\n" +
                "2. Upgrade info\n" +
                "3. Sources info\n" +
                "4. Upgrade\n" +
                "5. Back");
    }

    public void campInfoMenu() {
        System.out.println("1. Overall info\n" +
                "2. Back");
    }

    public void upgradeableInfoMenu() {
        System.out.println("1. Overall info\n" +
                "2. Upgrade info\n" +
                "3. Back");
    }

    public void soldierWaitListPrint(ArrayList<Soldier> soldiers) {
        for (Soldier soldier :
                soldiers) {
            System.out.println((soldier.getType().toString() + " " + soldier.getTimeToBuild()));
        }
    }

    public void printAvailableSoldier(SoldierType e, int itemNumber, int num) {
        if (num > 0)
            System.out.println((itemNumber + "." + e.toString() + " " + "A" + " " + "x" + num));
        else
            System.out.println((itemNumber + "." + e.toString() + " " + "U"));
    }

    public void printErrorCantBuildSoldier() {
        System.out.println("You can’t build this soldier.");
    }

    public void printHowManySoldierWantToBuild() {
        System.out.println("How many of this soldier do you want to build?");
    }

    public void printDontHaveEnoughResource() {
        System.out.println("You don’t have enough resources.");
    }

    public void printOveralInfo(Building building) {
        System.out.println("VisibleEntity: " + building.getLevel());
        System.out.println("Health: " + building.getStrength());

    }

    public void printUpgradeInfo(Building building) {
        System.out.println("Upgrade Cost: " + building.getResourceNeedToUpgrade().getElixir() + "-Elixir " + building.getResourceNeedToUpgrade().getGold() + "-Gold");
    }

    public void printSourceInfo(Storage storage) {
        if (storage.getClass() == GoldStorage.class) {
            System.out.println("Your gold storage is " + storage.getResource().getGold() + "/" + storage.getCapacity() + "loaded.");
        } else {
            System.out.println("Your elixir storage is " + storage.getResource().getElixir() + "/" + storage.getCapacity() + "loaded.");
        }
    }

    public void printDefenceInfoMenu(Building building) {
        System.out.println("-- " + building.getClass().getSimpleName() + " " + building.getID() + " (Info) --");
        System.out.println("1. Overall info");
        System.out.println("2. Upgrade info");
        System.out.println("3. Attack info");
        System.out.println("4. Back");
        System.out.println("-----------------------");
        System.out.println("Command");
    }

    public void printAttackInfo(DefenceBuilding defenceBuilding) {
        System.out.println("Target: " + defenceBuilding.getSoldiersMoveType().toString());
        System.out.println("Damage: " + defenceBuilding.getDamage());
        System.out.println("Damage Range: " + defenceBuilding.getAttackRadius());

    }

    public void printSoldiers(HashMap<SoldierType, Integer> list) {
        for (SoldierType type :
                list.keySet()) {
            System.out.println(type.toString() + " x" + list.get(type));
        }
    }

    public void printBuildCost(Building building) {
        System.out.println("Do you want to build " + building.getClass().getSimpleName() + "for " + building.getResourceNeedToBuild().getGold() + "-Gold  " +
                building.getResourceNeedToBuild().getElixir() + "-Elixir ? [Y/N]");
    }

    public void printWhereBuildMassage(Building building) {
        System.out.println("Where do you want to build " + building.getClass().getSimpleName() + "? (x,y)");
    }

    public void printAvailableCells(Village village) {
        MapView mapView = new MapView();
        mapView.printAvailablePlaces(village);
    }

    public void printResource(Village village) {
        System.out.println("Gold: " + village.getGoldResourceInStorages());
        System.out.println("Elixir: " + village.getElixirResourceInStorages());
        System.out.println("Score: " + village.getScore());
    }

    public void menu() {
        System.out.println("--- -- Game Menu -- ---");
        System.out.println("1.ShowBuildings");
        System.out.println("2.Resources");
        System.out.println("3.Attack Mode");
        System.out.println("4.Back");
        System.out.println("-----------------------");
        System.out.println("Command:");
    }

    public void buildingAdded(Building building) {
        System.out.println(building.getExactType() + " " + building.getID() + " added successfully to (" + building.getCoordinate().getX() + "," + building.getCoordinate().getY() + ")");
    }

    public void printDefenceMenu(Building building) {

        System.out.println("--- - " + building.getClass().getSimpleName() + " " + building.getID() + " - ---");
        System.out.println("1.Info");
        System.out.println("2.Target");
        System.out.println("3.back");
        System.out.println("-----------------------");
        System.out.println("command:");
    }

    public void printUpgradeSuccessfulMassage() {
        System.out.println("buildings is upgrading");
    }

    public void printUpgradeQuestion() {
        System.out.println("upgrade?");
    }

    public void printErrorInUpgrade() {
        System.out.println("can't upgrade this buildings");
    }

    public void showTarget(DefenceBuilding building) {
        if (building.getTarget() == null) {
            System.out.println("There is no Target under attack \n(or you are not in attack mode");
        } else {
            System.out.println("Target: " + building.getTarget().getType().toString());
            System.out.println("Coordinate: (" + building.getTarget().getCoordinate().getX() + "," + building.getTarget().getCoordinate().getY() + ")");
            System.out.println("health: " + building.getTarget().getLeftHealth() + " of " + building.getTarget().getFullHealth() + "(Dammage = " + building.getTarget().getDamage() + ")");
        }
    }

    public void printBuildingsList(ArrayList<Building> buildings) {
        System.out.println("--- Show Buildings  ---");
        for (Building building : buildings) {
            if (building.isCompleted()) {
                printBuilding(building);
            }
        }
        System.out.println("-----------------------");
        System.out.println("Command:");
    }

    public void invalidCommand() {
        System.out.println("::::Invalid Command::::");
    }

    public void printLoadNewMap() {
        System.out.println("Enter map path: ");
    }

    public void JsonFileCorrupted() {
        System.out.println("Json File Is Corrupted");
    }
}
