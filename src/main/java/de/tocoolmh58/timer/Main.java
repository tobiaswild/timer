package de.tocoolmh58.timer;

import de.tocoolmh58.timer.command.TimerCommand;
import de.tocoolmh58.timer.timer.Timer;
import de.tocoolmh58.timer.util.Config;
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
