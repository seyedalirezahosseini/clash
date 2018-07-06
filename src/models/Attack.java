package models;


import enums.BuildingType;
import models.building.Building;
import models.building.defence.DefenceBuilding;
import models.livingBeing.Attack.Soldier;

import java.util.ArrayList;
import java.util.HashMap;

public class Attack {
    private Village enemy;
    private HashMap<Building, Resource> resources;
    private ArrayList<Soldier> soldiers;
    private Loot takenLoot;


    public Attack(ArrayList<Soldier> soldiers, Village enemy) {
        this.enemy = enemy;
        resources = new HashMap<>();
        takenLoot = new Loot(0, 0, 0);
        this.soldiers = soldiers;
    }


//    public void addScore(int score) {
//        village.addScore(score);
//    }

    public boolean cyclePassed() {
        //true return value in this state means the game ended in the cycle
        for (int i = 0; i < soldiers.size(); i++) {
            if (soldiers.get(i).isDead()) {
                soldiers.remove(i);
                --i;
                continue;
            }
            switch (soldiers.get(i).changeState()) {
                case ATTACKING:
                    soldiers.get(i).getAim().damage(soldiers.get(i).attack());
                    if (soldiers.get(i).getAim().isDestroyed()) {
                        if (soldiers.get(i).getAim().getLoot() != null)
                            takenLoot.addLoot(soldiers.get(i).getAim().getLoot());
                    }
                    break;
                case GAME_FINISHED:
                    return true;
            }
        }
        if (soldiers.size() <= 0)
            return true;
        for (DefenceBuilding building :
                enemy.getDefenceBuildings()) {
            if (building == null)
                continue;
            if (building.getType() == BuildingType.WALL)
                continue;
            building.attack(soldiers);
        }
        return false;

    }

    public Loot getTakenLoot() {
        return takenLoot;
    }

//    public void addResource(Building building, Resource resource) {
//        if(!resources.containsKey(building)) {
//            resources.put(building, resource);
//            return;
//        }
//        resources.get(building).add(resource.getElixir(), resource.getGold());
//    }
}