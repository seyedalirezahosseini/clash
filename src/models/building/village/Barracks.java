package models.building.village;

import enums.*;
import models.building.Building;
import models.*;
import models.interfaces.Upgradeable;
import models.livingBeing.Attack.Soldier;
import models.Village;

import java.util.ArrayList;

import static enums.DictionaryIndexType.BARRACKS_BUILD_TIME;
import static enums.DictionaryIndexType.MAX_SOLDIER_BUILD_TIME;


public class Barracks extends Building implements Upgradeable {
    private ArrayList<Soldier> waitList = new ArrayList<Soldier>();
    private static final int MAX_BUILD_TIME = Dictionary.getInstance().getConst(MAX_SOLDIER_BUILD_TIME);
    public ArrayList<Soldier> getWaitList() {
        return waitList;
    }

    private static int numberOfBuildings = 0;
    public static int generateIDCode() {
        numberOfBuildings++;
        return numberOfBuildings;
    }

    public Barracks() {
        this.level++;
        this.upgradeTime = this.timeToComplete = Dictionary.getInstance().getConst(BARRACKS_BUILD_TIME);
        this.strength = Dictionary.getInstance().getConst(DictionaryIndexType.BARRACKS_HEALTH);
        this.resourceNeedToBuild = Dictionary.getInstance().get(DictionaryIndexType.BARRACKS_BUILD);
        this.gainLoot = (Loot) Dictionary.getInstance().get(DictionaryIndexType.BARRACKS_LOOT);
        exactType = type = BuildingType.BARRACKS;
        this.maxStrength = this.strength;

    }

    public void addSoldier(Soldier soldier){
        if(soldier.getTimeToBuild() - level <= 0)
            soldier.setTimeToBuild(0);
        else
            soldier.setTimeToBuild(soldier.getTimeToBuild() - level);
        waitList.add(soldier);

    }
    public void moveSoldierToCamp(Camp camp) {
        camp.addSoldier(waitList.get(0));
        waitList.remove(0);
    }

    @Override
    public void upgrade() {
        if(level != MAX_BUILD_TIME)
            this.level++;
        //impelement this in add soldier to wait list

    }


    public void setID() {
        this.ID = Barracks.generateIDCode();

    }

    @Override
    public int getUpgradeTime() {
        return upgradeTime;
    }

    public void passDeltaT(Village village) {
        if(waitList.size() == 0)
            return;
        if(village.getCamps().size() == 0) {
            return;
        }
        waitList.get(0).setTimeToBuild(waitList.get(0).getTimeToBuild()-1);
        if (waitList.get(0).getTimeToBuild() == 0) {
            for (Camp camp ://perhaps we have bug in pass soldiers to camp because of attack and empty camp
                    village.getCamps()) {
                if (camp.getCapacity() > camp.getNumberOfSoldiers()) {
                    moveSoldierToCamp(camp);
                    break;
                }
            }
        }
    }

    @Override
    public Resource getResourceNeedToUpgrade() {
        return new Resource(100, 0);
    }
}
