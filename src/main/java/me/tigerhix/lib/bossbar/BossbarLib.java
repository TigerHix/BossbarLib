package me.tigerhix.lib.bossbar;

import me.tigerhix.lib.bossbar.handler.BossbarHandler;
import me.tigerhix.lib.bossbar.handler.WitherBossbarHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class BossbarLib extends JavaPlugin {

    private static Plugin instance;
    private static BossbarHandler handler;

    public static Plugin getPluginInstance() {
        return instance;
    }

    public static void setPluginInstance(Plugin instance) {
        if (BossbarLib.instance != null) return;
        BossbarLib.instance = instance;
        setHandler(new WitherBossbarHandler());
    }

    public static BossbarHandler getHandler() {
        return handler;
    }

    public static void setHandler(BossbarHandler handler) {
        BossbarLib.handler = handler;
    }

    @Override
    public void onEnable() {
        setPluginInstance(this);
    }

}
