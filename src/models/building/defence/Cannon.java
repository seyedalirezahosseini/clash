package models.building.defence;

import enums.AttackType;
import enums.BuildingType;
import enums.DictionaryIndexType;
import enums.SoldierMoveType;
import exceptions.NoAvailableTargetException;
import models.Dictionary;
import models.Loot;
import models.livingBeing.Attack.Soldier;

import java.util.ArrayList;

public class Cannon extends DefenceBuilding {
    private static int INITIAL_ATTACK_POWER;
    private static int ATTACK_RADIUS;
    private static int IMPACT_RADIUS;
    private static int numberOfBuildings;
    private static int UPGRADE_TIME;
    private static AttackType ATTACK_TYPE;
    private static SoldierMoveType SOLDIER_MOVE_TYPE;
    private Soldier aliveTarget = null;


    static {
        INITIAL_ATTACK_POWER = Dictionary.getInstance().getConst(DictionaryIndexType.CANNON_INITIAL_ATTACK_POWER);
        ATTACK_RADIUS = Dictionary.getInstance().getConst(DictionaryIndexType.CANNON_INITIAL_ATTACK_RADIUS);
        IMPACT_RADIUS = Dictionary.getInstance().getConst(DictionaryIndexType.CANNON_IMPACT_RADIUS);
        AttackType ATTACK_TYPE = AttackType.BY_CELL;
        SoldierMoveType SOLDIER_MOVE_TYPE = SoldierMoveType.GROUND;
        UPGRADE_TIME = Dictionary.getInstance().getConst(DictionaryIndexType.CANNON_UPGRADE_TIME);
        numberOfBuildings = 0;
    } //initializing Class Values

    public Cannon() {
        type = BuildingType.DEFENSIVE_TOWER;
        exactType = BuildingType.CANNON;
        this.timeToComplete = Dictionary.getInstance().getConst(DictionaryIndexType.CANNON_BUILDING_TIME);
        this.strength = Dictionary.getInstance().getConst(DictionaryIndexType.CANNON_STRENGTH);
        this.resourceNeedToBuild = Dictionary.getInstance().get(DictionaryIndexType.CANNON_BUILD);
        this.gainLoot = (Loot) Dictionary.getInstance().get(DictionaryIndexType.CANNON_LOOT);
        this.resourceNeedToUpgrade = Dictionary.getInstance().get(DictionaryIndexType.CANNON_BUILD);
        this.ID = Cannon.generateIDCode();
        this.maxStrength = this.strength;

        super.setAttackPower(INITIAL_ATTACK_POWER);
    }

    public static int generateIDCode() {
        numberOfBuildings++;
        return numberOfBuildings;
    }

    public void attack(ArrayList<Soldier> Soldiers) {
        setAliveTarget(Soldiers);
        if (aliveTarget != null) {
            attackCell(Soldiers);
        }
    }

    private void attackCell(ArrayList<Soldier> Soldiers) {
        try {
            for (Soldier Soldier : Soldiers) {
                if (getDistanceFromTarget(Soldier) <= IMPACT_RADIUS && Soldier.getMoveType() == SoldierMoveType.GROUND) {
                    Soldier.decreaseHealth(attackPower);
                }
            }
        } catch (NoAvailableTargetException ignored) {
        }
    }

    //when finding a Soldier, this method attacks it until it's dead or out of range

    private Soldier findPriorityTarget(ArrayList<Soldier> Soldiers) throws NoAvailableTargetException {

        double minDistance = ATTACK_RADIUS;
        double distance;
        Soldier priorityTarget = null;
        for (Soldier Soldier : Soldiers) {
            if (Soldier.getMoveType() == SOLDIER_MOVE_TYPE) {
                distance = getDistanceFromTarget(Soldier);
                if (distance <= minDistance) {
                    minDistance = distance;
                    priorityTarget = Soldier;
                }
            }
        }
        if (priorityTarget == null) {
            throw new NoAvailableTargetException();
        } else {
            return priorityTarget;
        }
    }


    private double getDistanceFromTarget(Soldier Soldier) throws NoAvailableTargetException {
        if (aliveTarget == null) {
            throw new NoAvailableTargetException();
        }
        int dx = Soldier.getCoordinate().getX() - aliveTarget.getCoordinate().getX();
        int dy = Soldier.getCoordinate().getY() - aliveTarget.getCoordinate().getY();
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)); //distance
    }

    private void setAliveTarget(ArrayList<Soldier> Soldiers) {
        try {
            aliveTarget = findPriorityTarget(Soldiers);
        } catch (NoAvailableTargetException e) {
            aliveTarget = null;
        }
    }

    @Override
    public SoldierMoveType getSoldiersMoveType() {
        return SOLDIER_MOVE_TYPE;
    }

    @Override
    public Soldier getTarget() {
        return aliveTarget;
    }

    public int getAttackRadius() {
        return ATTACK_RADIUS;
    }

    public static SoldierMoveType getSoldierMoveType() {
        return SOLDIER_MOVE_TYPE;
    }

    @Override
    public int getUpgradeTime() {
        return UPGRADE_TIME;
    }
}
