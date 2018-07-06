package models.building.village;

import enums.BuildingType;
import enums.DictionaryIndexType;
import exceptions.*;
import models.Dictionary;
import models.Loot;

import static enums.DictionaryIndexType.GOLD_STORAGE_BUILD_TIME;


public class GoldStorage extends Storage {

    public static final int CAPACITY = 500;



    public GoldStorage() {
        this.upgradeTime = this.timeToComplete = Dictionary.getInstance().getConst(GOLD_STORAGE_BUILD_TIME);
        capacity = CAPACITY;
        type = BuildingType.STORAGE;
        exactType = BuildingType.GOLD_STORAGE;
        this.strength = Dictionary.getInstance().getConst(DictionaryIndexType.GOLD_STPRAGE_HEALTH);
        this.resourceNeedToBuild = Dictionary.getInstance().get(DictionaryIndexType.GOLD_STORAGE_BUILD);
        this.gainLoot = (Loot) Dictionary.getInstance().get(DictionaryIndexType.GOLD_STORAGE_LOOT);
    }

    public void setID() {
        this.ID = GoldStorage.generateIDCode();

    }

    private static int numberOfBuildings = 0;
    public static int generateIDCode() {
        numberOfBuildings++;
        return numberOfBuildings;
    }



    public void addResource(int value) throws NotEnoughCapacityException, InvalidValueException {
        if (value < 0 ) {
            throw new InvalidValueException();
        }
        if (value + this.resource.getGold() > capacity ) {
            throw new NotEnoughCapacityException();
        }
        this.resource.setGold( this.resource.getGold() + value );
    }

    public void spendResource(int value) throws NotEnoughResourceException , InvalidValueException {
        if (value > this.resource.getGold()) {
            throw new NotEnoughResourceException();
        }
        if (value < 0) {
            throw new InvalidValueException();
        }
        this.resource.setGold(this.resource.getGold() - value);
    }

    @Override
    public int getUpgradeTime() {
        return upgradeTime;
    }
}
