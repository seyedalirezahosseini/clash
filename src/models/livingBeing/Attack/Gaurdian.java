package models.livingBeing.Attack;

import enums.DictionaryIndexType;
import enums.SoldierMoveType;
import enums.SoldierType;
import models.building.Building;
import models.Dictionary;

import java.util.ArrayList;

public class Gaurdian extends Soldier {
    @Override
    public ArrayList<Building> findPossibleAims() {
        ArrayList<Building> possibleAims = new ArrayList<>();
        for (Building building:
                map.values()) {
            if(!building.isDestroyed())
                possibleAims.add(building);
        }
        return possibleAims;
    }

    public Gaurdian(int numberOfUpgrades) {
        buildCost = Dictionary.getInstance().getConst(DictionaryIndexType.GAURDIAN_BUILDING_COST);
        buildTime = Dictionary.getInstance().getConst(DictionaryIndexType.GAURDIAN_BUILDING_TIME);
        type = SoldierType.GAURDIAN;
        moveType = SoldierMoveType.GROUND;
        strength = Dictionary.getInstance().getConst(DictionaryIndexType.GAURDIAN_STRENGTH);
        fullHealth = Dictionary.getInstance().getConst(DictionaryIndexType.GAURDIAN_HEALTH);
        speed = Dictionary.getInstance().getConst(DictionaryIndexType.GAURDIAN_SPEED);
        attackRadius = Dictionary.getInstance().getConst(DictionaryIndexType.GAURDIAN_ATTACK_RADIUS);
        primitiveDataSetter(numberOfUpgrades);
    }

}