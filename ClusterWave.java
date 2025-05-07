import java.util.*;

class ClusterWave implements WaveStrategy {
    //spawns zombies in groups
    @Override
    public void spawnWave() {
        GameManager gameManager = GameManager.getInstance();
        EntityFactory zombieFactory = new ZombieFactory();
        int waveNumber = gameManager.getWaveNumber();
        //3 clusters
        int clusterCount = 3;
        for (int i = 0; i < clusterCount; i++) {
            double clusterX = new Random().nextDouble() * 800;
            double clusterY = new Random().nextDouble() * 600;
            int zombiesInCluster = 3 + waveNumber;
            for (int j = 0; j < zombiesInCluster; j++) {
                String type = selectZombieType(waveNumber);
                Zombie zombie = zombieFactory.createZombie(type);
                //small offset for cluster
                zombie.setX(clusterX + (new Random().nextDouble() - 0.5) * 50);
                zombie.setY(clusterY + (new Random().nextDouble() - 0.5) * 50);
                gameManager.getZombies().add(zombie);
            }
        }
    }

    //pick zombie type
    private String selectZombieType(int waveNumber) {
        double rand = new Random().nextDouble();
        double runnerChance = 0.3 + waveNumber * 0.05;
        double hugeChance = 0.15 + waveNumber * 0.03;
        double poisonChance = 0.15 + waveNumber * 0.03;
        if (rand < runnerChance) return "Runner";
        if (rand < runnerChance + hugeChance) return "Huge";
        if (rand < runnerChance + hugeChance + poisonChance) return "Poison";
        return "Regular";
    }
}