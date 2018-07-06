package models.livingBeing.Defence;

import models.Coordinate;
import models.interfaces.VisibleEntity;

public class Giant implements VisibleEntity {
    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public Coordinate getCoordinate() {
        return null;
    }
    //nothing yet we are mot supposed to initialize it yet
}
