import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Particle {
    private double x, y;
    private double dx, dy;
    private double lifetime;
    private Color color; // Должно быть javafx.scene.paint.Color

    public Particle(double x, double y, double dx, double dy, double lifetime, Color color) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.lifetime = lifetime;
        this.color = color;
    }

    public void update() {
        x += dx;
        y += dy;
        lifetime -= 0.1;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public boolean isAlive() {
        return lifetime > 0;
    }
}