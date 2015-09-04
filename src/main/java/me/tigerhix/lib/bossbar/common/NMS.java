package me.tigerhix.lib.bossbar.common;

import net.minecraft.server.v1_8_R3.EntityTypes;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Map;

public final class NMS {

    private NMS() {
    }

    public static void sendPacket(Player player, Packet... packets) {
        if (!(player instanceof CraftPlayer)) {
            player = player.getPlayer();
            if (!(player instanceof CraftPlayer)) {
                throw new IllegalArgumentException();
            }
        }
        for (Packet packet : packets) ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void registerCustomEntity(String entityName, Class<?> entityClass, int entityId) {
        Reflections.getField(EntityTypes.class, "c", Map.class).get(null).put(entityName, entityClass);
        Reflections.getField(EntityTypes.class, "d", Map.class).get(null).put(entityClass, entityName);
        Reflections.getField(EntityTypes.class, "e", Map.class).get(null).put(entityId, entityClass);
        Reflections.getField(EntityTypes.class, "f", Map.class).get(null).put(entityClass, entityId);
        Reflections.getField(EntityTypes.class, "g", Map.class).get(null).put(entityName, entityId);
    }

}
