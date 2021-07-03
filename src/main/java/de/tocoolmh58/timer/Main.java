package de.tocoolmh58.timer;

import de.tocoolmh58.timer.commands.TimerCommand;
import de.tocoolmh58.timer.timer.Timer;
import de.tocoolmh58.timer.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;
    private Timer timer;
    private Config config;

    @Override
    public void onLoad() {
        instance = this;
        config = new Config();
    }

    @Override
    public void onEnable() {
        getCommand();
        timer = new Timer();
    }

    @Override
    public void onDisable() {
        timer.save();
        config.save();
    }

    public void getCommand() {
        this.getCommand("timer").setExecutor(new TimerCommand());
    }

    public static Main getInstance() {
        return instance;
    }
    public Timer getTimer() {
        return timer;
    }
    public Config getConfiguration() {
        return config;
    }
}
