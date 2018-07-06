package models.building.village;

import models.Resource;
import models.Village;
import models.building.Building;
import models.interfaces.Upgradeable;

public class Mine extends Building implements Upgradeable {
    protected int efficiency;
    protected int storage = 0;

    private static int numberOfBuildings = 0;

    public static int generateIDCode() {
        numberOfBuildings++;
        return numberOfBuildings;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    @Override
    public void upgrade() {
        this.level++;
    }

    @Override
    public int getUpgradeTime() {
        return 0;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    public Resource getResourceNeedToUpgrade() {
        return new Resource(100, 0);
    }

    public void passDeltaT(Village village) throws Exception{}


}
