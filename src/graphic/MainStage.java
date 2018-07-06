package graphic;

import graphic.mainMenuUI.MainMenuUI;
import javafx.application.Application;
import javafx.stage.Stage;


public class MainStage extends Application {

    static Stage stage;

    public static void show() {
        launch();
    }

    public static Stage getStage() {
        return stage;
    }

    @Override
    public void start(javafx.stage.Stage stage) throws Exception {
        this.stage = stage;
//        MainStage.setResizable(false);
//        MainStage.setFullScreen(true);
        stage.setTitle("AP_PAK");

        stage.setScene(MainMenuUI.getScene());
        stage.show();
    }
}
