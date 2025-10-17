package entity;

public class Player extends Entity {

    private Direction dir;

    public Player(int x, int y) {
        super(x, y);

        dir = Direction.NO_DIR;
    }

    @Override
    public void tick() {
        if (dir == Direction.UP) {
            this.y -= 5;
        }

        if (dir == Direction.DOWN) {
            this.y += 5;
        }

        if (dir == Direction.LEFT) {
            this.x -= 5;
        }

        if (dir == Direction.RIGHT) {
            this.x += 5;
        }
    }

    public Direction getDirection() {
        return dir;
    }

    public void setDirection(Direction dir) {
        this.dir = dir;
    }
}
