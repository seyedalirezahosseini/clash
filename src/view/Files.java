package view;

import com.gilecode.yagson.YaGson;
import models.Village;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Files {
    //this class handles I/O for files
    // TODO: 5/5/2018 Add to Dictionary later
    private static String projectDirectory = System.getProperty("user.dir");
    private static String attackableVillagesDirectory = projectDirectory.concat("\\").concat("AttackableVillages");
    private static String savedVillagesDirectory = projectDirectory.concat("\\").concat("SavedVillages");
    private File[] attackableFilesList;

    public void printAttackableVillagesList() {
        File folder = new File(attackableVillagesDirectory);
        attackableFilesList = folder.listFiles();
        if (attackableFilesList == null) {
            System.out.println("there is no Village File to Attack");
            return;
        } else {
            printFilesList(attackableFilesList);
        }
    }

    public int getFilesNumber() {
        return attackableFilesList.length;
    }

    public String getAttackableVillagePath(int directoryNumber) {
        File folder = new File(attackableVillagesDirectory);
        attackableFilesList = folder.listFiles();
        return attackableFilesList[directoryNumber - 1].toString();
    }

    private void printFilesList(File[] attackableFilesList) {
        for (int i = 0, attackableFilesListLength = attackableFilesList.length; i < attackableFilesListLength; i++) {
            File file = attackableFilesList[i];
            String[] path = file.toString().split("\\\\");
            System.out.println((i + 2) + "." + path[path.length - 1]);
        }
    }

    public static String getJsonFromFile(String jsonPath) throws IOException {
        String json;
        json = new String(java.nio.file.Files.readAllBytes(Paths.get(jsonPath)));
        return json;
    }

    public static void saveVillage(String json, String path, String name) throws IOException {
        File file = new File(path.concat("\\" + name + ".json"));
        if (file.exists()) {
            file.delete();
        }
        if (file.createNewFile()) {
            System.out.println("Game was saved in:\n" + path.concat("\\" + name + ".json"));
        } else {
            System.out.println("file not created");
        }
        FileWriter writer = new FileWriter(file);
        writer.write(json);
        writer.close();
    }

    public static boolean isFileExisted(String path, String name) {
        File file = new File(path.concat("\\" + name + ".json"));
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public static boolean isFileExisted(String path) {
        if (!path.endsWith(".json"))
            return false;
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public static void printFileNotFound() {
        System.out.println("File Not Found");
    }

    public static Village jsonToVillage(String json) {
        Village village;
        village = new YaGson().fromJson(json, Village.class);
        return village;
    }

    public static ArrayList<Village> getSavedVillages() {
        File folder = new File(savedVillagesDirectory);
        File[] savedVillagesFiles = folder.listFiles();

        ArrayList<Village> savedVillages = new ArrayList<>();

        for (File villageFile : savedVillagesFiles) {
            try {
                savedVillages.add(jsonToVillage(getJsonFromFile(villageFile.getPath())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return savedVillages;
    }

}
