import java.util.*;

class PlayerFactory implements EntityFactory {
    //creates players
    @Override
    public Player createPlayer(String name) {
        switch (name) {
            case "Jack":
                return new Player("Jack", 100, 100, 150, new BasicWeapon(10), 1, new JackAbility());
            case "Emily":
                return new Player("Emily", 200, 100, 120, new BasicWeapon(15), 2, new EmilyAbility());
            case "Brad":
                return new Player("Brad", 150, 150, 210, new BasicWeapon(8), 1, new BradAbility());
            default:
                throw new IllegalArgumentException("Unknown player: " + name);
        }
    }

    //can't create zombies
    @Override
    public Zombie createZombie(String type) {
        throw new UnsupportedOperationException("PlayerFactory can't make zombies");
    }
}