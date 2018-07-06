package models.livingBeing.Attack;

import enums.DictionaryIndexType;
import enums.SoldierMoveType;
import enums.SoldierState;
import enums.SoldierType;
import models.Coordinate;
import models.Dictionary;
import models.PathFinder;
import models.building.Building;
import models.interfaces.VisibleEntity;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Soldier implements VisibleEntity {
    protected SoldierType type;
    protected int buildTime;
    protected int buildCost;
    protected int timeToBuild;
    protected int strength;

    protected int fullHealth;
    protected int leftHealth;
    protected int attackRadius;
    protected int speed;
    protected int limit;
    protected int level;
    private boolean set = false;
    protected Coordinate coordinate;
    protected SoldierMoveType moveType;
    protected PathFinder pathfinder;
    protected Building aim;
    protected ArrayList<Building> wallsInWay;
    protected HashMap<Coordinate, Building> map;
    protected ArrayList<Coordinate> cycleMove;
    public Coordinate getCoordinate() {
        return coordinate;
    }

    public int getFullHealth() {
        return fullHealth;
    }

    public boolean isFullOrDead() {
        if (isDead())
            return true;
        else if (leftHealth >= fullHealth) {
            leftHealth = fullHealth;
            return true;
        }
        return false;
    }

    public int attack() {
        return strength;
    }

    public int getTimeToBuild() {
        return timeToBuild;
    }

    public void setTimeToBuild(int timeToBuild) {
        this.timeToBuild = timeToBuild;
    }

    public int getBuildTime() {
        return buildTime;
    }

    public void reduceTimeToBuild() {
        --timeToBuild;
    }

    public int getBuildCost() {
        return buildCost;
    }

    public Building getAim() {
        return aim;
    }

    public SoldierType getType() {
        return type;
    }

    public SoldierMoveType getMoveType() {
        return moveType;
    }

    protected void primitiveDataSetter(int numberOfUpgrades) {
        limit = Dictionary.getInstance().getConst(DictionaryIndexType.MAP_MAX_COORDINATE);
        strength += numberOfUpgrades;
        fullHealth += 5 * numberOfUpgrades;
        timeToBuild = buildTime;
        buildTime -= numberOfUpgrades;
        level = numberOfUpgrades;
        aim = new Building();
    }

    public void setMapAndLocation(HashMap<Coordinate, Building> map, Coordinate coordination) {
        set = true;
        this.map = new HashMap<>();
        this.map.putAll(map);
        wallsInWay = new ArrayList<>();
        coordinate = new Coordinate(coordination.getX(), coordination.getY());
        leftHealth = fullHealth;
        pathfinder = new PathFinder(attackRadius);
    }

    public boolean getSet() {
        return set;
    }

    public abstract ArrayList<Building> findPossibleAims();

    public boolean findClosestAim(ArrayList<Building> possibleAims) {
        aim = new Building();
        pathfinder = new PathFinder(attackRadius);
        int minDistance = Integer.MAX_VALUE;
        if (possibleAims.size() < 1)
            return false;
        for (int i = 0; i < possibleAims.size(); i++) {
            int distance = PathFinder.distanceCalculator(possibleAims.get(i).getCoordinate(), coordinate);
            if (distance < minDistance) {
                minDistance = distance;
                aim = possibleAims.get(i);
            }
        }
        return true;
    }

    public void beingHealed(int heal) {
        leftHealth += heal;
        if (leftHealth > fullHealth) {
            leftHealth = fullHealth;
        }
    }

    public void pathSetter() {
        pathfinder.setData(aim.getCoordinate().toInt(), map, limit, coordinate.toInt(), moveType);
        pathfinder.findPath();
        wallsInWay = pathfinder.getInWay();
    }

    public boolean isAimDead() {
        if (aim.isDestroyed())
            return true;
        return false;
    }

    public SoldierState changeState() {
        if (aim == null || isAimDead() || aim.getCoordinate() == null) {
            findClosestAim(findPossibleAims());
            if (aim == null) {
                return SoldierState.GAME_FINISHED;
            }
            pathSetter();
        }
        else if (checkInWayWalls()) {
            pathSetter();
        }
        cycleMove = new ArrayList<>();
        for (int i = 0; i < this.getSpeed(); i++) {
            cycleMove.add(pathfinder.getNextStep());
        }
        if (cycleMove.get(cycleMove.size() - 1) != null)
            coordinate = new Coordinate(cycleMove.get(cycleMove.size() - 1).getX(), cycleMove.get(cycleMove.size() - 1).getY());
        else
            return SoldierState.ATTACKING;
        return SoldierState.MOVING;
    }

    public ArrayList<Coordinate> getCycleMove() {
        return cycleMove;
    }

    public boolean isDead() {
        if (leftHealth <= 0)
            return true;
        return false;
    }

    public boolean checkInWayWalls() {
        if (wallsInWay == null)
            return false;
        for (int i = 0; i < wallsInWay.size(); i++) {
            if (wallsInWay.get(i).isDestroyed())
                return true;
        }
        return false;
    }

    public void decreaseHealth(int amount) {
        leftHealth -= amount;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStrength() {
        return strength;
    }

    public int getLeftHealth() {
        return leftHealth;
    }

    public int getDamage() {
        return fullHealth - leftHealth;
    }

    public int getLevel() {
        return level;
    }

    public void setMap(HashMap<Coordinate, Building> map) {
        this.map = map;
    }
}