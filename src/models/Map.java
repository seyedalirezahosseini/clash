package models;

import enums.DictionaryIndexType;
import models.building.Building;
import exceptions.*;
import models.building.village.TownHall;

import java.util.HashMap;

public class Map {
    private HashMap<Coordinate, Building> cells = new HashMap<>();
    private static final int MAX_COORDINATE = Dictionary.getInstance().getConst(DictionaryIndexType.MAP_MAX_COORDINATE);

    public Map() {
    }

    public void townHallInit(TownHall townHall) {
        cells.put(new Coordinate(15,15) , townHall);
        cells.put(new Coordinate(16,15) , townHall);
        cells.put(new Coordinate(15,16) , townHall);
        cells.put(new Coordinate(16,16) , townHall);
    }

    public static int getMaxCoordinate() {
        return MAX_COORDINATE;
    }

    public void addBuilding(Building building, Coordinate coordinate) throws InvalidCoordinateException, CoordinateOutOfBondException {
        if (isCellFull(coordinate) || !isNotSide(coordinate))
            throw new InvalidCoordinateException();
        cells.put(coordinate, building);
        building.setCoordinate(coordinate);
    }

    private boolean isNotSide(Coordinate coordinate) {
        return coordinate.getX() != 1 && coordinate.getY() != 1 &&
                coordinate.getX() != MAX_COORDINATE && coordinate.getY() != getMaxCoordinate();
    }

    public boolean isCellFull(Coordinate coordinate) {
        if (cells.get(coordinate) != null)
            return true;
        return false;
    }

    public Coordinate[] getKeyValues() {
        return cells.keySet().toArray(new Coordinate[cells.size()]);
    }

    public HashMap<Coordinate, Building> getCells() {
        return cells;
    }
}


