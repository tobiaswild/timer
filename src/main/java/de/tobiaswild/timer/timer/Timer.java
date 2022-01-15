package de.tobiaswild.timer.timer;

import de.tobiaswild.timer.Main;
import de.tobiaswild.timer.util.Config;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer {
    private boolean running;
    private boolean hidden;
    private int time;
    private int seconds;
    private int minutes;
    private int hours;
    private int safe;

    public Timer() {
        Config config = Main.getInstance().getConfiguration();
        this.running = false;
        if (config.getConfig().contains("timer.time")) {
            this.time = config.getConfig().getInt("timer.time");
        } else {
            this.time = 0;
        }
        if (config.getConfig().contains("timer.hidden")) {
            this.hidden = config.getConfig().getBoolean("timer.hidden");
        } else {
            this.hidden = false;
        }
        run();
    }

    public void sendActionbar() {
        for (Player player: Bukkit.getOnlinePlayers()) {
            if (!isRunning()) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "timer is paused"));
                continue;
            }
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD.toString() + getHours() + ":" + getMinutes() + ":" + getSeconds()));
        }
    }

    public void save() {
        Config config = Main.getInstance().getConfiguration();
        config.getConfig().set("timer.time", time);
        config.getConfig().set("timer.hidden", hidden);
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!isHidden()) {
                    sendActionbar();
                    if (!isRunning()) {
                        return;
                    }
                    setTime(getTime()+1);
                    setHours(getTime() / 3600);
                    setSafe(getTime() - hours * 3600);
                    setMinutes(getSafe() / 60);
                    setSafe(getSafe() - getMinutes() * 60);
                    setSeconds(getSafe());
                } else {
                    setRunning(false);
                }
            }
        }.runTaskTimer(Main.getInstance(), 20, 20);
    }

    public boolean isRunning() {
        return running;
    }
    public void setRunning(boolean running) {
        this.running = running;
    }
    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }
    public int getSeconds() {
        return seconds;
    }
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
    public int getMinutes() {
        return minutes;
    }
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
    public int getHours() {
        return hours;
    }
    public void setHours(int hours) {
        this.hours = hours;
    }
    public int getSafe() {
        return safe;
    }
    public void setSafe(int safe) {
        this.safe = safe;
    }
    public boolean isHidden() {
        return hidden;
    }
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}
