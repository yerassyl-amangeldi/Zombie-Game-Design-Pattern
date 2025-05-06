class DamageBoostWeapon implements Weapon {
    private Weapon weapon;

    //boosts damage
    public DamageBoostWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public void shoot() {
        weapon.shoot();
    }

    @Override
    public int getDamage() {
        return (int) (weapon.getDamage() * 1.5); //50% more damage
    }

    @Override
    public double getFireRate() {
        return weapon.getFireRate();
    }
}