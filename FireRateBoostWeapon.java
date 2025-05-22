class FireRateBoostWeapon implements Weapon {
    private Weapon weapon;

    //boosts fire rate
    public FireRateBoostWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public void shoot() {
        weapon.shoot();
    }

    @Override
    public int getDamage() {
        return weapon.getDamage();
    }

    @Override
    public double getFireRate() {
        return weapon.getFireRate() * 1.5; //50% faster
    }
}