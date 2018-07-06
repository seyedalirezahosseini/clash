package models.livingBeing.Attack;

import enums.DictionaryIndexType;
import enums.SoldierMoveType;
import enums.SoldierState;
import enums.SoldierType;
import models.Coordinate;
import models.Dictionary;
import models.PathFinder;
import models.building.Building;

import java.nio.file.Path;
import java.util.ArrayList;

public class Healer extends Soldier{
    ArrayList<Soldier> soldiers = new ArrayList<>();
    private int lifeTime;
    private int timePassed;
    private SoldierType type;
    private Soldier soldierAim;

    public Healer(int numberOfUpgrades) {
        lifeTime = 10;
        timePassed = 0;
        buildCost = Dictionary.getInstance().getConst(DictionaryIndexType.HEALER_BUILDING_COST);
        buildTime = Dictionary.getInstance().getConst(DictionaryIndexType.HEALER_BUILDING_TIME);
        strength = Dictionary.getInstance().getConst(DictionaryIndexType.HEALER_STRENGTH) + numberOfUpgrades;
        speed = Dictionary.getInstance().getConst(DictionaryIndexType.HEALER_SPEED);
        type = SoldierType.HEALER;
        moveType = SoldierMoveType.AIR;
        attackRadius = Dictionary.getInstance().getConst(DictionaryIndexType.HEALER_HEAL_RADIUS);
        limit = Dictionary.getInstance().getConst(DictionaryIndexType.MAP_MAX_COORDINATE);
        level = numberOfUpgrades;
    }

    public void setSoldiers(ArrayList<Soldier> soldiers) {
        this.soldiers = soldiers;
    }

    private Soldier findClosestInjuredSoldier(ArrayList<Soldier> injured) {
        Soldier aim;
        if (injured.size() != 0) {
            int min = 10000;
            aim = soldiers.get(0);
            for (Soldier soldier :
                    injured) {
                int distance = PathFinder.distanceCalculator(soldier.getCoordinate(), coordinate);
                if (distance < min) {
                    min =  distance;
                    aim = soldier;
                }
            }
            return aim;
        }
        else {
            Soldier randomAim = null;
            for (Soldier soldier:
                 soldiers) {
                if (!soldier.isDead() && soldier.getType() != SoldierType.HEALER) {
                    randomAim = soldier;
                    if (PathFinder.distanceCalculator(soldier.getCoordinate(), coordinate) / speed
                            <= lifeTime - timePassed) {
                        return soldier;
                    }
                }
            }
            return randomAim;
        }
    }

    public boolean isInHealingRange(Soldier soldier) {
        if (PathFinder.distanceCalculator(soldier.getCoordinate(), coordinate) <= attackRadius)
            return true;
        return false;
    }

    public SoldierType getType() {
        return type;
    }

    @Override
    public ArrayList<Building> findPossibleAims() {
        return null;
    }

    @Override
    public SoldierState changeState() {
        if (soldiers.size() == 0)
            return null;
        ArrayList<Soldier> injured = findInjuredForces();
        timePassed++;
        boolean closeEnough = false;
        for (Soldier soldier:
             injured) {
            if (isInHealingRange(soldier)) {
                closeEnough = true;
                soldier.beingHealed(strength);
            }
        }
        if (!closeEnough) {
            if (soldierAim == null || soldierAim.isFullOrDead()) {
                soldierAim = findClosestInjuredSoldier(injured);
                if (soldierAim == null)
                    return SoldierState.GAME_FINISHED;
                pathSetter();
            }
            cycleMove = new ArrayList<>();
            for (int i = 0; i < this.getSpeed(); i++) {
                cycleMove.add(pathfinder.getNextStep());
            }
            if (cycleMove.get(cycleMove.size() - 1) != null)
                coordinate = new Coordinate(cycleMove.get(cycleMove.size() - 1).getX(), cycleMove.get(cycleMove.size() - 1).getY());
            return SoldierState.MOVING;
        }
        else {
            return SoldierState.ATTACKING;
        }
    }

    public void pathSetter() {
        pathfinder.setData(soldierAim.getCoordinate().toInt(), map, limit, coordinate.toInt(), moveType);
        pathfinder.findPath();
        wallsInWay = new ArrayList<>();
    }


    public ArrayList<Soldier> findInjuredForces() {
        ArrayList<Soldier> injured = new ArrayList<>();
        for (Soldier soldier:
             soldiers) {
            if (soldier.type == SoldierType.HEALER)
                continue;
            if (soldier.isFullOrDead())
                continue;
            injured.add(soldier);
        }
        return injured;
    }



    public boolean isDead() {
        if(timePassed >= lifeTime)
            return true;
        return false;
    }

}
