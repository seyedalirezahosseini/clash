import controllers.GameCenterController;
import graphic.MainStage;
import graphic.mainMenuUI.MainMenuUI;
import javafx.application.Application;
import javafx.stage.Stage;
import models.Dictionary;

public class Main {

    static Stage stage;


    public static void main(String[] args) {
        Dictionary.getInstance().initialize();
        GameCenterController controller = new GameCenterController();
        controller.getDictionary().initialize();

        MainStage.show();

//        try {
//            controller.gameCenterMenu();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
