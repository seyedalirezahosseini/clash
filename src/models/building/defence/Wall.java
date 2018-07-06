package models.building.defence;

import enums.AttackType;
import enums.BuildingType;
import enums.DictionaryIndexType;
import enums.SoldierMoveType;
import models.Dictionary;
import models.livingBeing.Attack.Soldier;

import java.util.ArrayList;

public class Wall extends DefenceBuilding {
    private static int UPGRADE_TIME;
    private static int numberOfInstances = 0;

    static {
        UPGRADE_TIME = Dictionary.getInstance().getConst(DictionaryIndexType.AIR_DEFENCE_UPGRADE_TIME);
    } // initializing static values

    {
        type = exactType = BuildingType.WALL;
    }

    private static int numberOfBuildings = 0;

    public static int generateIDCode() {
        numberOfBuildings++;
        return numberOfBuildings;
    }


    public int getUpgradeTime() {
        return 0;
    }

    @Override
    public void attack(ArrayList<Soldier> soldiers) {
    }

}
