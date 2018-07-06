package models;
import enums.DictionaryIndexType;
import exceptions.*;

import java.util.Objects;

public class Coordinate {
    private int x;
    private int y;
    private static int maxCoordinate = Dictionary.getInstance().getConst(DictionaryIndexType.MAP_MAX_COORDINATE) ;
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) throws CoordinateOutOfBondException {
        if (x > maxCoordinate) {
            throw new CoordinateOutOfBondException();
        }
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) throws CoordinateOutOfBondException {
        if (y > maxCoordinate) {
            throw new CoordinateOutOfBondException();
        }
        this.y = y;
    }

    public static Coordinate getCoordination(int convertedToInt) {
        return new Coordinate(convertedToInt / 1000, convertedToInt % 1000);
    }

    public int toInt() {
        return x * 1000 + y;
    }

    public static int toInt(int x, int y) {
        return x * 1000 + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}