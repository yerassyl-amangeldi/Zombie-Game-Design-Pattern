class BasicWeapon implements Weapon {
    private int damage;
    private double fireRate;

    //basic weapon
    public BasicWeapon(int damage) {
        this.damage = damage;
        this.fireRate = 1.0; //1 shot per sec
    }

    @Override
    public void shoot() {
        System.out.println("Weapon fired, damage: " + damage);
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public double getFireRate() {
        return fireRate;
    }
}