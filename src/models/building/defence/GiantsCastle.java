package models.building.defence;

import enums.SoldierMoveType;
import models.livingBeing.Attack.Soldier;
import models.livingBeing.Defence.Giant;

import java.util.ArrayList;

public class GiantsCastle extends DefenceBuilding {
    private Giant giant = new Giant();//in che karie ham new kardi ham toye constractor voroodi grefti

    /*public GiantsCastle(Giant giant) {
        type = BuildingType.GIANTS_CASTLE;
        this.giant = giant;
    }
*/
    //nothing yet we are mot supposed to initialize it yet
    public void attack(ArrayList<Soldier> Soldiers) {
        // TODO: 4/29/2018 has a different attack method
    }

    @Override
    public int getAttackRadius() {
        return 0;
    }

    @Override
    public SoldierMoveType getSoldiersMoveType() {
        return null;
    }

    private static int numberOfBuildings = 0;
    public static int generateIDCode() {
        numberOfBuildings++;
        return numberOfBuildings;
    }

    @Override
    public Soldier getTarget() {
        return null;
    }

    @Override
    public int getUpgradeTime() {
        return 0;
    }
}
