import java.util.*;

class ZombieFactory implements EntityFactory {
    //can't create players
    @Override
    public Player createPlayer(String name) {
        throw new UnsupportedOperationException("ZombieFactory can't make players");
    }

    //creating zombies
    @Override
    public Zombie createZombie(String type) {
        double x = new Random().nextDouble() * 800;
        double y = new Random().nextDouble() * 600;
        switch (type) {
            case "Regular":
                return new Zombie("Regular", x, y, 50, 2, 5, 10, new RegularZombieBehavior(), false);
            case "Runner":
                return new Zombie("Runner", x, y, 30, 4, 3, 15, new RunnerZombieBehavior(), false);
            case "Huge":
                return new Zombie("Huge", x, y, 100, 1.5, 7, 20, new HugeZombieBehavior(), false);
            case "Poison":
                return new Zombie("Poison", x, y, 40, 2, 4, 15, new PoisonZombieBehavior(), true);
            default:
                throw new IllegalArgumentException("Unknown zombie: " + type);
        }
    }
}