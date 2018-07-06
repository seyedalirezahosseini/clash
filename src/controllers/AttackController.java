package controllers;

import enums.CommandType;
import enums.DictionaryIndexType;
import enums.SoldierType;
import exceptions.JsonFileIsCorruptException;
import models.*;
import models.building.village.Camp;
import models.livingBeing.Attack.Soldier;
import view.AttackView;
import view.Command;
import view.Files;
import view.MapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AttackController {
    private Village village;
    private Village enemy;
    private Command command;
    private AttackView view;
    private Attack model;
    private ArrayList<Soldier> soldiers;
    private ArrayList<String> fileNames;
    private ArrayList<Soldier> outOfWar;

    public AttackController(Village village) {
        fileNames = new ArrayList<>();
        this.village = village;
        outOfWar = new ArrayList<>();
        enemy = new Village();
        command = new Command();
        view = new AttackView();
        soldiers = new ArrayList<>();
    }

    public void attackMenu() {
        while (true) {
            view.printLoadedList(fileNames);
            command.update();
            if (command.getType() == CommandType.BACK || command.getItemNumber() == fileNames.size() + 2) {
                return;
            }
            if (command.getType() == CommandType.WHERE_AM_I) {
                view.inAttackMenu();
                continue;
            }
            int chosenMap = command.getItemNumber();
            if (chosenMap == 1) {
                loadMap();
                continue;
            } else if (chosenMap < 1 || chosenMap > fileNames.size() + 1) {
                continue;
            }
            if (!loadSavedMap(chosenMap)) {
                return;
            }
        }
    }

    public boolean loadSavedMap(int chosen) {
        //false return means that the attack started
        ConverterEnemyJsonToVillage converter = new ConverterEnemyJsonToVillage();
        try {
            enemy = converter.getEnemyVillage(fileNames.get(chosen - 2));
        } catch (JsonFileIsCorruptException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            view.showLoadedMapMenu();
            command.update();
            if (command.getType() == CommandType.WHERE_AM_I) {
                view.inLoadSaveMap();
            }
            if (command.getType() == CommandType.BACK) {
                return true;
            }
            switch (command.getItemNumber()) {
                case 1:
                    view.showMapInfo(new Resource(enemy.getGoldResourceInStorages(),
                            enemy.getElixirResourceInStorages()), new ArrayList<>());
                    break;
                case 2:
                    attackStarted();
                    return false;
                case 3:
                    return true;
            }
        }
    }

    public void attackStarted() {
        view.startSelect();
        HashMap<SoldierType, Integer> soldiersToAdd = new HashMap<>();
        while (true) {
            command.update();
            if (command.getType() == CommandType.BACK) {
                return;
            }
            if (command.getType() == CommandType.END_SELECT)
                break;
            if (command.addSoldiers()) {
                if (soldiersToAdd.containsKey(command.getTypeToAdd())) {
                    soldiersToAdd.replace(command.getTypeToAdd(), soldiersToAdd.get(command.getTypeToAdd()),
                            soldiersToAdd.get(command.getTypeToAdd()) + command.getSoldiersToAdd());
                } else {
                    soldiersToAdd.put(command.getTypeToAdd(), command.getSoldiersToAdd());
                }
            }
        }
        for (Camp camp :
                village.getCamps()) {
            if (soldiersToAdd.size() <= 0)
                break;
            soldiers.addAll(camp.sendSoldierToAttack(soldiersToAdd));
        }
        view.showUnavailableSoldiers(soldiersToAdd);
        initializingSoldiers();
        attack();
    }

    public boolean isCellFull(Coordinate coordinate) {
        if (coordinate == null) {
            return false;
        }
        for (Coordinate coord:
                enemy.getBuidingsHashMap().keySet()) {
            if (coord != null) {
                if (coord.getX() == coordinate.getX() && coord.getY() == coordinate.getY()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void initializingSoldiers() {
        MapView mapView = new MapView();
        mapView.printAvailablePlaces(enemy);
        outer:
        while (true) {
            command.update();
            if (command.getType() == CommandType.WHERE_AM_I) {
                view.inSoldiersInitializing();
                continue;
            }
            if (command.getType() == CommandType.BACK) {
                return;
            }
            if (command.getType() == CommandType.PUT_UNIT) {
                int x = command.getUnitX();
                int y = command.getUnitY();
                if (x < 1 || y < 1 || x > Dictionary.getInstance().getConst(DictionaryIndexType.MAP_MAX_COORDINATE) ||
                        y > Dictionary.getInstance().getConst(DictionaryIndexType.MAP_MAX_COORDINATE)) {
                    view.showUnavailableCell();
                    continue;
                }
                if (isCellFull(new Coordinate(x, y))) {
                    view.showUnavailableCell();
                    continue;
                }
                if (command.getSoldierType() == null) {
                    continue;
                }
                int i = 0;
                for (Soldier soldier :
                        soldiers) {
                    if (!soldier.getSet() && soldier.getType() == command.getSoldierType()) {
                        ++i;
                        soldier.setMapAndLocation(enemy.getBuidingsHashMap(), new Coordinate(command.getUnitX(), command.getUnitY()));
                        if (i >= command.getSoldierNumber()) {
                            continue outer;
                        }
                    }
                }
            } else if (command.getType() == CommandType.ONE_TURN) {
                for (int i = 0; i < soldiers.size(); i++) {
                    if (!soldiers.get(i).getSet()) {
                        outOfWar.add(soldiers.get(i));
                        soldiers.remove(i);
                        i--;
                    }
                }
                return;
            }
        }
    }

    public void attack() {
        model = new Attack(soldiers, enemy);
        int turnValue = 0;
        while (true) {
            command.update();
            switch (command.getType()) {
                case QUIT_ATTACK:
                    return;
                case TURN:
                    turnValue = command.getTurnValue();
                    break;
                case ONE_TURN:
                    turnValue = 1;
                    break;
                case STATUS_RESOURCE:
                    turnValue = 1;
                    view.showResourceStatus(model.getTakenLoot(), new Resource(enemy.getGoldResourceInStorages()
                            , enemy.getElixirResourceInStorages()));
                    break;
                case STATUS_SOLDIER:
                    view.showSoldierStatus(soldiers, command.getSoldierType());
                    break;
                case SHOW_BUILDINGS:
                    view.showBuildingStatus(enemy.getBuildings(), command.getTowerType());
                    break;
                case STATUS_WAR:
                    turnValue = 1;
                    view.showResourceStatus(model.getTakenLoot(), new Resource(enemy.getGoldResourceInStorages()
                            , enemy.getElixirResourceInStorages()));
                    view.showSoldierStatus(soldiers, null);
                    view.showBuildingStatus(enemy.getBuildings(), null);
                    break;
                default:
                    turnValue = 0;

            }
            for (int i = 0; i < turnValue; i++) {
                if (model.cyclePassed() || model.getTakenLoot().getElixir() +
                        village.getElixirResourceInStorages() >= village.maxElixirCapacity() ||
                        model.getTakenLoot().getGold() + village.getGoldResourceInStorages() >= village.maxGoldCapacity()
                        || enemy.getGoldResourceInStorages() <= 0 || enemy.getElixirResourceInStorages() <= 0) {
                    view.warEnded(model.getTakenLoot());
                    return;
                }
            }
        }
    }

    public ArrayList<Soldier> getRemainedSoldiers() {
        for (int i = 0; i < soldiers.size(); i++) {
            if (soldiers.get(i).isDead()) {
                soldiers.remove(i);
                i--;
            }
        }
        soldiers.addAll(outOfWar);
        return soldiers;
    }


    public void loadMap() {
        view.showLoadMap();
        command.update();
        if (command.getType() == CommandType.WHERE_AM_I) {
            view.inLoadMap();
        }
        if (command.getType() == CommandType.BACK) {
            return;
        }
        if (!Files.isFileExisted(command.getCommandString())) {
            view.invalidFile();
            return;
        }
        ConverterEnemyJsonToVillage converter = new ConverterEnemyJsonToVillage();
        try {
            converter.getEnemyVillage(command.getStrCommand());
        } catch (Exception e) {
            view.invalidFile();
            return;
        }
        fileNames.add(command.getStrCommand());
    }

}