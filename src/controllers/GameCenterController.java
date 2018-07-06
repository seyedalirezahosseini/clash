package controllers;

import com.gilecode.yagson.YaGson;
import exceptions.ThereIsNoGameException;
import models.Dictionary;
import models.Village;
import view.Command;
import view.Files;
import view.GameCenterView;

import java.io.IOException;


public class GameCenterController {
    private Command command = new Command();
    private GameCenterView gameCenterView = new GameCenterView();
    private Dictionary dictionary = Dictionary.getInstance();//Dictionary uses singleton pattern
    private GameCenterView view = new GameCenterView();
    private Village village; // should only be initialized in loadGame or newGame because we need to find out if it's null!

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void gameCenterMenu() {
        while (true) {
            gameCenterView.printMenu(village);
            command.update();
            switch (command.getType()) {
                case NEW_GAME:
                    newGame();
                    break;
                case LOAD_GAME:
                    loadGame();
                    break;
                case SAVE_GAME:
                    saveGame();
                    break;
                case WHERE_AM_I:
                    // TODO: 5/6/2018 handle later
                    System.out.println("You Are MainMenu");
                    break;
                case EXIT:
                    // we can check and if game wasn't save ask if client wants to save the game
                    return;
                default:
                    view.invalidCommand();
            }
        }
    }

    private void loadGame() {
        try {
            loadVillageFromJson(command.getPath());
            VillageController villageController = new VillageController(village);
            villageController.menu();
        } catch (IOException e) {
            gameCenterView.cantWorkWithJsonFile();
        }
    }

    private void newGame() {
        village = new Village();
        VillageController controller = new VillageController(village);
        controller.menu();
    }

    private void loadVillageFromJson(String path) throws IOException {
        String json = Files.getJsonFromFile(path);
        village = new YaGson().fromJson(json, Village.class);
    }

    private void saveGame() {
        String path = command.getPath();
        String name = command.getName();

        try {
            if (Files.isFileExisted(path, name)) {
                view.FileSavedWithThisName();
                command.update();
                if (command.isInputBooleanTrue()) {// TODO: 5/7/2018 check
                    String Json = villageToJson(village);
                    Files.saveVillage(Json, path, name);
                }
            } else {
                String Json = villageToJson(village);
                Files.saveVillage(Json, path, name);
            }
        } catch (ThereIsNoGameException |
                NullPointerException e) {
            // TODO: 4/26/2018 exceptions view.Files
        } catch (IOException e) {
        }
    }

    public void saveGame(Village village ,Command command) {
        String path = command.getPath();
        String name = command.getName();
        try {
            if (Files.isFileExisted(path, name)) {
                view.FileSavedWithThisName();
                command.update();
                if (command.isInputBooleanTrue()) {// TODO: 5/7/2018 check
                    String Json = villageToJson(village);
                    Files.saveVillage(Json, path, name);
                }
            } else {
                String Json = villageToJson(village);
                Files.saveVillage(Json, path, name);
            }
        } catch (ThereIsNoGameException |
                NullPointerException e) {
            // TODO: 4/26/2018 exceptions view.Files
        } catch (IOException e) {
        }
    }


    private String villageToJson(Village village) throws NullPointerException, ThereIsNoGameException {
        if (village == null) {
            throw new ThereIsNoGameException();
        }
        return new YaGson().toJson(village);
    }
}
