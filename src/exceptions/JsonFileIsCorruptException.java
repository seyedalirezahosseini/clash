package exceptions;

public class JsonFileIsCorruptException extends Exception {
    String massage; // shows us why json file is corrupted

    public JsonFileIsCorruptException(String massage) {
        this.massage = massage;
    }
}
