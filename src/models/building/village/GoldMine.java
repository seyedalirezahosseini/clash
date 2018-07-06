package models.building.village;

import enums.*;
import models.Dictionary;
import models.Loot;
import models.Village;

import static enums.DictionaryIndexType.GOLD_MINE_BUILD_TIME;
import static enums.DictionaryIndexType.GOLD_MINE_VALUE_PER_DELTAT;

public class GoldMine extends Mine {
    public GoldMine() {
        this.upgradeTime  = this.timeToComplete = Dictionary.getInstance().getConst(GOLD_MINE_BUILD_TIME);
        type = BuildingType.MINE;
        exactType = BuildingType.GOLD_MINE;
        this.strength = Dictionary.getInstance().getConst(DictionaryIndexType.GOLD_MINE_HEALTH);
        this.resourceNeedToBuild = Dictionary.getInstance().get(DictionaryIndexType.GOLD_MINE_BUILD);
        this.gainLoot = (Loot) Dictionary.getInstance().get(DictionaryIndexType.GOLD_MINE_LOOT);
        this.maxStrength = this.strength;

    }

    public void setID() {
        this.ID = GoldMine.generateIDCode();

    }

    private static int numberOfBuildings = 0;
    public static int generateIDCode() {
        numberOfBuildings++;
        return numberOfBuildings;
    }

    public void passDeltaT(Village village) throws Exception{
        if(village.getGoldResourceInStorages() < village.maxGoldCapacity() || this.isCompleted)
        this.storage += Dictionary.getInstance().getConst(GOLD_MINE_VALUE_PER_DELTAT);
    }

    @Override
    public void upgrade() {
        super.upgrade();
        Dictionary.getInstance().replace( GOLD_MINE_VALUE_PER_DELTAT , (int)(Dictionary.getInstance().getConst(GOLD_MINE_VALUE_PER_DELTAT)*1.6));
    }

    @Override
    public int getUpgradeTime() {
        return upgradeTime;
    }
}
