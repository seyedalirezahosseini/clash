package models.building.village;

import exceptions.InvalidValueException;
import exceptions.NotEnoughCapacityException;
import exceptions.NotEnoughResourceException;
import models.Resource;
import models.building.Building;
import models.interfaces.Upgradeable;

public class Storage extends Building implements Upgradeable {
    private static final double UPGRADE_PERCENT = 0.6;
    protected int capacity;
    protected Resource resource = new Resource(0, 0);

    private static int numberOfBuildings = 0;

    public static int generateIDCode() {
        numberOfBuildings++;
        return numberOfBuildings;
    }


    public Resource getResource() {
        return resource;
    }

    @Override
    public void upgrade() {
        this.level++;
        capacity += capacity * UPGRADE_PERCENT;
    }

    @Override
    public int getUpgradeTime() {
        return 0;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void increaseCapacity(int capacity) {
        this.capacity += capacity;
    }

    public void decreaseCapacity(int capacity) {
        this.capacity -= capacity;
    }

    public void addResource(int value) throws NotEnoughCapacityException, InvalidValueException{}

    public void spendResource(int value) throws NotEnoughResourceException, InvalidValueException{}

    public Resource getResourceNeedToUpgrade() {
        return new Resource(100, 0);
    }
}
