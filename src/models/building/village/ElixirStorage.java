package models.building.village;

import enums.*;
import exceptions.*;
import models.*;

import static enums.DictionaryIndexType.ELIXIR_STORAGE_BUILD_TIME;


public class ElixirStorage extends Storage {

    public static final int CAPACITY = 250;

    public ElixirStorage() {
        this.upgradeTime  = this.timeToComplete = Dictionary.getInstance().getConst(ELIXIR_STORAGE_BUILD_TIME);
        capacity = CAPACITY;
        type = BuildingType.STORAGE;
        exactType = BuildingType.ELIXIR_STORAGE;
        this.strength = Dictionary.getInstance().getConst(DictionaryIndexType.ELIXIR_STORAGE_HEALTH);
        this.resourceNeedToBuild = Dictionary.getInstance().get(DictionaryIndexType.ELIXIR_STORAGE_BUILD);
        this.gainLoot = (Loot) Dictionary.getInstance().get(DictionaryIndexType.ELIXIR_STORAGE_LOOT);
        this.maxStrength = this.strength;

    }

    public void setID() {
        this.ID = ElixirStorage.generateIDCode();

    }

    private static int numberOfBuildings = 0;
    public static int generateIDCode() {
        numberOfBuildings++;
        return numberOfBuildings;
    }

    public void addResource(int value) throws InvalidValueException, NotEnoughCapacityException {
        if (value < 0 ) {
            throw new InvalidValueException();
        }
        if (value + this.resource.getElixir() > capacity ) {
            throw new NotEnoughCapacityException();
        }
        this.resource.setElixir( this.resource.getElixir() + value );
    }

    public void spendResource(int value) throws NotEnoughResourceException ,InvalidValueException{
        if (value > this.resource.getElixir()) {
            throw new NotEnoughResourceException();
        }
        if (value < 0 ) {
            throw new InvalidValueException();
        }
        this.resource.setElixir( this.resource.getElixir() - value );
    }

    @Override
    public int getUpgradeTime() {
        return upgradeTime;
    }
}
