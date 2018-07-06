package models.livingBeing.Attack;

import enums.BuildingType;
import enums.DictionaryIndexType;
import enums.SoldierMoveType;
import enums.SoldierType;
import models.building.Building;
import models.Dictionary;

import java.util.ArrayList;

public class Giant extends Soldier {
    @Override
    public ArrayList<Building> findPossibleAims() {
        ArrayList<Building> possibleAims = new ArrayList<>();
        ArrayList<Building> ordinaryBuildings = new ArrayList<>();
        for (Building building:
                map.values()) {
            if(!building.isDestroyed()) {
                if (building.getType() == BuildingType.MINE || building.getType() == BuildingType.STORAGE)
                    possibleAims.add(building);
                else
                    ordinaryBuildings.add(building);
            }
        }
        if(possibleAims.size() != 0)
            return possibleAims;
        return ordinaryBuildings;
    }

    public Giant(int numberOfUpgrades) {
        buildCost = Dictionary.getInstance().getConst(DictionaryIndexType.GIANT_BUILDING_COST);
        buildTime = Dictionary.getInstance().getConst(DictionaryIndexType.GIANT_BUILDING_TIME);
        type = SoldierType.GIANT;
        moveType = SoldierMoveType.GROUND;
        strength = Dictionary.getInstance().getConst(DictionaryIndexType.GIANT_STRENGTH);
        fullHealth = Dictionary.getInstance().getConst(DictionaryIndexType.GIANT_HEALTH);
        speed = Dictionary.getInstance().getConst(DictionaryIndexType.GIANT_SPEED);
        attackRadius = Dictionary.getInstance().getConst(DictionaryIndexType.GIANT_ATTACK_RADIUS);
        primitiveDataSetter(numberOfUpgrades);
    }

    public void isBeingHealed(int heal) {

    }

}
