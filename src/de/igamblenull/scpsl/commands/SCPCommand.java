package de.igamblenull.scpsl.commands;

import de.igamblenull.scpsl.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SCPCommand implements CommandExecutor {
    private final Main main;

    public SCPCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0) {
            sendHelp(sender);
        }else {
            switch(args[0].toLowerCase()) {
                case "join":
                    if(sender instanceof Player) {
                        main.gameManager.joinPlayer((Player) sender);
                    }else {
                        sender.sendMessage("[SCP:SL] You must be a player!");
                    }
                    break;

                case "leave":
                    if(sender instanceof Player) {
                        main.gameManager.leavePlayer((Player) sender);
                    }else {
                        sender.sendMessage("[SCP:SL] You must be a player!");
                    }
                    break;

                case "set":
                    if(args.length >= 2) {
                        switch (args[1].toLowerCase()) {
                            case "spawn":
                                if(sender instanceof Player) {
                                    Player p = (Player) sender;
                                    if(args.length == 3) {
                                        if(main.gameManager.factions.containsKey(args[2].toLowerCase())) {
                                            main.gameManager.factions.get(args[2].toLowerCase()).addSpawnPoint(p.getLocation().toVector());
                                            main.config.set("spawnpoints." + args[2].toLowerCase(), main.gameManager.factions.get(args[2].toLowerCase()).getSpawnPoint());
                                            main.saveConfig();
                                        }else {
                                            sendHelp(sender);
                                        }
                                    }else {
                                        sendHelp(sender);
                                    }
                                }else {
                                    sender.sendMessage("[SCP:SL] You must be a player!");
                                }
                                break;
                            case "lobbytime":
                                if(args.length == 3) {
                                    main.config.set("lobbytime", Integer.parseInt(args[2]));
                                    main.saveConfig();
                                }else {
                                    sendHelp(sender);
                                }
                                break;

                            case "minplayer":
                                if(args.length == 3) {
                                    main.config.set("minPlayer", Integer.parseInt(args[2]));
                                    main.saveConfig();
                                }else {
                                    sendHelp(sender);
                                }
                                break;

                            case "maxplayer":
                                if(args.length == 3) {
                                    if(main.config.getInt("minPlayer") <= Integer.parseInt(args[2])) {
                                        main.config.set("maxPlayer", Integer.parseInt(args[2]));
                                        main.saveConfig();
                                    }else {
                                        sender.sendMessage("[SCP:SL] The maximum players has to be greater or equals than the minimum players!");
                                    }
                                }else {
                                    sendHelp(sender);
                                }
                                break;

                            default:
                                sendHelp(sender);
                        }
                    }else {
                        sendHelp(sender);
                    }
                    break;

                default:
                    sendHelp(sender);
            }
        }
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("Help Page");
    }
}