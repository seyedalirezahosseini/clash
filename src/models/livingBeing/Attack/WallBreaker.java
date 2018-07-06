package models.livingBeing.Attack;

import enums.BuildingType;
import enums.DictionaryIndexType;
import enums.SoldierMoveType;
import enums.SoldierType;
import models.building.Building;
import models.Dictionary;

import java.util.ArrayList;

public class WallBreaker extends Soldier {
    int numberOfUpgrades;

    @Override
    public ArrayList<Building> findPossibleAims() {
        ArrayList<Building> possibleAims = new ArrayList<>();
        ArrayList<Building> ordinaryBuildings = new ArrayList<>();
        for (Building building:
                map.values()) {
            if(!building.isDestroyed()) {
                if (building.getType() == BuildingType.WALL)
                    possibleAims.add(building);
                else
                    ordinaryBuildings.add(building);
            }
        }
        if (possibleAims.size() != 0) {
            backToWallBreaker();
            return possibleAims;
        }
        actAsGaurdian();
        return ordinaryBuildings;
    }

    public int attack() {
        if(aim.getType() == BuildingType.WALL)
            return strength;
        return Dictionary.getInstance().getConst(DictionaryIndexType.GAURDIAN_STRENGTH);
    }

    public void actAsGaurdian() {
        strength = Dictionary.getInstance().getConst(DictionaryIndexType.GAURDIAN_STRENGTH) + numberOfUpgrades;
        speed = Dictionary.getInstance().getConst(DictionaryIndexType.GAURDIAN_SPEED);
        attackRadius = Dictionary.getInstance().getConst(DictionaryIndexType.GAURDIAN_ATTACK_RADIUS);
    }

    public void backToWallBreaker() {
        strength = Dictionary.getInstance().getConst(DictionaryIndexType.WALL_BREAKER_STRENGTH) + numberOfUpgrades;
        speed = Dictionary.getInstance().getConst(DictionaryIndexType.WALL_BREAKER_SPEED);
        attackRadius = Dictionary.getInstance().getConst(DictionaryIndexType.WALL_BREAKER_ATTACK_RADIUS);
    }

    public WallBreaker(int numberOfUpgrades) {
        this.numberOfUpgrades = numberOfUpgrades;
        buildCost = Dictionary.getInstance().getConst(DictionaryIndexType.WALL_BREAKER_BUILDING_COST);
        buildTime = Dictionary.getInstance().getConst(DictionaryIndexType.WALL_BREAKER_BUILDING_TIME);
        type = SoldierType.WALLBREAKER;
        moveType = SoldierMoveType.GROUND;
        fullHealth = Dictionary.getInstance().getConst(DictionaryIndexType.WALL_BREAKER_HEALTH);
        primitiveDataSetter(numberOfUpgrades);
        backToWallBreaker();
    }

    public int getSpeed() {
        if(aim.getType() == BuildingType.WALL)
            return speed;
        return Dictionary.getInstance().getConst(DictionaryIndexType.GAURDIAN_SPEED);
    }

}
