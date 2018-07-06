package enums;

import java.util.HashMap;

public enum SoldierType {
    DRAGON("DRAGON"), HEALER("HEALER"), GIANT("GIANT"), GAURDIAN("GAURDIAN"), WALLBREAKER("WALLBREAKER"), ARCHER("ARCHER");

    private String name;


    SoldierType(String name) {
        this.name = name;
    }

    public static SoldierType getByString(String name) {
        for (SoldierType type:
             SoldierType.values()) {
            if (name.equals(type.name))
                return type;
        }
        return null;
    }


}
