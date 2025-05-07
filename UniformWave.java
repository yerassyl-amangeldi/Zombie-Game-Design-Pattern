import java.util.*;
class UniformWave implements WaveStrategy {
    //spawns zombies evenly
    @Override
    public void spawnWave() {
        GameManager gameManager = GameManager.getInstance();
        EntityFactory zombieFactory = new ZombieFactory();
        int waveNumber = gameManager.getWaveNumber();
        //more zombies each wave
        int zombieCount = 5 + waveNumber * 2;
        for (int i = 0; i < zombieCount; i++) {
            String type = selectZombieType(waveNumber);
            gameManager.getZombies().add(zombieFactory.createZombie(type));
        }
    }

    //pick zombie type based on wave
    private String selectZombieType(int waveNumber) {
        double rand = new Random().nextDouble();
        double runnerChance = 0.2 + waveNumber * 0.05;
        double hugeChance = 0.1 + waveNumber * 0.03;
        double poisonChance = 0.1 + waveNumber * 0.03;
        if (rand < runnerChance) return "Runner";
        if (rand < runnerChance + hugeChance) return "Huge";
        if (rand < runnerChance + hugeChance + poisonChance) return "Poison";
        return "Regular";
    }
}