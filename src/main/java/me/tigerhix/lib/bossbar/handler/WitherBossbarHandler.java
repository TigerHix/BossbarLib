package me.tigerhix.lib.bossbar.handler;

import me.tigerhix.lib.bossbar.BossbarLib;
import me.tigerhix.lib.bossbar.common.NMS;
import me.tigerhix.lib.bossbar.type.BossbarWither;
import me.tigerhix.lib.bossbar.type.CraftWitherBossbar;
import me.tigerhix.lib.bossbar.type.WitherBossbar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class WitherBossbarHandler implements Listener, BossbarHandler {

    static {
        NMS.registerCustomEntity("WitherBoss", BossbarWither.class, 64);
    }

    private final Plugin plugin;
    private final Map<UUID, WitherBossbar> spawnedWithers = new HashMap<>();

    public WitherBossbarHandler() {
        this.plugin = BossbarLib.getPluginInstance();
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    WitherBossbarHandler.this.teleport(player);
                }
            }
        }, 0L, 20L);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        clearBossbar(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerKick(PlayerKickEvent event) {
        clearBossbar(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerTeleport(final PlayerTeleportEvent event) {
        teleport(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        teleport(event.getPlayer());
    }

    private void teleport(final Player player) {
        if (!hasBossbar(player)) return;
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                updateBossbar(player);
            }
        }, 2L);
    }

    private WitherBossbar newBossbar() {
        return newBossbar(ChatColor.BOLD + "", 1f);
    }

    private WitherBossbar newBossbar(String message, float percentage) {
        return new CraftWitherBossbar(message, null).setMessage(message).setPercentage(percentage);
    }

    public void clearBossbar(Player player) {
        WitherBossbar bossbar = spawnedWithers.get(player.getUniqueId());
        spawnedWithers.remove(player.getUniqueId());
        if (bossbar == null || !bossbar.isSpawned() || bossbar.getDestroyPacket() == null) {
            return;
        }
        NMS.sendPacket(player, bossbar.getDestroyPacket());
    }

    public WitherBossbar getBossbar(Player player) {
        WitherBossbar bossbar = spawnedWithers.get(player.getUniqueId());
        if (bossbar == null) {
            bossbar = newBossbar();
            spawnedWithers.put(player.getUniqueId(), bossbar);
        }
        return bossbar;
    }

    public boolean hasBossbar(Player player) {
        return spawnedWithers.containsKey(player.getUniqueId());
    }

    public void updateBossbar(Player player) {
        WitherBossbar bossbar = spawnedWithers.get(player.getUniqueId());
        if (bossbar == null) {
            return;
        }
        if (!bossbar.isSpawned()) {
            bossbar.setSpawned(true);
            bossbar.setSpawnLocation(player.getLocation().add(player.getEyeLocation().getDirection().multiply(20)));
            NMS.sendPacket(player, bossbar.getSpawnPacket());
        }
        NMS.sendPacket(player, bossbar.getMetaPacket(bossbar.getWatcher()));
        NMS.sendPacket(player, bossbar.getTeleportPacket(player.getLocation().add(player.getEyeLocation().getDirection().multiply(20))));
    }

}
