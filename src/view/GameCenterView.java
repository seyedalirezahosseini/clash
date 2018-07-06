package view;

import models.Village;

public class GameCenterView {
    public void printMenu(Village village) {
        // TODO: 4/28/2018 we can don't print saveGame when there's nothing to save or print it in gray
        System.out.println("--- -- Main Menu -- ---");
        System.out.println("1.NewGame");
        System.out.println("2.Load #Path");
        System.out.println("3.Save $Path #name");
//        System.out.println("4.save #name");
        System.out.println("-----------------------");
        if (village != null) {
            System.out.println("Current Village : " + village.getName());
        } else {
            System.out.println("No village Loaded!");
        }
        System.out.println("-----------------------");
        System.out.println("Command: ");
    }

    public void cantWorkWithJsonFile() {
        System.out.println("there is something wring with json file");
    }

    public void FileSavedWithThisName() {
        System.out.println("there is a game saved with this name");
        System.out.println("do you want to replace [N,Y]");
    }

    public void invalidCommand() {
        System.out.println("::::Invalid Command::::");
    }
}
