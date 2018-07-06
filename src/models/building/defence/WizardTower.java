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

public class WizardTower extends DefenceBuilding {
    private static final int INITIAL_ATTACK_POWER = Dictionary.getInstance().getConst(DictionaryIndexType.WIZARD_TOWER_INITIAL_ATTACK_POWER);
    private static final int ATTACK_RADIUS = Dictionary.getInstance().getConst(DictionaryIndexType.WIZARD_TOWER_INITIAL_ATTACK_RADIUS);
    private static final int IMPACT_RADIUS = Dictionary.getInstance().getConst(DictionaryIndexType.WIZARD_TOWER_IMPACT_RADIUS);
    private static final AttackType ATTACK_TYPE = AttackType.BY_CELL;
    private static final SoldierMoveType SOLDIER_MOVE_TYPE = SoldierMoveType.GROUND_AND_AIR;
    private static final int UPGRADE_TIME = Dictionary.getInstance().getConst(DictionaryIndexType.WIZARD_TOWER_UPGRADE_TIME);
    private Soldier aliveTarget = null;

    public WizardTower() {
        super.setAttackPower(INITIAL_ATTACK_POWER);
        type = BuildingType.DEFENSIVE_TOWER;
        exactType = BuildingType.WIZARD_TOWER;
        this.timeToComplete = Dictionary.getInstance().getConst(DictionaryIndexType.WIZARD_TOWER_BUILDING_TIME);
        this.strength = Dictionary.getInstance().getConst(DictionaryIndexType.WIZARD_TOWER_STRENGTH);
        this.resourceNeedToBuild = Dictionary.getInstance().get(DictionaryIndexType.WIZARD_TOWER_BUILD);
        this.resourceNeedToUpgrade = Dictionary.getInstance().get(DictionaryIndexType.WIZARD_TOWER_BUILD);
        this.gainLoot = (Loot) Dictionary.getInstance().get(DictionaryIndexType.WIZARD_TOWER_LOOT);
        this.ID = WizardTower.generateIDCode();
        this.maxStrength = this.strength;

    }

    @Override
    public SoldierMoveType getSoldiersMoveType() {
        return SOLDIER_MOVE_TYPE;
    }

    @Override
    public Soldier getTarget() {
        return aliveTarget;
    }

    private static int numberOfBuildings = 0;

    public static int generateIDCode() {
        numberOfBuildings++;
        return numberOfBuildings;
    }

    public int getAttackRadius() {
        return ATTACK_RADIUS;
    }

    public static SoldierMoveType getSoldierMoveType() {
        return SOLDIER_MOVE_TYPE;
    }

    {
        type = BuildingType.DEFENSIVE_TOWER;
        exactType = BuildingType.WIZARD_TOWER;
    }

    @Override
    public int getUpgradeTime() {
        return UPGRADE_TIME;
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
                if (getDistanceFromTarget(Soldier) <= IMPACT_RADIUS) {
                    Soldier.decreaseHealth(attackPower);
                }
            }
        } catch (NoAvailableTargetException ignored) {
        }
    }

    //when finding a Soldier, this method attacks it until it's dead or out of range
    private void setAliveTarget(ArrayList<Soldier> Soldiers) {
        try {
            aliveTarget = findPriorityTarget(Soldiers);
        } catch (NoAvailableTargetException e) {
            aliveTarget = null;
        }
    }

    private Soldier findPriorityTarget(ArrayList<Soldier> Soldiers) throws NoAvailableTargetException {
        double minDistance = ATTACK_RADIUS;
        double distance;
        Soldier priorityTarget = null;
        for (Soldier Soldier : Soldiers) {
            distance = getDistanceFromTarget(Soldier);
            if (distance <= minDistance) {
                minDistance = distance;
                priorityTarget = Soldier;
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


}
