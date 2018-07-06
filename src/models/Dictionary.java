package models;

import enums.DictionaryIndexType;

import java.util.HashMap;

import static enums.DictionaryIndexType.ELIXIR_MINE_VALUE_PER_DELTAT;
import static enums.DictionaryIndexType.GOLD_MINE_VALUE_PER_DELTAT;

public class Dictionary {
    private static Dictionary instance = new Dictionary();
    private static HashMap<DictionaryIndexType, Resource> resource = new HashMap<>();
    private static HashMap<DictionaryIndexType, Integer> consts = new HashMap<DictionaryIndexType, Integer>();

    public static Dictionary getInstance() {
        return instance;
    }

    public Resource get(DictionaryIndexType e) {
        if (e == null) {
            throw new NullPointerException();
        }
        return resource.get(e);
    }

    public int getConst(DictionaryIndexType e) {
        return consts.get(e);
    }

    public void set(DictionaryIndexType e, Resource r) {
        if (e == null || r == null) {
            throw new NullPointerException();
        }
        resource.put(e, r);
    }

    public void setConst(DictionaryIndexType e, int x) {
        consts.put(e, x);
    }

    public void replace(DictionaryIndexType e, int x) {
        consts.replace(e, x);
    }

    public void replace(DictionaryIndexType e, Resource r) {
        resource.replace(e, r);
    }

    public void initialize() {
        Dictionary dictionary = Dictionary.getInstance();
        dictionary.set(DictionaryIndexType.GOLD_MINE_BUILD, new Resource(150, 5));
        dictionary.set(DictionaryIndexType.GOLD_MINE_LOOT, new Loot(150, 5, 2));
        dictionary.setConst(DictionaryIndexType.GOLD_MINE_HEALTH, 300);
        dictionary.set(DictionaryIndexType.GOLD_MINE_BUILD, new Resource(150, 5));
        dictionary.set(DictionaryIndexType.GOLD_MINE_LOOT, new Loot(150, 5, 2));
        dictionary.setConst(DictionaryIndexType.GOLD_MINE_HEALTH, 300);
        dictionary.setConst(DictionaryIndexType.GOLD_MINE_BUILD_TIME, 200);
        dictionary.setConst(GOLD_MINE_VALUE_PER_DELTAT, 10);

        dictionary.set(DictionaryIndexType.ELIXIR_MINE_BUILD, new Resource(100, 3));
        dictionary.set(DictionaryIndexType.ELIXIR_MINE_LOOT, new Loot(100, 3, 2));
        dictionary.setConst(DictionaryIndexType.ELIXIR_MINE_HEALTH, 200);
        dictionary.set(DictionaryIndexType.ELIXIR_MINE_BUILD, new Resource(100, 3));
        dictionary.set(DictionaryIndexType.ELIXIR_MINE_LOOT, new Loot(100, 3, 2));
        dictionary.setConst(DictionaryIndexType.ELIXIR_MINE_HEALTH, 200);
        dictionary.setConst(DictionaryIndexType.ELIXIR_MINE_BUILD_TIME, 100);
        dictionary.setConst(ELIXIR_MINE_VALUE_PER_DELTAT, 5);

        dictionary.set(DictionaryIndexType.GOLD_STORAGE_BUILD, new Resource(200, 0));
        dictionary.set(DictionaryIndexType.GOLD_STORAGE_LOOT, new Loot(200, 0, 3));
        dictionary.setConst(DictionaryIndexType.GOLD_STPRAGE_HEALTH, 300);
        dictionary.set(DictionaryIndexType.GOLD_STORAGE_BUILD, new Resource(200, 0));
        dictionary.set(DictionaryIndexType.GOLD_STORAGE_LOOT, new Loot(200, 0, 3));
        dictionary.setConst(DictionaryIndexType.GOLD_STPRAGE_HEALTH, 300);
        dictionary.setConst(DictionaryIndexType.GOLD_STORAGE_BUILD_TIME, 200);


        dictionary.set(DictionaryIndexType.ELIXIR_STORAGE_BUILD, new Resource(200, 0));
        dictionary.set(DictionaryIndexType.ELIXIR_STORAGE_LOOT, new Loot(200, 0, 3));
        dictionary.setConst(DictionaryIndexType.ELIXIR_STORAGE_HEALTH, 300);
        dictionary.set(DictionaryIndexType.ELIXIR_STORAGE_BUILD, new Resource(200, 0));
        dictionary.set(DictionaryIndexType.ELIXIR_STORAGE_LOOT, new Loot(200, 0, 3));
        dictionary.setConst(DictionaryIndexType.ELIXIR_STORAGE_HEALTH, 300);
        dictionary.setConst(DictionaryIndexType.ELIXIR_STORAGE_BUILD_TIME, 100);

        dictionary.set(DictionaryIndexType.TOWNHALL_BUILD, new Resource(0, 0));
        dictionary.set(DictionaryIndexType.TOWNHALL_LOOT, new Loot(1000, 500, 8));
        dictionary.setConst(DictionaryIndexType.TOWNHALL_HEALTH, 1000);

        dictionary.set(DictionaryIndexType.BARRACKS_BUILD, new Resource(200, 0));
        dictionary.set(DictionaryIndexType.BARRACKS_LOOT, new Loot(200, 0, 1));
        dictionary.setConst(DictionaryIndexType.BARRACKS_HEALTH, 900);
        dictionary.set(DictionaryIndexType.BARRACKS_BUILD, new Resource(200, 0));
        dictionary.set(DictionaryIndexType.BARRACKS_LOOT, new Loot(200, 0, 1));
        dictionary.setConst(DictionaryIndexType.BARRACKS_HEALTH, 900);
        dictionary.setConst(DictionaryIndexType.BARRACKS_BUILD_TIME, 100);


        dictionary.set(DictionaryIndexType.GOLD_STORAGE_BUILD, new Resource(200, 0));
        dictionary.set(DictionaryIndexType.GOLD_STORAGE_LOOT, new Loot(200, 0, 1));
        dictionary.setConst(DictionaryIndexType.GOLD_STPRAGE_HEALTH, 300);

        dictionary.set(DictionaryIndexType.CAMP_BUILD, new Resource(200, 0));
        dictionary.set(DictionaryIndexType.CAMP_LOOT, new Loot(200, 0, 1));
        dictionary.setConst(DictionaryIndexType.CAMP_HEALTH, 300);
        dictionary.set(DictionaryIndexType.CAMP_BUILD, new Resource(200, 0));
        dictionary.set(DictionaryIndexType.CAMP_LOOT, new Loot(200, 0, 1));
        dictionary.setConst(DictionaryIndexType.CAMP_HEALTH, 300);
        dictionary.setConst(DictionaryIndexType.CAMP_BUILD_TIME, 100);


        dictionary.setConst(DictionaryIndexType.MAP_MAX_COORDINATE, 30);
        dictionary.setConst(DictionaryIndexType.MAX_SOLDIER_BUILD_TIME, 45);

        dictionary.setConst(DictionaryIndexType.GAURDIAN_ATTACK_RADIUS, 1);
        dictionary.setConst(DictionaryIndexType.GAURDIAN_HEALTH, 100);
        dictionary.setConst(DictionaryIndexType.GAURDIAN_SPEED, 2);
        dictionary.setConst(DictionaryIndexType.GAURDIAN_BUILDING_COST, 50);
        dictionary.setConst(DictionaryIndexType.GAURDIAN_BUILDING_TIME, 10);
        dictionary.setConst(DictionaryIndexType.GAURDIAN_STRENGTH, 10);

        dictionary.setConst(DictionaryIndexType.GIANT_ATTACK_RADIUS, 1);
        dictionary.setConst(DictionaryIndexType.GIANT_HEALTH, 500);
        dictionary.setConst(DictionaryIndexType.GIANT_SPEED, 3);
        dictionary.setConst(DictionaryIndexType.GIANT_BUILDING_COST, 125);
        dictionary.setConst(DictionaryIndexType.GIANT_BUILDING_TIME, 30);
        dictionary.setConst(DictionaryIndexType.GIANT_STRENGTH, 30);

        dictionary.setConst(DictionaryIndexType.DRAGON_ATTACK_RADIUS, 3);
        dictionary.setConst(DictionaryIndexType.DRAGON_HEALTH, 700);
        dictionary.setConst(DictionaryIndexType.DRAGON_SPEED, 6);
        dictionary.setConst(DictionaryIndexType.DRAGON_BUILDING_COST, 175);
        dictionary.setConst(DictionaryIndexType.DRAGON_BUILDING_TIME, 40);
        dictionary.setConst(DictionaryIndexType.DRAGON_STRENGTH, 30);

        dictionary.setConst(DictionaryIndexType.ARCHER_ATTACK_RADIUS, 10);
        dictionary.setConst(DictionaryIndexType.ARCHER_HEALTH, 100);
        dictionary.setConst(DictionaryIndexType.ARCHER_SPEED, 2);
        dictionary.setConst(DictionaryIndexType.ARCHER_BUILDING_COST, 60);
        dictionary.setConst(DictionaryIndexType.ARCHER_BUILDING_TIME, 10);
        dictionary.setConst(DictionaryIndexType.ARCHER_STRENGTH, 10);

        dictionary.setConst(DictionaryIndexType.WALL_BREAKER_ATTACK_RADIUS, 1);
        dictionary.setConst(DictionaryIndexType.WALL_BREAKER_HEALTH, 100);
        dictionary.setConst(DictionaryIndexType.WALL_BREAKER_SPEED, 6);
        dictionary.setConst(DictionaryIndexType.WALL_BREAKER_BUILDING_COST, 60);
        dictionary.setConst(DictionaryIndexType.WALL_BREAKER_BUILDING_TIME, 10);
        dictionary.setConst(DictionaryIndexType.WALL_BREAKER_STRENGTH, 50);

        dictionary.setConst(DictionaryIndexType.HEALER_BUILDING_COST, 175);
        dictionary.setConst(DictionaryIndexType.HEALER_BUILDING_TIME, 10);
        dictionary.setConst(DictionaryIndexType.HEALER_HEAL_RADIUS, 10);
        dictionary.setConst(DictionaryIndexType.HEALER_SPEED, 3);
        dictionary.setConst(DictionaryIndexType.HEALER_STRENGTH, 50);

        dictionary.setConst(DictionaryIndexType.MAP_MAX_COORDINATE, 30);

        dictionary.setConst(DictionaryIndexType.ATTACK_POWER_INCREASED_PER_LEVEL, 1);
        dictionary.setConst(DictionaryIndexType.STRENGTH_INCREASE_PER_LEVEL, 10);

        dictionary.setConst(DictionaryIndexType.AIR_DEFENCE_INITIAL_ATTACK_POWER, 20);
        dictionary.setConst(DictionaryIndexType.AIR_DEFENCE_UPGRADE_TIME, 60);
        dictionary.setConst(DictionaryIndexType.AIR_DEFENCE_ATTACK_RADIUS, 10);
        dictionary.setConst(DictionaryIndexType.AIR_DEFENCE_BUILDING_TIME, 60);
        dictionary.setConst(DictionaryIndexType.AIR_DEFENCE_STRENGTH, 300);
        dictionary.set(DictionaryIndexType.AIR_DEFENCE_LOOT, new Loot(300, 0, 3));
        dictionary.set(DictionaryIndexType.AIR_DEFENCE_BUILD, new Resource(300, 0));


        dictionary.setConst(DictionaryIndexType.ARCHER_TOWER_ATTACK_RADIUS, 10);
        dictionary.setConst(DictionaryIndexType.ARCHER_TOWER_INITIAL_ATTACK_POWER, 20);
        dictionary.setConst(DictionaryIndexType.ARCHER_TOWER_BUILDING_TIME, 60);
        dictionary.setConst(DictionaryIndexType.ARCHER_TOWER_UPGRADE_TIME, 60);
        dictionary.setConst(DictionaryIndexType.ARCHER_TOWER_STRENGTH, 300);
        dictionary.set(DictionaryIndexType.ARCHER_TOWER_LOOT, new Loot(300, 0, 3));
        dictionary.set(DictionaryIndexType.ARCHER_TOWER_BUILD, new Resource(300, 0));

        dictionary.setConst(DictionaryIndexType.CANNON_INITIAL_ATTACK_RADIUS, 13);
        dictionary.setConst(DictionaryIndexType.CANNON_INITIAL_ATTACK_POWER, 20);
        dictionary.setConst(DictionaryIndexType.CANNON_UPGRADE_TIME, 100);
        dictionary.setConst(DictionaryIndexType.CANNON_BUILDING_TIME, 100);
        dictionary.setConst(DictionaryIndexType.CANNON_IMPACT_RADIUS, 2);//sqrt(2) Actually
        dictionary.setConst(DictionaryIndexType.CANNON_STRENGTH, 400);
        dictionary.set(DictionaryIndexType.CANNON_LOOT, new Loot(400, 0, 4));
        dictionary.set(DictionaryIndexType.CANNON_BUILD, new Resource(400, 0));

        dictionary.setConst(DictionaryIndexType.WIZARD_TOWER_INITIAL_ATTACK_RADIUS, 13);
        dictionary.setConst(DictionaryIndexType.WIZARD_TOWER_INITIAL_ATTACK_POWER, 20);
        dictionary.setConst(DictionaryIndexType.WIZARD_TOWER_UPGRADE_TIME, 120);
        dictionary.setConst(DictionaryIndexType.WIZARD_TOWER_BUILDING_TIME, 120);
        dictionary.setConst(DictionaryIndexType.WIZARD_TOWER_IMPACT_RADIUS, 2);//sqrt(2) Actually
        dictionary.setConst(DictionaryIndexType.WIZARD_TOWER_STRENGTH, 700);
        dictionary.set(DictionaryIndexType.WIZARD_TOWER_LOOT, new Loot(500, 0, 5));
        dictionary.set(DictionaryIndexType.WIZARD_TOWER_BUILD, new Resource(500, 0));


        dictionary.setConst(DictionaryIndexType.CANNON_INITIAL_ATTACK_RADIUS, 13);
        dictionary.setConst(DictionaryIndexType.CANNON_INITIAL_ATTACK_POWER, 20);
        dictionary.setConst(DictionaryIndexType.CANNON_UPGRADE_TIME, 100);
        dictionary.setConst(DictionaryIndexType.CANNON_IMPACT_RADIUS, 2);//sqrt(2) Actually

        dictionary.setConst(DictionaryIndexType.TRAP_INITIAL_ATTACK_RADIUS, 2);
        dictionary.setConst(DictionaryIndexType.TRAP_INITIAL_ATTACK_POWER, 100);
        dictionary.setConst(DictionaryIndexType.TRAP_UPGRADE_TIME, 40);
        dictionary.setConst(DictionaryIndexType.TRAP_BUILDING_TIME, 40);
        dictionary.setConst(DictionaryIndexType.TRAP_IMPACT_RADIUS, 2);//sqrt(2) Actually
        dictionary.setConst(DictionaryIndexType.TRAP_STRENGTH, 100);
        dictionary.set(DictionaryIndexType.TRAP_LOOT, new Loot(100, 0, 1));
        dictionary.set(DictionaryIndexType.TRAP_BUILD, new Resource(100, 0));



    }


    private Dictionary() {
    }

}
