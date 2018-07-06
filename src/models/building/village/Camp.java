package models.building.village;

import enums.*;
import models.building.*;
import models.*;
import models.livingBeing.Attack.Soldier;

import java.util.ArrayList;
import java.util.HashMap;

import static enums.DictionaryIndexType.CAMP_BUILD_TIME;

public class Camp extends Building {
    private static final int MAX_CAPACITY = 50;
    private int capacity = MAX_CAPACITY;

    private ArrayList<Soldier> soldiers = new ArrayList<Soldier>();
    private static int numberOfBuildings = 0;
    public static int generateIDCode() {
        numberOfBuildings++;
        return numberOfBuildings;
    }

    public void setID() {
        this.ID = Camp.generateIDCode();

    }


    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }

    public Camp() {
        this.upgradeTime = this.timeToComplete = Dictionary.getInstance().getConst(CAMP_BUILD_TIME);
        type = BuildingType.CAMP;
        type = exactType = BuildingType.CAMP;
        this.strength = Dictionary.getInstance().getConst(DictionaryIndexType.CAMP_HEALTH);
        this.resourceNeedToBuild = Dictionary.getInstance().get(DictionaryIndexType.CAMP_BUILD);
        this.gainLoot = (Loot) Dictionary.getInstance().get(DictionaryIndexType.CAMP_LOOT);
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNumberOfSoldiers() {
        return soldiers.size();
    }


    @Override
    public void destroy() {
        super.destroy();
        soldiers = new ArrayList<Soldier>();
    }

    public void addSoldier(Soldier soldier) {
        soldiers.add(soldier);
    }


    public ArrayList<Soldier> sendSoldierToAttack(HashMap<SoldierType, Integer> wanted) {
        ArrayList<Soldier> sentSoldiers = new ArrayList<>();
        for (int i = 0; i < this.soldiers.size(); i++) {
            SoldierType type = soldiers.get(i).getType();
            if (wanted.containsKey(type) && wanted.get(type) > 0) {
                wanted.replace(type, wanted.get(type), wanted.get(type) - 1);
                sentSoldiers.add(soldiers.get(i));
                soldiers.remove(i);
                i--;
            }
        }
        return sentSoldiers;
    }

    public Resource getResourceNeedToUpgrade() {
        return new Resource(0, 0);
    }

}
