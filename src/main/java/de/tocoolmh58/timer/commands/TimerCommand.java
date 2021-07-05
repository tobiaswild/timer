package de.tocoolmh58.timer.commands;

import de.tocoolmh58.timer.Main;
import de.tocoolmh58.timer.timer.Timer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TimerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("timer.command")) {
            noPermission(sender);
            return false;
        }
        if (args.length == 0) {
            sendUsage(sender);
            return false;
        }
        switch (args[0].toLowerCase()) {
            case "resume": {
                if (!sender.hasPermission("timer.resume")) {
                    noPermission(sender);
                    return false;
                }
                Timer timer = Main.getInstance().getTimer();
                if (!timer.isHidden()) {
                    if (timer.isRunning()) {
                        sender.sendMessage(ChatColor.RED + "Der Timer läuft bereits");
                        return false;
                    }
                    timer.setRunning(true);
                    sender.sendMessage(ChatColor.GREEN + "Der Timer wurde gestartet");
                    return true;
                }
                notPossible(sender);
                return false;
            }
            case "pause": {
                if (!sender.hasPermission("timer.pause")) {
                    noPermission(sender);
                    return false;
                }
                Timer timer = Main.getInstance().getTimer();
                if (!timer.isHidden()) {
                    if (!timer.isRunning()) {
                        sender.sendMessage(ChatColor.RED + "Der Timer ist bereits angehalten");
                        return false;
                    }
                    timer.setRunning(false);
                    sender.sendMessage(ChatColor.GREEN + "Der Timer wurde gestoppt");
                    return true;
                }
                notPossible(sender);
                return false;
            }
            case "set": {
                if (!sender.hasPermission("timer.set")) {
                    noPermission(sender);
                    return false;
                }
                Timer timer = Main.getInstance().getTimer();
                if (!timer.isHidden()) {
                    if (args.length != 2) {
                        sender.sendMessage("Verwendung: /timer time <time>");
                        return false;
                    }
                    try {
                        timer.setRunning(false);
                        timer.setTime(Integer.parseInt(args[1]));
                        sender.sendMessage(ChatColor.GREEN + "Die Zeit wurde auf " + args[1] + " gesetzt!");
                    } catch (NumberFormatException e) {
                        sender.sendMessage(ChatColor.RED + "Deine 2. Parameter muss eine Zahl sein!");
                    }
                    break;
                }
                return false;
            }
            case "reset":{
                if (!sender.hasPermission("timer.reset")) {
                    noPermission(sender);
                    return false;
                }
                Timer timer = Main.getInstance().getTimer();
                if (!timer.isHidden()) {
                    timer.setRunning(false);
                    timer.setTime(0);
                    sender.sendMessage(ChatColor.GREEN + "Der Timer wurde zurückgesetzt");
                    return true;
                }
                return false;
            }
            case "show": {
                if (!sender.hasPermission("timer.show")) {
                    noPermission(sender);
                    return false;
                }
                Timer timer = Main.getInstance().getTimer();
                if (timer.isHidden()) {
                    timer.setHidden(false);
                    sender.sendMessage(ChatColor.GREEN + "Der Timer wurde eingeblendet");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Der Timer ist bereits eingeblendet");
                    return false;
                }
            }
            case "hide": {
                if (!sender.hasPermission("timer.hide")) {
                    noPermission(sender);
                    return false;
                }
                Timer timer = Main.getInstance().getTimer();
                if (!timer.isHidden()) {
                    timer.setHidden(true);
                    sender.sendMessage(ChatColor.GREEN + "Der Timer wird ausgeblendet");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.RED + "Der Timer ist bereits ausgeblendet");
                    return false;
                }
            }
            default:
                sendUsage(sender);
                return false;
        }
        return false;
    }

    public static void sendUsage(CommandSender sender) {
        sender.sendMessage(
                ChatColor.LIGHT_PURPLE +
                        "Verwendung: " +
                        "/timer resume, " +
                        "/timer pause, " +
                        "/timer time <time>, " +
                        "/timer reset, " +
                        "/timer show, " +
                        "/timer hide"
        );
    }
    public static void noPermission(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Dazu hast du keine Rechte");
    }
    public static void notPossible(CommandSender sender) {
        sender.sendMessage(ChatColor.RED + "Das geht leider nicht");
    }
}
