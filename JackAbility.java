class JackAbility implements Command {
    @Override
    public void execute(Player player) {
        // jack's ability: more damage
        Weapon currentWeapon = player.getWeapon();
        player.setWeapon(new DamageBoostWeapon(currentWeapon));
    }
}