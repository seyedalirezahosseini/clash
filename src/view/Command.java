package view;

import enums.BuildingType;
import enums.CommandType;
import enums.SoldierType;
import models.Coordinate;
import models.livingBeing.Attack.Soldier;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {

    private static final String SHOW_BUILDING = "showBuildings";
    private static final String RESOURCE = "resources";
    private static final String ITEM = "(\\d+)";
    private static final String TURN = "turn (\\d+)";
    private static final String SHOW_MENU = "showMenu";
    private static final String WHERE_AM_I = "whereAmI";
    private static final String NEW_GAME = "newGame";
    private static final String SAVE_GAME = "^save (.+) (.+)$";
    private static final String LOAD_GAME = "^load (.+)";

    private static final String BACK = "back";
    private static final String UPGRADE = "upgrade";
    private static final String BOOLEAN = "[Y||N]";
    private static final String COORDINATE = "\\((\\d+),(\\d+)\\)";

    private static final String ATTACK = "attack";
    private static final String ADD_SOLDIER = "Select (\\D+) (\\d+)";
    private static final String END_SELECT = "END SELECT";
    private static final String MAP_FORMAT = "";
    private static final String PUT_UNIT = "Put (\\D+) (\\d+) in (\\d+),(\\d+)";
    private static final String ONE_TURN = "turn";
    private static final String END_BATTLE = "Quit attack";
    private static final String STATUS_RESOURCE = "Status resources";
    private static final String STATUS_UNIT = "status unit (\\D+)";
    private static final String STATUS_UNITS = "status units";
    private static final String STATUS_BUILDING = "status tower (\\D+)";
    private static final String STATUS_BUILDINGS = "status towers";
    private static final String STATUS_WAR = "status all";

    private static Scanner scanner = new Scanner(System.in);
    private String strCommand;
    private CommandType type;
    private int turnValue;
    private String path;
    private String name;
    private int itemNumber;
    private boolean inputBoolean;

    private int unitX;
    private int unitY;
    private SoldierType soldierType;
    private int soldierNumber;
    private BuildingType towerType;

    private int soldiersToAdd;
    private SoldierType typeToAdd;

    public boolean isInputBooleanTrue() {
        return inputBoolean;
    }

    public String getBuildingName() {
        return strCommand.split(" ")[0];
    }

    public int getBuildingID() {
        return Integer.parseInt(strCommand.split(" ")[1]);
    }

    public String getStrCommand() {
        return strCommand;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }


    public int getItemNumber() {
        itemNumber = 0;
        Matcher matcher = Pattern.compile(ITEM).matcher(strCommand);
        if (matcher.find())
            itemNumber = Integer.parseInt(matcher.group(1));
        return itemNumber;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public int getTurnValue() {
        return turnValue;
    }

    public String getCommandString() {
        return strCommand;
    }

    public CommandType getType() {
        if (type != null) {
            return type;
        }
        findTypeOfCommand();
        return type;
    }

    private void findTypeOfCommand() {
        if (strCommand.matches(LOAD_GAME)) {
            initializeLoadGame();
        } else if (strCommand.matches(SAVE_GAME)) {
            initializeSaveGame();
        } else if (strCommand.matches(SHOW_BUILDING)) {
            initializeShowBuilding();
        } else if (strCommand.matches(RESOURCE)) {
            initializeResource();

        } else if (strCommand.matches(ITEM)) {
            initializeItemNumber();

        } else if (strCommand.matches(TURN)) {
            initializeTurn();
        } else if (strCommand.matches(SHOW_MENU)) {
            initializeShowMenu();

        } else if (strCommand.matches(WHERE_AM_I)) {
            initializeWhereAmI();
        } else if (strCommand.matches(BACK)) {
            initializeBack();

        } else if (strCommand.matches(UPGRADE)) {
            initializeUpgrade();
        } else if (strCommand.matches(BOOLEAN)) {
            initializeBoolean();
        } else if (strCommand.matches(COORDINATE)) {
            this.type = CommandType.COORDINATE;
        } else if (strCommand.matches(NEW_GAME)) {
            this.type = CommandType.NEW_GAME;
        } else if (strCommand.matches((ATTACK))) {
            this.type = CommandType.ATTACK_MODE;
        } else if (strCommand.matches(END_SELECT)) {
            this.type = CommandType.END_SELECT;
        } else if (strCommand.matches(PUT_UNIT)) {
            putUnit();
        } else if (strCommand.matches(ONE_TURN)) {
            this.type = CommandType.ONE_TURN;
        } else if (strCommand.matches(END_BATTLE)) {
            this.type = CommandType.QUIT_ATTACK;
        } else if (strCommand.matches(STATUS_RESOURCE)) {
            this.type = CommandType.STATUS_RESOURCE;
        } else if (strCommand.matches(STATUS_UNIT) || strCommand.matches(STATUS_UNITS)) {
            statusSoldier();
        } else if (strCommand.matches(STATUS_BUILDING) || strCommand.matches(STATUS_BUILDINGS)) {
            statusTower();
        } else if (strCommand.matches(STATUS_WAR)) {
            this.type = CommandType.STATUS_WAR;
        } else {
            type = CommandType.ELSE;
        }

    }

    public void statusTower() {
        this.type = CommandType.SHOW_BUILDINGS;
        towerType = null;
        if (strCommand.matches(STATUS_BUILDINGS)) {
            return;
        }
        Matcher matcher = Pattern.compile(STATUS_BUILDING).matcher(strCommand);
        if (matcher.find()) {
            if (matcher.group(1).equals("BARRACKS")) {
                towerType = BuildingType.BARRACKS;
            } else if (matcher.group(1).equals("CAMP")) {
                towerType = BuildingType.CAMP;
            } else if (matcher.group(1).equals("TOWN_HALL")) {
                towerType = BuildingType.TOWN_HALL;
            } else if (matcher.group(1).equals("AIR_DEFENSE")) {
                towerType = BuildingType.AIR_DEFENSE;
            } else if (matcher.group(1).equals("CANNON")) {
                towerType = BuildingType.CANNON;
            } else if (matcher.group(1).equals("ARCHER_TOWER")) {
                towerType = BuildingType.ARCHER_TOWER;
            } else if (matcher.group(1).equals("WIZARD_TOWER")) {
                towerType = BuildingType.WIZARD_TOWER;
            } else if (matcher.group(1).equals("ELIXIR_MINE")) {
                towerType = BuildingType.ELIXIR_MINE;
            } else if (matcher.group(1).equals("ELIXIR_STORAGE")) {
                towerType = BuildingType.ELIXIR_STORAGE;
            } else if (matcher.group(1).equals("GOLD_MINE")) {
                towerType = BuildingType.GOLD_MINE;
            } else if (matcher.group(1).equals("GOLD_STORAGE")) {
                towerType = BuildingType.GOLD_STORAGE;
            }
        }
    }

    public BuildingType getTowerType() {
        return towerType;
    }

    public void statusSoldier() {
        this.type = CommandType.STATUS_SOLDIER;
        soldierType = null;
        if (strCommand.matches(STATUS_UNITS)) {
            return;
        }
        Matcher matcher = Pattern.compile(STATUS_UNIT).matcher(strCommand);
        if (matcher.find()) {
            soldierType = SoldierType.getByString(matcher.group(1));
        }
    }

    public void putUnit() {
        this.type = CommandType.PUT_UNIT;
        Matcher matcher = Pattern.compile(PUT_UNIT).matcher(strCommand);
        if (matcher.find()) {
            soldierType = SoldierType.getByString(matcher.group(1));
            soldierNumber = Integer.parseInt(matcher.group(2));
            unitX = Integer.parseInt(matcher.group(3));
            unitY = Integer.parseInt(matcher.group(4));
        }
    }

    public int getUnitX() {
        return unitX;
    }

    public int getUnitY() {
        return unitY;
    }

    public SoldierType getSoldierType() {
        return soldierType;
    }

    public int getSoldierNumber() {
        return soldierNumber;
    }

    private void initializeLoadGame() {
        Matcher matcher = getMatcher(LOAD_GAME);
        path = getMatcher(LOAD_GAME).group(1);
        type = CommandType.LOAD_GAME;
    }

    private Matcher getMatcher(String loadGame) {
        Pattern pattern = Pattern.compile(loadGame);
        Matcher matcher = pattern.matcher(strCommand);
        matcher.find();
        return matcher;
    }

    private void initializeSaveGame() {
        Matcher matcher = getMatcher(SAVE_GAME);
        path = matcher.group(1);
        name = matcher.group(2);
        type = CommandType.SAVE_GAME;
    }


    public Coordinate getCoordinate() {
        Matcher matcher = getMatcher(COORDINATE);
        return new Coordinate(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
    }

    private void initializeBoolean() {
        type = CommandType.BOOLEAN;
        if (strCommand.matches("Y")) {
            inputBoolean = true;
        } else {
            inputBoolean = false;
        }

    }

    private void initializeUpgrade() {
        type = CommandType.UPGRADE;
    }

    private void initializeBack() {
        type = CommandType.BACK;
    }

    private void initializeWhereAmI() {
        type = CommandType.WHERE_AM_I;
    }

    private void initializeShowMenu() {
        type = CommandType.SHOW_MENU;
    }

    private void initializeTurn() {
        type = CommandType.TURN;
        Matcher matcher = Pattern.compile(TURN).matcher(strCommand);
        if (matcher.find())
            turnValue = Integer.parseInt(matcher.group(1));
    }

    private void initializeResource() {
        type = CommandType.RESOURCES;
    }

    private void initializeShowBuilding() {
        type = CommandType.SHOW_BUILDINGS;
    }

    private void initializeItemNumber() {
        type = CommandType.ITEM_NUMBER;
    }

    public void update() {
        strCommand = scanner.nextLine();
        type = null;
        turnValue = 0;
        path = null;
        name = null;
        itemNumber = 0;
        inputBoolean = false;
        soldierType = null;
        soldierNumber = 0;
        towerType = null;
    }

    public boolean addSoldiers() {
        Matcher matcher = Pattern.compile(ADD_SOLDIER).matcher(strCommand);
        HashMap<SoldierType, Integer> soldierToAdd = new HashMap<>();
        if (matcher.find()) {
            soldiersToAdd = Integer.parseInt(matcher.group(2));
            typeToAdd = SoldierType.getByString(matcher.group(1));
            return true;
        }
        return false;
    }

    public SoldierType getTypeToAdd() {
        return typeToAdd;
    }

    public int getSoldiersToAdd() {
        return soldiersToAdd;
    }

}