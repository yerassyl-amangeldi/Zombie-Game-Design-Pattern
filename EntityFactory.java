public interface EntityFactory {
    //for creating players and zombies
    Player createPlayer(String name);
    Zombie createZombie(String type);
}