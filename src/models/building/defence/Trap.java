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

public class Trap extends DefenceBuilding {
    private static int INITIAL_ATTACK_POWER;
    private static int ATTACK_RADIUS;
    private static int UPGRADE_TIME;
    private static AttackType ATTACK_TYPE;
    private static SoldierMoveType SOLDIER_MOVE_TYPE;
    private static int numberOfInstances = 0;
    private Soldier target = null;

    static {
        INITIAL_ATTACK_POWER = Dictionary.getInstance().getConst(DictionaryIndexType.TRAP_INITIAL_ATTACK_POWER);
        ATTACK_RADIUS = Dictionary.getInstance().getConst(DictionaryIndexType.TRAP_ATTACK_RADIUS);
        AttackType ATTACK_TYPE = AttackType.BY_ENEMY;
        SoldierMoveType SOLDIER_MOVE_TYPE = SoldierMoveType.GROUND;
        UPGRADE_TIME = Dictionary.getInstance().getConst(DictionaryIndexType.TRAP_UPGRADE_TIME);
    } // initializing static values


    public Trap() {
        type = BuildingType.DEFENSIVE_TOWER;
        exactType = BuildingType.TRAP;
        attackPower = INITIAL_ATTACK_POWER;
        this.timeToComplete = Dictionary.getInstance().getConst(DictionaryIndexType.TRAP_BUILDING_TIME);
        this.resourceNeedToBuild = Dictionary.getInstance().get(DictionaryIndexType.TRAP_BUILD);
        this.gainLoot = (Loot) Dictionary.getInstance().get(DictionaryIndexType.TRAP_LOOT);
        this.resourceNeedToUpgrade = Dictionary.getInstance().get(DictionaryIndexType.TRAP_BUILD);
        this.strength = Dictionary.getInstance().getConst(DictionaryIndexType.TRAP_STRENGTH);
        super.setAttackPower(INITIAL_ATTACK_POWER);
        this.ID = ArcherTower.generateIDCode();
        this.maxStrength = this.strength;

    }

// Attack Methods

    public void attack(ArrayList<Soldier> Soldiers) {
        setTarget(Soldiers);
        if (target != null) {
            attackTarget();
            isDestroyed = true;
        }
    }

    private Soldier findPriorityTarget(ArrayList<Soldier> Soldiers) throws NoAvailableTargetException {
        double minDistance = ATTACK_RADIUS;
        double distance;
        Soldier priorityTarget = null;
        for (Soldier Soldier : Soldiers) {
            if (Soldier.getMoveType() == SOLDIER_MOVE_TYPE) {
                distance = getDistance(Soldier);
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

    private void attackTarget() {
        target.decreaseHealth(super.getAttackPower());
    }

    private boolean isInRange(Soldier aliveTarget) {
        return getDistance(aliveTarget) <= ATTACK_RADIUS;
    }

    private void setTarget(ArrayList<Soldier> Soldiers) {
        try {
            if (target == null) {
                target = findPriorityTarget(Soldiers);
            }
        } catch (NoAvailableTargetException e) {
            target = null;
        }
    }

    private double getDistance(Soldier Soldier) {
        int dx = Soldier.getCoordinate().getX() - this.coordinate.getX();
        int dy = Soldier.getCoordinate().getY() - this.coordinate.getY();
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)); //distance
    }

    public static int generateIDCode() {
        numberOfInstances++;
        return numberOfInstances;
    }
}
