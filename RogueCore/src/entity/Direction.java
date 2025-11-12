package entity;

public enum Direction {
    UP(3),
    DOWN(0),
    LEFT(1),
    RIGHT(2);

    final int value;
    Direction(int value) {
        this.value = value;
    }
}
