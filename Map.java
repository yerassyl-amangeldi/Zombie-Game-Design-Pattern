import java.util.*;

class Map {
    //map size and obstacles
    private double width, height;
    private List<Obstacle> obstacles;

    public Map(double width, double height) {
        this.width = width;
        this.height = height;
        //add some obstacles
        obstacles = new ArrayList<>();
        obstacles.add(new Obstacle(200, 200, 50, 50));
        obstacles.add(new Obstacle(500, 300, 50, 50));
    }

    //get map size
    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    //check if there's an obstacle
    public boolean hasObstacle(double x, double y) {
        for (Obstacle obstacle : obstacles) {
            if (x >= obstacle.getX() && x <= obstacle.getX() + obstacle.getWidth() &&
                    y >= obstacle.getY() && y <= obstacle.getY() + obstacle.getHeight()) {
                return true;
            }
        }
        return false;
    }

    //remove obstacle (for huge zombies)
    public void destroyObstacle(double x, double y) {
        obstacles.removeIf(obstacle ->
                x >= obstacle.getX() && x <= obstacle.getX() + obstacle.getWidth() &&
                        y >= obstacle.getY() && y <= obstacle.getY() + obstacle.getHeight());
    }
}