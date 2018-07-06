package models;

import models.building.Building;
import models.interfaces.Upgradeable;

public class Worker {
    private Building building;
    private int timeToComplete;
    private boolean free = true;
    private boolean isUpgrade = false;

    public Worker() {
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public void Work(Upgradeable building, int timeToComplete) {
        this.building = (Building) building;
        this.timeToComplete = timeToComplete;
        this.building.setTimeToComplete(timeToComplete);
        free = false;
        isUpgrade = true;
    }
    public void Work(Building building) {
        this.building = (Building) building;
        this.timeToComplete = building.getTimeToComplete();
        this.building.setTimeToComplete(timeToComplete);
        free = false;
        isUpgrade = false;
    }

    public void passDeltaT(Village village) {
        if(building == null)
            return;
        if(timeToComplete > 0)
            timeToComplete--;
        building.setTimeToComplete(timeToComplete);
        if (timeToComplete == 0) {
            free = true;
            building.setCompleted(true);
            if(isUpgrade) {
                Upgradeable upgradeable = (Upgradeable) building;
                upgradeable.upgrade();
            }
            building = null;
            village.upgradeFromList();

        }
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public int getTimeToComplete() {
        return timeToComplete;
    }

    public void setTimeToComplete(int timeToComplete) {
        this.timeToComplete = timeToComplete;
    }
}
