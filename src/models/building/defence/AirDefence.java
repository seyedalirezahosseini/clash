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

public class AirDefence extends DefenceBuilding {
    private static int INITIAL_ATTACK_POWER;
    private static int ATTACK_RADIUS;
    private static int UPGRADE_TIME;
    private static AttackType ATTACK_TYPE;
    private static SoldierMoveType SOLDIER_MOVE_TYPE;

    private static int numberOfInstances = 0;
    private Soldier target = null;

    static {
        INITIAL_ATTACK_POWER = Dictionary.getInstance().getConst(DictionaryIndexType.AIR_DEFENCE_INITIAL_ATTACK_POWER);
        ATTACK_RADIUS = Dictionary.getInstance().getConst(DictionaryIndexType.AIR_DEFENCE_ATTACK_RADIUS);
        AttackType ATTACK_TYPE = AttackType.BY_ENEMY;
        SoldierMoveType SOLDIER_MOVE_TYPE = SoldierMoveType.AIR;
        UPGRADE_TIME = Dictionary.getInstance().getConst(DictionaryIndexType.AIR_DEFENCE_UPGRADE_TIME);
    } // initializing static values

    public AirDefence() {
        this.attackPower = INITIAL_ATTACK_POWER;
        type = BuildingType.DEFENSIVE_TOWER;
        exactType = BuildingType.AIR_DEFENSE;
        this.timeToComplete = Dictionary.getInstance().getConst(DictionaryIndexType.AIR_DEFENCE_BUILDING_TIME);
        this.strength = Dictionary.getInstance().getConst(DictionaryIndexType.AIR_DEFENCE_STRENGTH);
        this.resourceNeedToBuild = Dictionary.getInstance().get(DictionaryIndexType.AIR_DEFENCE_BUILD);
        this.gainLoot = (Loot) Dictionary.getInstance().get(DictionaryIndexType.AIR_DEFENCE_LOOT);
        this.resourceNeedToUpgrade = Dictionary.getInstance().get(DictionaryIndexType.AIR_DEFENCE_BUILD);
        super.setAttackPower(INITIAL_ATTACK_POWER);
        this.ID = AirDefence.generateIDCode();
    }

    public static int generateIDCode() {
        numberOfInstances += 1;
        return numberOfInstances;
    }

    // Attack Methods

    //when finding a Soldier, this method attacks it until it's dead or out of range
    public void attack(ArrayList<Soldier> Soldiers) {
        setTarget(Soldiers);
        if (target != null) {
            attackTarget();
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
        // TODO: 4/29/2018 decreaseHealth method should check being dead or alive @kimia
    }

    private boolean isInRange(Soldier aliveTarget) {
        return getDistance(aliveTarget) <= ATTACK_RADIUS;
    }

    private double getDistance(Soldier Soldier) {
        int dx = Soldier.getCoordinate().getX() - this.coordinate.getX();
        int dy = Soldier.getCoordinate().getY() - this.coordinate.getY();
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)); //distance
    }


    // Getter and Setter

    private void setTarget(ArrayList<Soldier> Soldiers) {
        try {
            if (target == null) {
                target = findPriorityTarget(Soldiers);
            } else if (target.isDead() || !isInRange(target)) {
                target = findPriorityTarget(Soldiers);
            }
        } catch (NoAvailableTargetException e) {
            target = null;
        }
    }

    public int getAttackRadius() {
        return ATTACK_RADIUS;
    }

    public static SoldierMoveType getSoldierMoveType() {
        return SOLDIER_MOVE_TYPE;
    }

    @Override
    public SoldierMoveType getSoldiersMoveType() {
        return SOLDIER_MOVE_TYPE;
    }

    @Override
    public Soldier getTarget() {
        return target;
    }

    @Override
    public int getUpgradeTime() {
        return UPGRADE_TIME;
    }
}
