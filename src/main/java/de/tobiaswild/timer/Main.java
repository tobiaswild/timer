package de.tobiaswild.timer;

import de.tobiaswild.timer.command.TimerCommand;
import de.tobiaswild.timer.timer.Timer;
import de.tobiaswild.timer.util.Config;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * Original Source Code by DerBanko on YouTube and GitHub
 * https://www.youtube.com/watch?v=KxjsEzA_fU0
 * https://github.com/DerBanko/TutorialReloaded
 *
 * Time conversion by @Haphazard on Stack Overflow
 * https://stackoverflow.com/questions/6118922/convert-seconds-value-to-hours-minutes-seconds
 */

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
        this.getCommand("timer").setExecutor(new TimerCommand());
        timer = new Timer();
    }

    @Override
    public void onDisable() {
        timer.save();
        config.save();
    }

    public static String getPrefix() {
        return ChatColor.GRAY + "[" + ChatColor.BLUE + "timer" + ChatColor.GRAY + "]";
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
