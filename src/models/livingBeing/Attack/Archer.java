package models.livingBeing.Attack;

import enums.*;
import enums.DictionaryIndexType;
import enums.SoldierMoveType;
import models.building.Building;
import models.Dictionary;

import java.util.ArrayList;

public class Archer extends Soldier {
    @Override
    public ArrayList<Building> findPossibleAims() {
        ArrayList<Building> possibleAims = new ArrayList<>();
        ArrayList<Building> ordinaryBuildings = new ArrayList<>();
        for (Building building:
             map.values()) {
            if(!building.isDestroyed()) {
                if (building.getType() == BuildingType.DEFENSIVE_TOWER || building.getType() == BuildingType.WALL)
                    possibleAims.add(building);
                else
                    ordinaryBuildings.add(building);

            }
        }
        if (possibleAims.size() != 0)
            return possibleAims;
        return ordinaryBuildings;
    }

    public Archer(int numberOfUpgrades) {
        buildCost = Dictionary.getInstance().getConst(DictionaryIndexType.ARCHER_BUILDING_COST);
        buildTime = Dictionary.getInstance().getConst(DictionaryIndexType.ARCHER_BUILDING_TIME);
        type = SoldierType.ARCHER;
        moveType = SoldierMoveType.GROUND;
        strength = Dictionary.getInstance().getConst(DictionaryIndexType.ARCHER_STRENGTH);
        fullHealth = Dictionary.getInstance().getConst(DictionaryIndexType.ARCHER_HEALTH);
        speed = Dictionary.getInstance().getConst(DictionaryIndexType.ARCHER_SPEED);
        attackRadius = Dictionary.getInstance().getConst(DictionaryIndexType.ARCHER_ATTACK_RADIUS);
        primitiveDataSetter(numberOfUpgrades);
    }

}
