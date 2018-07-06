package graphic.VillageUi;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Village;
import models.building.village.*;

import javafx.scene.control.Button;

public class VillageGUI {
    private Village village;
    private Scene scene;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    public VillageGUI(Village village) {
        this.village = village;
    }


}
