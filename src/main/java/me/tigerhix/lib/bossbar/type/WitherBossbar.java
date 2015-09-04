package me.tigerhix.lib.bossbar.type;

import me.tigerhix.lib.bossbar.common.Maths;
import org.bukkit.Location;

public abstract class WitherBossbar implements Bossbar {

    protected static final float MAX_HEALTH = 300;

    protected boolean spawned;
    protected Location spawnLocation;

    protected String name;
    protected float health;

    public WitherBossbar(String message, Location spawnLocation) {
        this.spawnLocation = spawnLocation;
        this.name = message;
        this.health = MAX_HEALTH;
    }

    @Override
    public String getMessage() {
        return name;
    }

    @Override
    public WitherBossbar setMessage(String message) {
        this.name = message;
        return this;
    }

    @Override
    public float getPercentage() {
        return health / MAX_HEALTH;
    }

    @Override
    public WitherBossbar setPercentage(float percentage) {
        percentage = Maths.clamp(percentage, 0f, 1f);
        health = percentage * MAX_HEALTH;
        return this;
    }

    public boolean isSpawned() {
        return spawned;
    }

    public void setSpawned(boolean spawned) {
        this.spawned = spawned;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    public void setSpawnLocation(Location spawnLocation) {
        this.spawnLocation = spawnLocation;
    }

}
