package models.building.village;

import enums.BuildingType;
import enums.DictionaryIndexType;
import models.Dictionary;
import models.Loot;
import models.Resource;
import models.building.Building;
import models.interfaces.Upgradeable;

public class TownHall extends Building implements Upgradeable {

    public static final int STRENGTH_UPGRADE = 500;
    private static int numberOfBuildings = 0;

    public static int generateIDCode() {
        numberOfBuildings++;
        return numberOfBuildings;
    }

    public void setID() {
        this.ID = TownHall.generateIDCode();

    }

    public TownHall() {
        this.strength = Dictionary.getInstance().getConst(DictionaryIndexType.TOWNHALL_HEALTH);
        this.maxStrength = this.strength;
        this.resourceNeedToBuild = Dictionary.getInstance().get(DictionaryIndexType.TOWNHALL_BUILD);
        this.gainLoot = (Loot) Dictionary.getInstance().get(DictionaryIndexType.TOWNHALL_LOOT);
        exactType = type = BuildingType.TOWN_HALL;
        this.isCompleted = true;
        this.upgradeTime = this.timeToComplete = 1;
    }

    @Override
    public void upgrade() {
        this.level++;
        strength += STRENGTH_UPGRADE;
    }

    @Override
    public int getUpgradeTime() {
        return upgradeTime;
    }

    public Resource getResourceNeedToUpgrade() {
        return new Resource(500, 0);
    }
}
