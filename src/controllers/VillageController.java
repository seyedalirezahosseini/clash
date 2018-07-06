package controllers;

import enums.CommandType;
import enums.DictionaryIndexType;
import enums.SoldierType;
import exceptions.*;
import models.Coordinate;
import models.Dictionary;
import models.Resource;
import models.Village;
import models.building.Building;
import models.building.defence.*;
import models.building.village.*;
import models.interfaces.Upgradeable;
import models.livingBeing.Attack.Soldier;
import view.AttackView;
import view.Command;
import view.Files;
import view.VillageView;

import java.util.ArrayList;
import java.util.HashMap;

public class VillageController {
    private Village village;
    private static Command command = new Command();
    private VillageView view = new VillageView();

    public VillageController(Village village) {
        this.village = village;
    }

    public void menu() {
        try {
            while (true) {
                view.menu();
                updateCommand();
                if (command.getType() == CommandType.BACK) {
                    return;
                }
                if (command.getType() == CommandType.WHERE_AM_I) {
                    view.printWhereAmI(village);
                    continue;
                }
                if (command.getType() == CommandType.SHOW_MENU) {
                    view.menu();
                    continue;
                }
                switch (command.getItemNumber()) {
                    case 1:
                        showBuilding();
                        break;
                    case 2://resources
                        view.printResource(village);
                        break;
                    case 3://attackMode
//                        try {
//                        attackMode();
//                        } catch (JsonFileIsCorruptException | IOException e) {//todo
//                            e.printStackTrace();
//                        }
                        AttackController attackController = new AttackController(village);
                        attackController.attackMenu();
                        break;
                    case 4:
                        //back
                        return;

                }
            }
        } catch (Exception e) {
            menu();
        }
    }

    private void attackMode() {
        ArrayList<String> loadedGames = new ArrayList<>();
        AttackView attackView = new AttackView();
        while (true) {
            attackView.printLoadedList(loadedGames);
            command.update();
            if (command.getItemNumber() == 1) {//load game
                loadNewMapMenu(loadedGames);
            } else if (command.getItemNumber() == loadedGames.size() + 2) {//back
                return;
            } else if (command.getItemNumber() > loadedGames.size() + 2) {//invalid command
                view.invalidCommand();
            } else { // loading one of saved maps
                // TODO: 5/8/2018
            }

        }

    }

    private void loadNewMapMenu(ArrayList<String> loadedGames) {
        ConverterEnemyJsonToVillage converter = new ConverterEnemyJsonToVillage();
        view.printLoadNewMap();
        command.update();
        String path = command.getStrCommand();
        if (Files.isFileExisted(path)) {
            try {
                converter.getEnemyVillage(path);
                loadedGames.add(path);
            } catch (Exception e) {
                e.printStackTrace();
                view.JsonFileCorrupted();
            }
        } else {
            Files.printFileNotFound();
        }

    }

    /*private void attackMode() throws JsonFileIsCorruptException, IOException {
        ConverterEnemyJsonToVillage converter = new ConverterEnemyJsonToVillage();
        command.update();
        Village enemyVillage = converter.getEnemyVillage(command.getStrCommand());
        Attack attack = new Attack(village.getSoldiers(), enemyVillage);
        AttackController attackController = new AttackController(village );
        attackController.attackMenu();
        try {
            village.addScore(attack.getTakenLoot().getScore());
            village.addResources(attack.getTakenLoot());
        } catch (StoragesIsFullException e) {
            e.printStackTrace();
        }
    }*/

    public void showBuilding() {
        view.printBuildingsList(village.getBuildings());
        updateCommand();
        if (command.getType() == CommandType.SHOW_MENU) {
            showBuilding();
        }
        if (command.getType() == CommandType.BACK) {
            return;
        }

        for (Building building :
                village.getBuildings()) {
            if (command.getBuildingName().equals(building.getClass().getSimpleName()) && command.getBuildingID() == building.getID()) {
                if (building.getClass().getSimpleName().equals(AirDefence.class.getSimpleName())
                        || building.getClass().getSimpleName().equals(AirDefence.class.getSimpleName())
                        || building.getClass().getSimpleName().equals(ArcherTower.class.getSimpleName())
                        || building.getClass().getSimpleName().equals(WizardTower.class.getSimpleName())
                        || building.getClass().getSimpleName().equals(Cannon.class.getSimpleName())
                        || building.getClass().getSimpleName().equals(Trap.class.getSimpleName())
                        || building.getClass().getSimpleName().equals(GiantsCastle.class.getSimpleName())) {
                    defenceMenu(((DefenceBuilding) building));
                }

                if (building.getClass().getSimpleName().equals(Camp.class.getSimpleName())) {
                    campMenu((Camp) building);
                    break;

                }
                if (building.getClass().getSimpleName().equals(Barracks.class.getSimpleName())) {
                    barracksMenu((Barracks) building);
                    break;

                }
                if (building.getClass().getSimpleName().equals(GoldMine.class.getSimpleName()) || building.getClass().getName().equals(ElixirMine.class.getName())) {
                    mineMenu((Mine) building);
                    break;

                }
                if (building.getClass().getSimpleName().equals(GoldStorage.class.getSimpleName()) || building.getClass().getName().equals(ElixirStorage.class.getName())) {
                    storageMenu((Storage) building);
                    break;

                }
                if (building.getClass().getSimpleName().equals(TownHall.class.getSimpleName())) {
                    townHallMenu((TownHall) building);
                    break;
                }
            }
        }
    }

    public void campMenu(Camp camp) {
        view.printCampMenu();
        while (true) {
            updateCommand();
            if (command.getType() == CommandType.BACK) {
                return;
            }
            if (command.getType() == CommandType.SHOW_MENU) {
                view.printCampMenu();
                continue;
            }
            if (command.getType() == CommandType.WHERE_AM_I) {
                view.printWhereAmI(village);
                continue;
            }
            switch (command.getItemNumber()) {
                case 1:
                    notUpgradeableBuildingInfo(camp);
                    break;
                case 2:
//                    soldiersPrint(camp);
                    break;
                case 3:
                    return;
            }
        }
    }

    public HashMap<SoldierType , Integer> campListOfSoldiers(Camp camp) {
        HashMap<SoldierType, Integer> list = new HashMap<>();
        for (Soldier soldier :
                camp.getSoldiers()) {
            if (list.containsKey(soldier.getType())) {
                list.replace(soldier.getType(), list.get(soldier.getType()) + 1);
            } else {
                list.put(soldier.getType(), 1);
            }
        }
        return list;
    }

    public void mineMenu(Mine mine) {
        view.printMineMenu();
        while (true) {
            updateCommand();
            if (command.getType() == CommandType.BACK) {
                return;
            }
            if (command.getType() == CommandType.SHOW_MENU) {
                view.printMineMenu();
                continue;
            }
            if (command.getType() == CommandType.WHERE_AM_I) {
                view.printWhereAmI(mine);
                continue;
            }
            switch (command.getItemNumber()) {
                case 1:
                    upgradeableBuildingInfo(mine);
                    break;
                case 2:
                    mine(mine);
                    break;
                case 3:
                    return;
            }
        }

    }

    public void mine(Mine mine) {
        try {
            if (mine.getClass() == GoldMine.class) {
                Resource resource = new Resource(mine.getStorage(), 0);
                village.addResources(resource);
                mine.setStorage(0);
            } else {
                Resource resource = new Resource(0, mine.getStorage());
                village.addResources(resource);
                mine.setStorage(0);
            }

        } catch (StoragesIsFullException e) {
            return;
        }
    }

    public void storageMenu(Storage storage) {
        while (true) {
            view.printStorageMenu();
            updateCommand();
            if (command.getType() == CommandType.BACK) {
                return;
            }
            if (command.getType() == CommandType.SHOW_MENU) {
                view.printStorageMenu();
                continue;
            }
            if (command.getType() == CommandType.WHERE_AM_I) {
                view.printWhereAmI(storage);
                continue;
            }
            switch (command.getItemNumber()) {
                case 1:
                    storageInfo(storage);
                    break;
                case 2:
                    return;
            }
        }
    }

    public void barracksMenu(Barracks barracks) {
        while (true) {
            view.printBarrackMenu();
            updateCommand();
            if (command.getType() == CommandType.BACK) {
                return;
            }
            if (command.getType() == CommandType.SHOW_MENU) {
                view.printBarrackMenu();
                continue;
            }
            if (command.getType() == CommandType.WHERE_AM_I) {
                view.printWhereAmI(barracks);
                continue;
            }
            switch (command.getItemNumber()) {
                case 1:
                    upgradeableBuildingInfo(barracks);
                    break;
                case 2:
                    buildSoldierMenu(barracks);
                    break;
                case 3:
                    view.soldierWaitListPrint(barracks.getWaitList());
                    break;
                case 4:
                    return;
            }
        }
    }

    public void buildSoldierMenu(Barracks barracks) {
        printSoldiersMenu();
        updateCommand();
        if (command.getType() == CommandType.BACK) {
            return;
        }
        if (command.getType() == CommandType.SHOW_MENU) {
            printSoldiersMenu();
        }
        if (command.getType() == CommandType.WHERE_AM_I)
            view.printWhereAmI(barracks);
        SoldierType newSoldier = listOfSoldiersToBuild().get(command.getItemNumber());
        if (howManyCanBuild(newSoldier) < 0) {
            view.printErrorCantBuildSoldier();
            return;
        }
        view.printHowManySoldierWantToBuild();
        updateCommand();
        if (command.getType() == CommandType.BACK) {
            return;
        }
        if (command.getType() == CommandType.WHERE_AM_I)
            view.printWhereAmI(barracks);
        int number = command.getItemNumber();
        if (number > howManyCanBuild(newSoldier)) {
            view.printDontHaveEnoughResource();
            return;
        }
        village.trainTroops(newSoldier, number, barracks);
    }

    public void printSoldiersMenu() {
        HashMap<Integer, SoldierType> list = listOfSoldiersToBuild();
        if (listOfSoldiersToBuild().size() == 0)
            return;
        for (Integer integer :
                list.keySet()) {
            switch (list.get(integer)) {
                case DRAGON:
                    view.printAvailableSoldier(list.get(integer), integer, village.getElixirResourceInStorages() / Dictionary.getInstance().getConst(DictionaryIndexType.DRAGON_BUILDING_COST));
                    break;
                case HEALER:
                    break;
                case GIANT:
                    view.printAvailableSoldier(list.get(integer), integer, village.getElixirResourceInStorages() / Dictionary.getInstance().getConst(DictionaryIndexType.GIANT_BUILDING_COST));
                    break;
                case GAURDIAN:
                    view.printAvailableSoldier(list.get(integer), integer, village.getElixirResourceInStorages() / Dictionary.getInstance().getConst(DictionaryIndexType.GAURDIAN_BUILDING_COST));
                    break;
                case WALLBREAKER:
                    view.printAvailableSoldier(list.get(integer), integer, village.getElixirResourceInStorages() / Dictionary.getInstance().getConst(DictionaryIndexType.WALL_BREAKER_BUILDING_COST));
                    break;
                case ARCHER:
                    view.printAvailableSoldier(list.get(integer), integer, village.getElixirResourceInStorages() / Dictionary.getInstance().getConst(DictionaryIndexType.ARCHER_BUILDING_COST));
                    break;
            }

        }

    }

    public int howManyCanBuild(SoldierType type) {
        switch (type) {
            case DRAGON:
                return village.getElixirResourceInStorages() / Dictionary.getInstance().getConst(DictionaryIndexType.DRAGON_BUILDING_COST);
            case HEALER:
                break;
            case GIANT:
                return village.getElixirResourceInStorages() / Dictionary.getInstance().getConst(DictionaryIndexType.GIANT_BUILDING_COST);
            case GAURDIAN:
                return village.getElixirResourceInStorages() / Dictionary.getInstance().getConst(DictionaryIndexType.GAURDIAN_BUILDING_COST);
            case WALLBREAKER:
                return village.getElixirResourceInStorages() / Dictionary.getInstance().getConst(DictionaryIndexType.WALL_BREAKER_BUILDING_COST);
            case ARCHER:
                return village.getElixirResourceInStorages() / Dictionary.getInstance().getConst(DictionaryIndexType.ARCHER_BUILDING_COST);
        }
        return 0;
    }


    public HashMap<Integer, SoldierType> listOfSoldiersToBuild() {
        HashMap<Integer, SoldierType> soldiers = new HashMap<>();
        int i = 1;
        soldiers.put(i++, SoldierType.ARCHER);
        soldiers.put(i++, SoldierType.DRAGON);
        soldiers.put(i++, SoldierType.GAURDIAN);
        soldiers.put(i++, SoldierType.GIANT);
        soldiers.put(i++, SoldierType.WALLBREAKER);
        return soldiers;
    }

    public void townHallMenu(TownHall townHall) {
        while (true) {
            view.printVillageMenu();
            updateCommand();
            if (command.getType() == CommandType.BACK) {
                return;
            }
            if (command.getType() == CommandType.SHOW_MENU) {
                view.printVillageMenu();
                continue;
            }
            if (command.getType() == CommandType.WHERE_AM_I) {
                view.printWhereAmI(townHall);
                continue;
            }
            switch (command.getItemNumber()) {
                case 1:
                    upgradeableBuildingInfo(townHall);
                    break;
                case 2:
//                    availableBuildings();
                    break;
                case 3:
                    townHallStatus();
                    break;
                case 4:
                    return;
            }
        }
    }
//
//    public void availableBuildings() {
//        HashMap<Integer, Building> buildingsList = availableBuildingsList();
//        view.printAvailableBuilding(buildingsList);
//        updateCommand();
//        if (command.getType() == CommandType.BACK) {
//            return;
//        }
//        if (command.getType() == CommandType.SHOW_MENU) {
//            view.printAvailableBuilding(buildingsList);
//        }
//        try {
//            build(buildingsList.get(command.getItemNumber()));
//        } catch (DontHaveFreeWorkerException e) {
//            view.printDontHaveWorkerError();
//        }
//    }

    public void build(Building building , Coordinate coordinate) throws DontHaveFreeWorkerException, CoordinateOutOfBondException {
        if (village.hasFreeWorkers()) {
            view.printBuildCost(building);
            try {
                village.build(building, coordinate );
                view.buildingAdded(building);
                building.setID();
            } catch (InvalidCoordinateException | CoordinateOutOfBondException e) {
                throw new CoordinateOutOfBondException();
            }
        } else {
            throw new DontHaveFreeWorkerException();
        }
    }

    public HashMap<Integer, Building> availableBuildingsList() {
        HashMap<Integer, Building> buildings = new HashMap<>();
        int i = 1;
        Barracks barracks = new Barracks();
        Camp camp = new Camp();
        ElixirMine elixirMine = new ElixirMine();
        ElixirStorage elixirStorage = new ElixirStorage();
        GoldMine goldMine = new GoldMine();
        GoldStorage goldStorage = new GoldStorage();
        ArcherTower archerTower = new ArcherTower();
        Cannon cannon = new Cannon();
        AirDefence airDefence = new AirDefence();
        WizardTower wizardTower = new WizardTower();
        if (canBuild(airDefence)) {
            buildings.put(i++, airDefence);
        }
        if (canBuild(archerTower)) {
            buildings.put(i++, archerTower);
        }
        if (canBuild(barracks)) {
            buildings.put(i++, barracks);
        }
        if (canBuild(camp)) {
            buildings.put(i++, camp);
        }
        if (canBuild(cannon)) {
            buildings.put(i++, cannon);
        }
        if (canBuild(elixirMine)) {
            buildings.put(i++, elixirMine);
        }
        if (canBuild(elixirStorage)) {
            buildings.put(i++, elixirStorage);
        }
        if (canBuild(goldMine)) {
            buildings.put(i++, goldMine);
        }
        if (canBuild(goldStorage)) {
            buildings.put(i++, goldStorage);
        }
        if (canBuild(wizardTower)) {
            buildings.put(i++, wizardTower);
        }
        return buildings;
    }

    public ArrayList<Building> townHallStatus() {
        ArrayList<Building> inCompleteBuildings = new ArrayList<>();
        for (Building building :
                village.getBuildings()) {
            if (!building.isCompleted()) {
                inCompleteBuildings.add(building);
            }
        }
        return inCompleteBuildings;
    }

    public void storageInfo(Storage storage) {
        view.printStorageInfoMenu();
        while (true) {
            updateCommand();
            if (command.getType() == CommandType.BACK) {
                return;
            }
            if (command.getType() == CommandType.SHOW_MENU) {
                view.printStorageInfoMenu();
                continue;
            }
            if (command.getType() == CommandType.WHERE_AM_I) {
                view.printWhereAmI("Storage InFo");
                continue;
            }
            switch (command.getItemNumber()) {
                case 1:
                    view.printOveralInfo(storage);
                    break;
                case 2:
                    view.printUpgradeInfo(storage);
                    upgradeMenu(storage);
                    break;
                case 3:
                    view.printSourceInfo(storage);
                    break;
                case 4:
                    upgradeMenu(storage);
                    break;
                case 5://back
                    return;
            }
        }
    }

    public void upgradeableBuildingInfo(Building building) {
        while (true) {
            view.upgradeableInfoMenu();
            updateCommand();
            if (command.getType() == CommandType.BACK) {
                return;
            }
            if (command.getType() == CommandType.SHOW_MENU) {
                view.upgradeableInfoMenu();
                continue;
            }
            if (command.getType() == CommandType.WHERE_AM_I) {
                view.printWhereAmI(building);
                continue;
            }
            switch (command.getItemNumber()) {
                case 1://overall info
                    view.printOveralInfo(building);
                    break;
                case 2:
                    view.printUpgradeInfo(building);
                    upgradeMenu(building);
                    return;
                case 3://back
                    return;
            }
        }
    }

    public void notUpgradeableBuildingInfo(Building building) {
        view.campInfoMenu();
        while (true) {
            updateCommand();
            if (command.getType() == CommandType.BACK) {
                return;
            }
            if (command.getType() == CommandType.SHOW_MENU) {
                view.campInfoMenu();
                continue;
            }
            if (command.getType() == CommandType.WHERE_AM_I) {
                view.printWhereAmI(building);
                continue;
            }
            switch (command.getItemNumber()) {
                case 1://overall info
                    view.printOveralInfo(building);
                    break;
                case 2://back
                    return;
            }
        }
    }

    public void upgradeMenu(Building building) {
        view.printUpgradeQuestion();
        updateCommand();
        if (command.getType() == CommandType.BACK) {
            return;
        }
        if (command.getType() == CommandType.WHERE_AM_I) {
            view.printWhereAmI(building);
        }
        switch (command.getType()) {
            case UPGRADE:
                if (canUpgrade(building)) {
                    try {
                        village.spendResources(building.getResourceNeedToUpgrade());
                    } catch (NotEnoughResourceException e) {
                        //this is not happend because of if
                        return;
                    }
                    village.upgrade((Upgradeable) building);
                    view.printUpgradeSuccessfulMassage();
                    return;
                } else {
                    view.printErrorInUpgrade();
                }
                break;
            case BACK:
                return;
        }
    }

    public void defenceMenu(Building building) {
        while (true) {
            view.printDefenceMenu(building);
            updateCommand();
            if (command.getType() == CommandType.BACK) {
                return;
            }
            if (command.getType() == CommandType.SHOW_MENU) {
                view.menu();
                continue;
            }
            if (command.getType() == CommandType.WHERE_AM_I) {
                view.printWhereAmI(building);
                continue;
            }
            switch (command.getItemNumber()) {
                case 1: //Info
                    defenceInfoMenu(building);
                    break;
                case 2: //Target
                    view.showTarget(((DefenceBuilding) building));
                    break;
                case 3: //Back
                    return;
            }
        }
    }


    public void defenceInfoMenu(Building building) {
        while (true) {
            view.printDefenceInfoMenu(building);
            updateCommand();
            if (command.getType() == CommandType.BACK) {
                return;
            }
            if (command.getType() == CommandType.SHOW_MENU) {
                view.printDefenceInfoMenu(building);
                continue;
            }
            if (command.getType() == CommandType.WHERE_AM_I) {
                view.printWhereAmI(building);
                continue;
            }
            switch (command.getItemNumber()) {
                case 1: //Overall info
                    view.printOveralInfo(building);
                    break;
                case 2: //Upgrade Info
                    view.printUpgradeInfo(building);
                    upgradeMenu(building);
                    break;
                case 3: //Attack Info
                    view.printAttackInfo(((DefenceBuilding) building));
                    break;
                case 4: //Back
                    return;
            }
        }
    }

    public boolean canUpgrade(Building building) {
        if (building.getResourceNeedToUpgrade().getElixir() <= village.getElixirResourceInStorages()
                && building.getResourceNeedToUpgrade().getElixir() <= village.getElixirResourceInStorages()
                && (building.getLevel() < village.getTownHall().getLevel() || (building.getClass() == TownHall.class))) {
            return true;
        }
        return false;
    }

    public boolean canBuild(Building building) {
        if (building.getResourceNeedToBuild().getElixir() <= village.getElixirResourceInStorages()
                && (building.getResourceNeedToBuild().getGold() <= village.getGoldResourceInStorages())) {
            return true;
        }
        return false;
    }

    private void turn(int num) {
        for (int i = 0; i < num; i++) {
            village.cyclePass();
        }
    }

    private void updateCommand() {
        command.update();
        village.cyclePass();

        if (command.getType() == CommandType.RESOURCES) {
            view.printResource(village);
            updateCommand();
        } else if (command.getType() == CommandType.TURN) {
            turn(command.getTurnValue() - 1);
            updateCommand();
        } else if (command.getType() == CommandType.SAVE_GAME) {
            GameCenterController gameCenterController = new GameCenterController();
            gameCenterController.saveGame(village, command);
        }
    }

    public Village getvillage() {
        return village;
    }

    public int numberOfWorkers() {
        return village.getWorkers().size();
    }
}


