package models;

import controllers.TimeController;
import enums.DictionaryIndexType;
import enums.SoldierType;
import exceptions.*;
import models.building.Building;
import models.building.defence.DefenceBuilding;
import models.building.village.*;
import models.interfaces.TimeSensitive;
import models.interfaces.Upgradeable;
import models.livingBeing.Attack.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Village implements TimeSensitive {
    private ArrayList<models.building.Building> buildings = new ArrayList<>();
    private Map map = new Map();
    private ArrayList<Storage> storages = new ArrayList<>();
    private int score;
    private String name;
    private ArrayList<Camp> camps = new ArrayList<>();
    private TownHall townHall;
    private ArrayList<Barracks> barrackses = new ArrayList<>();
    private ArrayList<Worker> workers = new ArrayList<>();
    private ArrayList<Upgradeable> upgradeWaitList = new ArrayList<>();
    private ArrayList<Mine> mines = new ArrayList<>(); //to be used in phase 2 (Parsa) need it in converter
    private ArrayList<DefenceBuilding> defenceBuildings = new ArrayList<>();
    private HashMap<Coordinate, Building> buidingsHashMap = new HashMap<>();
    private TimeController timeController;


    public Village() {
        initTownHall();
        initStorages();
        initWorker();
        initTimeController();
    }

    private void initTimeController() {
        timeController = new TimeController(this);
        timeController.start();
    }

    public TownHall getTownHall() {
        return townHall;
    }

    private void initWorker() {
        try {
            this.addNewWorker();
        } catch (NotEnoughLevelException e) {
        }
    }

    private void initTownHall() {
        this.townHall = new TownHall();
        map.townHallInit(townHall);
        townHall.setLevel(1);
        townHall.setID();
        buildings.add(townHall);
    }

    private void initStorages() {
        GoldStorage goldStorage = new GoldStorage();
        goldStorage.setCoordinate(new Coordinate(11, 11));
        goldStorage.setCompleted(true);
        goldStorage.setTimeToComplete(0);
        goldStorage.increaseCapacity(9500);
        ElixirStorage elixirStorage = new ElixirStorage();
        elixirStorage.setCoordinate(new Coordinate(12, 12));
        townHall.setCoordinate(new Coordinate(15, 15));
        try {
            map.addBuilding(elixirStorage, elixirStorage.getCoordinate());
            map.addBuilding(goldStorage, goldStorage.getCoordinate());
        } catch (InvalidCoordinateException e) {
            e.printStackTrace();
        } catch (CoordinateOutOfBondException e) {
            e.printStackTrace();
        }

        elixirStorage.setCompleted(true);
        elixirStorage.setTimeToComplete(0);
        elixirStorage.increaseCapacity(2250);
        elixirStorage.setLevel(1);
        goldStorage.setLevel(1);
        goldStorage.setID();
        elixirStorage.setID();
        storages.add(goldStorage);
        storages.add(elixirStorage);
        buildings.add(goldStorage);
        buildings.add(elixirStorage);
        try {
            goldStorage.addResource(10000);
            elixirStorage.addResource(2500);
        } catch (InvalidValueException e) {
            e.printStackTrace();
        } catch (NotEnoughCapacityException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Soldier> getSoldiers() {
        ArrayList<Soldier> soldiers = new ArrayList<>();
        for (Camp camp :
                camps) {
            for (Soldier soldier :
                    camp.getSoldiers()) {
                soldiers.add(soldier);
            }
        }
        return soldiers;
    }

    public ArrayList<DefenceBuilding> getDefenceBuildings() {
        return defenceBuildings;
    }

    public void addNewWorker() throws NotEnoughLevelException {
        if (((townHall.getLevel() / 5) + 1) > workers.size()) {
            workers.add(new Worker());
        } else {
            throw new NotEnoughLevelException();
        }
    }

    public void upgradeFromList() {
        if (upgradeWaitList.size() > 0) {
            upgrade(upgradeWaitList.get(0));
            upgradeWaitList.remove(upgradeWaitList.get(0));
        }
    }

    public void upgrade(Upgradeable buiding) {
        for (Worker worker :
                workers) {
            if (worker.isFree()) {
                worker.Work(buiding, buiding.getUpgradeTime());
                return;
            }
        }
        upgradeWaitList.add(buiding);
    }

    public boolean hasFreeWorkers() {
        for (Worker worker :
                workers) {
            if (worker.isFree()) {
                return true;
            }
        }
        return false;
    }

    public int getElixirResourceInStorages() {
        int elixir = 0;
        for (Storage storage :
                storages) {
            elixir += storage.getResource().getElixir();
        }
        return elixir;
    }

    public int getGoldResourceInStorages() {
        int gold = 0;
        for (Storage storage :
                storages) {
            gold += storage.getResource().getGold();
        }
        return gold;
    }

    public Village(String name) {
        this.name = name;
    }


    public void cyclePass() {
        for (Barracks barracks :
                barrackses) {
            barracks.passDeltaT(this);
        }
        for (Mine mine :
                mines) {
            try {
                mine.passDeltaT(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Worker worker :
                workers) {
            worker.passDeltaT(this);
        }
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<Storage> getStorages() {
        return storages;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Camp> getCamps() {
        return camps;
    }

    public boolean isAllGoldStorageFull() {
        for (Storage storage :
                this.getStorages()) {
            if (storage.getClass() == GoldStorage.class && storage.getResource().getGold() < storage.getCapacity()) {
                return false;
            }
        }
        return true;
    }

    public boolean isStoragesFull() {
        return isAllElixirStorageFull() && isAllElixirStorageFull();
    }

    public boolean isAllElixirStorageFull() {
        for (Storage storage :
                this.getStorages()) {
            if (storage.getClass() == ElixirStorage.class && storage.getResource().getElixir() < storage.getCapacity()) {
                return false;
            }
        }
        return true;
    }

    public void addResources(Resource resource) throws StoragesIsFullException {//half of resource add to town hall
        int gold = resource.getGold();
        int elixir = resource.getElixir();

        while (gold > 0) {

            if (isAllGoldStorageFull()) {
                break;
            }
            for (Storage storage :
                    this.storages) {
                if (gold == 0) {
                    break;
                }
                if (storage.getClass() == GoldStorage.class) {
                    GoldStorage goldStorage = (GoldStorage) storage;
                    try {
                        if (gold > 50) {
                            goldStorage.addResource(50);
                            gold -= 50;
                        } else {
                            goldStorage.addResource(gold);
                            gold = 0;
                        }
                    } catch (NotEnoughCapacityException notEnoughCapacity) {
                    } catch (InvalidValueException invalidValueException) {
                        invalidValueException.printStackTrace();
                    }
                }
            }
        }

        while (elixir > 0) {
            if (isAllElixirStorageFull()) {
                break;
            }
            for (Storage storage :
                    this.storages) {
                if (elixir == 0) {
                    break;
                }
                if (storage.getClass() == ElixirStorage.class) {
                    ElixirStorage elixirStorage = (ElixirStorage) storage;
                    try {
                        if (elixir > 50) {
                            elixirStorage.addResource(50);
                            elixir -= 50;
                        } else {
                            elixirStorage.addResource(elixir);
                            elixir = 0;
                        }
                    } catch (InvalidValueException invalidValueException) {
                        invalidValueException.printStackTrace();
                    } catch (NotEnoughCapacityException notEnoughCapacity) {
                        return;
                    }
                }
            }
        }

        if (gold > 0 && elixir > 0) {
            throw new StoragesIsFullException();
        }
    }

    public void spendResources(Resource resource) throws NotEnoughResourceException {//half of resource add to town hall
        int gold = resource.getGold();
        int elixir = resource.getElixir();

        while (gold > 0) {
            for (Storage storage :
                    this.storages) {
                if (gold == 0) {
                    break;
                }
                if (storage.getClass() == GoldStorage.class) {
                    GoldStorage goldStorage = (GoldStorage) storage;
                    try {
                        goldStorage.spendResource(1);
                        gold--;

                    } catch (InvalidValueException e) {
                        return;
                    }
                }
            }
        }
        while (elixir > 0) {
            for (Storage storage :
                    this.storages) {
                if (elixir == 0) {
                    break;
                }
                if (storage.getClass() == ElixirStorage.class) {
                    ElixirStorage elixirStorage = (ElixirStorage) storage;
                    try {
                        elixirStorage.spendResource(1);
                        elixir--;

                    } catch (InvalidValueException e) {
                        return;
                    }
                }
            }
        }
    }

    public void build(Building building, Coordinate coordinate) throws InvalidCoordinateException, CoordinateOutOfBondException {
        map.addBuilding(building, coordinate);
        try {
            spendResources(building.getResourceNeedToBuild());
        } catch (NotEnoughResourceException e) {
            e.printStackTrace();
        }
        buildings.add(building);
        for (Worker worker :
                workers) {
            if (worker.isFree()) {
                worker.Work(building);
            }
        }
        if (building.getClass() == GoldStorage.class || building.getClass() == ElixirStorage.class) {
            storages.add((Storage) building);
        } else if (building.getClass() == GoldMine.class || building.getClass() == ElixirMine.class) {
            mines.add((Mine) building);
        } else if (building.getClass() == Barracks.class) {
            barrackses.add((Barracks) building);
        } else if (building.getClass() == Camp.class) {
            camps.add((Camp) building);
        }
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void trainTroops(SoldierType soldier, int num, Barracks barracks) {
        for (int i = 0; i < num; i++) {
            try {
                switch (soldier) {
                    case HEALER:
                        break;
                    case GIANT:
                        spendResources(new Resource(0, Dictionary.getInstance().getConst(DictionaryIndexType.GIANT_BUILDING_COST)));
                        barracks.addSoldier(new Giant(barracks.getLevel()));
                        break;
                    case DRAGON:
                        spendResources(new Resource(0, Dictionary.getInstance().getConst(DictionaryIndexType.DRAGON_BUILDING_COST)));
                        barracks.addSoldier(new Dragon(barracks.getLevel()));
                        break;
                    case GAURDIAN:
                        spendResources(new Resource(0, Dictionary.getInstance().getConst(DictionaryIndexType.GAURDIAN_BUILDING_COST)));
                        barracks.addSoldier(new Gaurdian(barracks.getLevel()));
                        break;
                    case WALLBREAKER:
                        spendResources(new Resource(0, Dictionary.getInstance().getConst(DictionaryIndexType.WALL_BREAKER_BUILDING_COST)));
                        barracks.addSoldier(new WallBreaker(barracks.getLevel()));
                        break;
                    case ARCHER:
                        spendResources(new Resource(0, Dictionary.getInstance().getConst(DictionaryIndexType.ARCHER_BUILDING_COST)));
                        barracks.addSoldier(new Archer(barracks.getLevel()));
                }
            } catch (Exception e) {
                //i
            }
        }
    }

    public void Addloots(Loot loot) throws StoragesIsFullException {
        addScore(loot.getScore());
        addResources(loot);
    }

    public int maxGoldCapacity() {
        int sum = 0;
        for (Storage storage :
                this.storages) {
            if (storage.getClass() == GoldStorage.class) {
                sum += storage.getCapacity();
            }
        }
        return sum;
    }

    public int maxElixirCapacity() {
        int sum = 0;
        for (Storage storage :
                this.storages) {
            if (storage.getClass() == ElixirStorage.class) {
                sum += storage.getCapacity();
            }
        }
        return sum;
    }

    public void remainSoldiers(ArrayList<Soldier> soldiers) {//for remain soldiers after attack
        for (Soldier o :
                soldiers) {
            for (Camp camp :
                    camps) {
                if (camp.getCapacity() > camp.getNumberOfSoldiers()) {
                    camp.addSoldier(o);
                    break;
                }
            }
        }
    }

    public HashMap<Coordinate, Building> getBuidingsHashMap() {
        for (Building building : buildings) {
            buidingsHashMap.put(building.getCoordinate(), building);
        }
        buidingsHashMap.put(new Coordinate(15, 15), townHall);
        buidingsHashMap.put(new Coordinate(15, 16), townHall);
        buidingsHashMap.put(new Coordinate(16, 16), townHall);
        buidingsHashMap.put(new Coordinate(16, 15), townHall);
        return buidingsHashMap;
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }
}
