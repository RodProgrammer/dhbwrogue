package mapmanager.maps;

public enum Difficulty {
    EASY("Easy"),
    NORMAL("Normal"),
    HARD("Hard");

    final String difString;
    Difficulty(String difString) {
        this.difString = difString;
    }
}
