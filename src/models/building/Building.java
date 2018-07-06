package models.building;

import enums.BuildingType;
import exceptions.CoordinateOutOfBondException;
import models.Coordinate;
import models.Loot;
import models.Resource;
import models.interfaces.VisibleEntity;

public class Building implements VisibleEntity {
    protected static int jsonCode;
    protected int strength;
    protected int level = 0;
    protected Coordinate coordinate;
    protected Resource resourceNeedToBuild;
    protected Resource resourceNeedToUpgrade;
    protected Loot gainLoot;
    protected boolean isDestroyed = false;
    protected boolean isCompleted = false;
    protected boolean canUpgrade = true;
    protected boolean hasLoot = true;
    protected int ID;
    protected int upgradeTime;
    protected int timeToComplete;
    protected BuildingType type;
    protected BuildingType exactType;

    public int getTimeToComplete() {
        return timeToComplete;
    }

    public BuildingType getExactType() {
        return exactType;
        
    }

    public void setTimeToComplete(int timeToComplete) {
        this.timeToComplete = timeToComplete;
    }

    public BuildingType getType() {
        return type;
    }

    public int getID() {
        return ID;
    }

    public boolean CanUpgrade() {
        return canUpgrade;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void destroy() {
        isDestroyed = true;
    }

    public Resource getResourceNeedToBuild() {
        return resourceNeedToBuild;
    }

    public void setResourceNeedToBuild(Resource resourceNeedToBuild) {
        this.resourceNeedToBuild = resourceNeedToBuild;
    }

    public Resource getResourceNeedToUpgrade() {
        return resourceNeedToUpgrade;
    }

    public void setResourceNeedToUpgrade(Resource resourceNeedToUpgrade) {
        this.resourceNeedToUpgrade = resourceNeedToUpgrade;
    }

    public Loot getLoot() {
        if (hasLoot == true) {
            hasLoot = false;
            return gainLoot;
        }
        return null;
    }

    public void setGainLoot(Loot gainLoot) {
        this.gainLoot = gainLoot;
    }

    public void damage(int x) {
        strength = strength - x;
        if (strength < 0)
            isDestroyed = true;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(int x, int y) throws CoordinateOutOfBondException {
        // TODO: 5/8/2018 fix later
//        if ((x == 0) || (y == 0)) {
//            coordinate.setX(15);
//            coordinate.setY(15);
//        }
//        coordinate.setX(x);
//        coordinate.setY(y);
        coordinate = new Coordinate(x, y);
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getJsonCode() {
        return jsonCode;
    }

    public void setJsonCode(int jsonCode) {
        this.jsonCode = jsonCode;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                " " + ID;
    }

    public void setID() {
    }

    public void upgrade() {
        // TODO: 5/8/2018  
    }
}
