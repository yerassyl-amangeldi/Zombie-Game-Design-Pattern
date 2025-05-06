class EmilyAbility implements Command {
    @Override
    public void execute(Player player) {
        // emily's ability: faster shooting
        Weapon currentWeapon = player.getWeapon();
        player.setWeapon(new FireRateBoostWeapon(currentWeapon));
    }
}