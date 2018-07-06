package models.building.village;

import enums.*;
import models.Dictionary;
import models.*;

import static enums.DictionaryIndexType.ELIXIR_MINE_BUILD_TIME;
import static enums.DictionaryIndexType.ELIXIR_MINE_VALUE_PER_DELTAT;

public class ElixirMine extends Mine {

    public ElixirMine() {
        this.upgradeTime  = this.timeToComplete = Dictionary.getInstance().getConst(ELIXIR_MINE_BUILD_TIME);
        type = BuildingType.MINE;
        exactType = BuildingType.ELIXIR_MINE;
        this.strength = Dictionary.getInstance().getConst(DictionaryIndexType.ELIXIR_MINE_HEALTH);
        this.resourceNeedToBuild = Dictionary.getInstance().get(DictionaryIndexType.ELIXIR_MINE_BUILD);
        this.gainLoot = (Loot) Dictionary.getInstance().get(DictionaryIndexType.ELIXIR_MINE_LOOT);
    }
    public void setID() {
        this.ID = ElixirMine.generateIDCode();

    }

    private static int numberOfBuildings = 0;
    public static int generateIDCode() {
        numberOfBuildings++;
        return numberOfBuildings;
    }

    public void passDeltaT(Village village) throws Exception{
        if (village.maxElixirCapacity() > village.getElixirResourceInStorages() || this.isCompleted) {
            this.storage += Dictionary.getInstance().getConst(ELIXIR_MINE_VALUE_PER_DELTAT);
        }
    }


    @Override
    public void upgrade() {
        super.upgrade();
        Dictionary.getInstance().replace(ELIXIR_MINE_VALUE_PER_DELTAT , (int) (Dictionary.getInstance().getConst(ELIXIR_MINE_VALUE_PER_DELTAT)*1.6));
    }

    @Override
    public int getUpgradeTime() {
        return upgradeTime;
    }
}
