package me.tigerhix.lib.bossbar.type;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

public class CraftWitherBossbar extends WitherBossbar {

    private BossbarWither wither;

    public CraftWitherBossbar(String name, Location location) {
        super(name, location);
    }

    @Override
    public Packet getSpawnPacket() {
        wither = new BossbarWither(((CraftWorld) spawnLocation.getWorld()).getHandle());
        wither.setLocation(spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ(), spawnLocation.getYaw(), spawnLocation.getPitch());
        wither.setInvisible(false);
        wither.setCustomName(name);
        wither.setHealth(health);
        return new PacketPlayOutSpawnEntityLiving(wither);
    }

    @Override
    public Packet getDestroyPacket() {
        if (wither == null) return null;
        return new PacketPlayOutEntityDestroy(wither.getId());
    }

    @Override
    public Packet getMetaPacket(DataWatcher watcher) {
        return new PacketPlayOutEntityMetadata(wither.getId(), watcher, true);
    }

    @Override
    public Packet getTeleportPacket(Location location) {
        return new PacketPlayOutEntityTeleport(wither.getId(), location.getBlockX() * 32, location.getBlockY() * 32, location.getBlockZ() * 32, (byte) ((int) location.getYaw() * 256 / 360), (byte) ((int) location.getPitch() * 256 / 360), false);
    }

    @Override
    public DataWatcher getWatcher() {
        DataWatcher watcher = new DataWatcher(wither);
        watcher.a(0, (byte) 0x20);
        watcher.a(2, name);
        watcher.a(3, (byte) 1);
        watcher.a(6, health);
        watcher.a(7, 0);
        watcher.a(8, (byte) 0);
        watcher.a(10, name);
        watcher.a(11, (byte) 1);
        return watcher;
    }

}
