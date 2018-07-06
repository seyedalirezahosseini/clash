package models.building.defence;

import enums.DictionaryIndexType;
import enums.SoldierMoveType;
import models.Dictionary;
import models.building.Building;
import models.interfaces.Upgradeable;
import models.livingBeing.Attack.Soldier;

import java.util.ArrayList;

public abstract class DefenceBuilding extends Building implements Upgradeable {
    private static final int ATTACK_POWER_INCREASED_PER_LEVEL = Dictionary.getInstance().getConst(DictionaryIndexType.ATTACK_POWER_INCREASED_PER_LEVEL);
    private static final int STRENGTH_INCREASE_PER_LEVEL = Dictionary.getInstance().getConst(DictionaryIndexType.STRENGTH_INCREASE_PER_LEVEL);
    protected int attackPower;

    private static int numberOfBuildings = 0;

    public static int generateIDCode() {
        numberOfBuildings++;
        return numberOfBuildings;
    }

    public Soldier getTarget() {
        return null;
    }

    @Override
    public void upgrade() {
        this.attackPower += ATTACK_POWER_INCREASED_PER_LEVEL;
        super.strength += STRENGTH_INCREASE_PER_LEVEL;
        level++;
    }

    @Override
    public int getUpgradeTime() {
        return 0;
    }

    public abstract void attack(ArrayList<Soldier> soldiers);

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getAttackRadius() {
        return 0;
    }

    public SoldierMoveType getSoldiersMoveType() {
        return null;
    }

    public int getDamage() {
        return getAttackPower();
    }
}