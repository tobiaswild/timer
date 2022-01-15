package de.tobiaswild.timer.command;

import de.tobiaswild.timer.timer.Timer;
import de.tobiaswild.timer.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import javax.imageio.stream.ImageInputStream;
import java.util.ArrayList;
import java.util.List;

public class TimerCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Timer timer = Main.getInstance().getTimer();

        if (!sender.hasPermission("timer.use")) {
            noPermission(sender);
            return false;
        }
        if (args.length == 0) {
            sendUsage(sender);
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "resume" -> {
                if (!timer.isHidden()) {
                    if (timer.isRunning()) {
                        sendToSender(sender,ChatColor.RED + "already running");
                        return false;
                    }
                    timer.setRunning(true);
                    sendToAll(ChatColor.GREEN + "started");
                    return true;
                }
                notPossible(sender);
                return false;
            }
            case "pause" -> {
                if (!timer.isHidden()) {
                    if (!timer.isRunning()) {
                        sendToSender(sender,ChatColor.RED + "already stopped");
                        return false;
                    }
                    timer.setRunning(false);
                    sendToAll(ChatColor.GREEN + "stopped");
                    return true;
                }
                notPossible(sender);
                return false;
            }
            case "set" -> {
                if (!timer.isHidden()) {
                    if (args.length != 2) {
                        sendToSender(sender,"Usage: /timer time <time>");
                        return false;
                    }
                    try {
                        timer.setRunning(false);
                        timer.setTime(Integer.parseInt(args[1]));
                        sendToSender(sender,ChatColor.GREEN + "set to " + args[1]);
                    } catch (NumberFormatException e) {
                        sendToSender(sender,ChatColor.RED + "second parameter has to be a int!");
                    }
                    break;
                }
                notPossible(sender);
                return false;
            }
            case "reset" -> {
                if (!timer.isHidden()) {
                    timer.setRunning(false);
                    timer.setTime(0);
                    sendToSender(sender,ChatColor.GREEN + "reset");
                    return true;
                }
                notPossible(sender);
                return false;
            }
            case "show" -> {
                if (timer.isHidden()) {
                    timer.setHidden(false);
                    sendToAll(ChatColor.GREEN + "shown");
                    return true;
                } else {
                    sendToSender(sender,ChatColor.RED + "already shown");
                    return false;
                }
            }
            case "hide" -> {
                if (!timer.isHidden()) {
                    timer.setHidden(true);
                    sendToAll(ChatColor.GREEN + "hidden");
                    return true;
                } else {
                     sendToSender(sender,ChatColor.RED + "already hidden");
                    return false;
                }
            }
            default -> {
                sendUsage(sender);
                return false;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        ArrayList<String> list = new ArrayList<>();
        Timer timer = Main.getInstance().getTimer();
        if (args.length == 0) {
            return list;
        }
        if (args.length == 1) {
            if (timer.isHidden()) {
                list.add("show");
            } else {
                list.add("hide");
                if (timer.isRunning()) {
                    list.add("pause");
                    list.remove("hide");
                } else {
                    list.add("resume");
                    list.add("set");
                    list.add("reset");
                }
            }
        }
        ArrayList<String> comList = new ArrayList<>();
        String current = args[args.length-1].toLowerCase();
        for (String s1 : list) {
            if (s1.startsWith(current)) {
                comList.add(s1);
            }
        }
        return comList;
    }

    public static void sendUsage(CommandSender sender) {
        sendToSender(sender,"""
                Usage:
                /timer resume
                /timer pause
                /timer set <time>
                /timer reset
                /timer show
                /timer hide"""
        );
    }
    public static void noPermission(CommandSender sender) {
        sendToSender(sender,ChatColor.RED + "Your don't have to permission to do this");
    }
    public static void notPossible(CommandSender sender) {
        sendToSender(sender,ChatColor.RED + "this is currently not possible.");
    }
    public static void sendToSender(CommandSender sender, String message) {
        sender.sendMessage(Main.getPrefix() + " " + message);
    }
    public static void sendToAll(String message) {
        Main.getInstance().getServer().broadcastMessage(Main.getPrefix() + " " + message);
    }
}
