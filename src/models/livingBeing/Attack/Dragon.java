package models.livingBeing.Attack;

import enums.DictionaryIndexType;
import enums.SoldierMoveType;
import enums.SoldierType;
import models.building.Building;
import models.Dictionary;

import java.util.ArrayList;

public class Dragon extends Soldier {
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

    public Dragon(int numberOfUpgrades) {
        buildCost = Dictionary.getInstance().getConst(DictionaryIndexType.DRAGON_BUILDING_COST);
        buildTime = Dictionary.getInstance().getConst(DictionaryIndexType.DRAGON_BUILDING_TIME);
        type = SoldierType.DRAGON;
        moveType = SoldierMoveType.AIR;
        strength = Dictionary.getInstance().getConst(DictionaryIndexType.DRAGON_STRENGTH);
        fullHealth = Dictionary.getInstance().getConst(DictionaryIndexType.DRAGON_HEALTH);
        speed = Dictionary.getInstance().getConst(DictionaryIndexType.DRAGON_SPEED);
        attackRadius = Dictionary.getInstance().getConst(DictionaryIndexType.DRAGON_ATTACK_RADIUS);
        primitiveDataSetter(numberOfUpgrades);
    }

}