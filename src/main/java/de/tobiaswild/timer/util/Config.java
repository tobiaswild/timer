package de.tobiaswild.timer.util;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    private final File file;
    private final YamlConfiguration config;

    public Config() {
        File dir = new File("./plugins/Timer");
        if (!dir.exists()) {
            dir.mkdir();
        }
        this.file = new File(dir, "config.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }
    public YamlConfiguration getConfig() {
        return config;
    }
}
