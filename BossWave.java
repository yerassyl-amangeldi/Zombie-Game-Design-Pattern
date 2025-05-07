class BossWave implements WaveStrategy {
    //spawns one big boss
    @Override
    public void spawnWave() {
        GameManager gameManager = GameManager.getInstance();
        EntityFactory zombieFactory = new ZombieFactory();
        //boss in the middle
        Zombie boss = new Zombie("Boss", 400, 300, 200, 2, 10, 100, new HugeZombieBehavior(), true);
        gameManager.getZombies().add(boss);
        //add some regular zombies
        for (int i = 0; i < 5; i++) {
            gameManager.getZombies().add(zombieFactory.createZombie("Regular"));
        }
    }
}