import java.util.*;

public abstract class Entity {
    //basic stuff for all entities
    protected double x, y;
    protected int health;

    public Entity(double x, double y, int health) {
        this.x = x;
        this.y = y;
        this.health = health;
    }

    //every entity updates itself
    public abstract void update();

    //getters and setters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}