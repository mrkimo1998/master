package me.mrkimo.kimotools;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.StringTokenizer;
import java.util.UUID;
import java.lang.Integer;
import java.lang.Byte;
import java.lang.Short;
import java.lang.Double;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.Sound;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.GameMode;
import me.mrkimo.kimotools.WarpManager;
import me.mrkimo.kimotools.HomeManager;
import me.mrkimo.kimotools.kimotools;

public class ComManager {

    WarpManager warpMgr;
    HomeManager homeMgr;
    JavaPlugin plg;
    final String path1 = "Configuration.serverteam";
    final String path2 = "Configuration.owner";
    String serverteam;
    String owner;

    public ComManager(JavaPlugin plg, WarpManager warpMgr, HomeManager homeMgr) {
        this.plg = plg;
        this.warpMgr = warpMgr;
        this.homeMgr = homeMgr;
        serverteam = plg.getConfig().getString(path1);
        owner = plg.getConfig().getString(path2);
    }

    public boolean command(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
        Player p = null;
	    if(sender instanceof Player){ p = (Player)sender; }
        UUID uuid = p.getUniqueId();
        double health;

        //serverteam command
        if(cmd.getName().equalsIgnoreCase("serverteam")){
            if(p == null){ System.out.println("[KimoTools] Not a console command!"); return true; }
            if(args.length != 0){ p.sendMessage(ChatColor.RED + "ERROR: zu viele Argumente!"); return false; }
            p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Das Serverteam besteht aus " + ChatColor.RED + ChatColor.UNDERLINE + serverteam + ChatColor.RESET + ChatColor.GREEN + ".");
            return true;
        }
        //gethealth command
        if(cmd.getName().equalsIgnoreCase("gethealth")){
            if(p == null){ System.out.println("[KimoTools] Not a console command!"); return true; }
            if(!p.hasPermission("kimotools.gethealth")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true; }
            if(args.length > 1){ p.sendMessage(ChatColor.RED + "ERROR: Zu viele Argumente!"); return false; }
            else if(args.length == 0){
                health = p.getHealth();
                p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Ihr Leben liegt bei " + ChatColor.RED + health/2 + ChatColor.GREEN + " Herzen.");
                return true;
            }
            else if(args.length == 1){
                for(Player curp : plg.getServer().getOnlinePlayers()){
                    if(curp.getName().equalsIgnoreCase(args[0])){
                        health = curp.getHealth();
                        p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Die Leben des Spielers " + ChatColor.AQUA + curp.getName() + ChatColor.GREEN + " liegen bei " + ChatColor.RED + health/2 + ChatColor.GREEN + " Herzen.");
                        return true;
                    }
                }
                p.sendMessage(ChatColor.RED + " ERROR: Spieler offline!");
                return true;
            }
            return false;
        }
        //setmaxhealth command
        if(cmd.getName().equalsIgnoreCase("setmaxhealth")){
            if(p == null){ System.out.println("[KimoTools] Not a console command!"); return true;}
            if(!p.hasPermission("kimotools.setmaxhealth")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return false;}
            if(args.length > 2){ p.sendMessage(ChatColor.RED + "ERROR: Zu viele Argumente!"); return false; }
            else if(args.length == 1){
                try {
                    health = Integer.parseInt(args[0])*2;
                    p.setMaxHealth(health);
                    p.playSound(p.getLocation(), (Sound) Sound.ENTITY_PLAYER_LEVELUP, (float) 1, (float) 1);
                    p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Ihre Maximalen-Leben wurde auf " + ChatColor.RED + health/2 + ChatColor.GREEN + " Herzen gesetzt.");
                    return true;
                } catch(IllegalArgumentException e) {
                    p.sendMessage(ChatColor.RED + "ERROR: Keine Zahl!");
                    return false;
                }
            }
            else if(args.length == 2){
                try{
                    for(Player curp : plg.getServer().getOnlinePlayers()){
                        if(curp.getName().equalsIgnoreCase(args[1])){
                            health = Integer.parseInt(args[0])*2;
                            curp.setMaxHealth(health);
                            curp.playSound(curp.getLocation(), (Sound) Sound.ENTITY_PLAYER_LEVELUP, (float) 1, (float) 1);
                            curp.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Deine Maximalen-Leben wurden auf " + ChatColor.AQUA + health/2 + ChatColor.GREEN + " gesetzt!");
                            p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Die Maximalen-Leben des Spielers " + ChatColor.AQUA + curp.getName() + ChatColor.GREEN + " wurden auf " + ChatColor.RED + health/2 + ChatColor.GREEN + " Herzen gesetzt.");
                            return true;
                        }
                    }
                } catch(IllegalArgumentException e) {
                    p.sendMessage(ChatColor.RED + "ERROR: Keine Zahl!");
                    return false;
                }
                p.sendMessage(ChatColor.RED + " ERROR: Spieler offline!");
                return true;
            }
            return false;
        }
        //sethealth command
        if(cmd.getName().equalsIgnoreCase("sethealth")){
            if(p == null){ System.out.println("[KimoTools] Not a console command!"); return true;}
            if(!p.hasPermission("kimotools.sethealth")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return false;}
            if(args.length > 2){ p.sendMessage(ChatColor.RED + "ERROR: Zu viele Argumente!"); return false; }
            else if(args.length == 1){
                try {
                    if(Integer.parseInt(args[0])*2 > p.getMaxHealth()){
                        health = p.getMaxHealth();
                    } else {
                        health = Integer.parseInt(args[0])*2;
                    }
                    p.setHealth(health);
                    p.playSound(p.getLocation(), (Sound) Sound.ENTITY_PLAYER_LEVELUP, (float) 1, (float) 1);
                    p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Ihr Leben wurde auf " + ChatColor.RED + health/2 + ChatColor.GREEN + " Herzen gesetzt.");
                    return true;
                } catch(IllegalArgumentException e) {
                    p.sendMessage(ChatColor.RED + "ERROR: Keine Zahl!");
                    return false;
                }
            }
            else if(args.length == 2){
                try{
                    for(Player curp : plg.getServer().getOnlinePlayers()){
                        if(curp.getName().equalsIgnoreCase(args[1])){
                            if(Integer.parseInt(args[0])*2 > curp.getMaxHealth()){
                                health = curp.getMaxHealth();
                            } else {
                                health = Integer.parseInt(args[0])*2;
                            }
                            curp.setHealth(health);
                            curp.playSound(curp.getLocation(), (Sound) Sound.ENTITY_PLAYER_LEVELUP, (float) 1, (float) 1);
                            curp.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Deine Leben wurden auf " + ChatColor.AQUA + health/2 + ChatColor.GREEN + " gesetzt!");
                            p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Die Leben des Spielers " + ChatColor.AQUA + curp.getName() + ChatColor.GREEN + " wurden auf " + ChatColor.RED + health/2 + ChatColor.GREEN + " Herzen gesetzt.");
                            return true;
                        }
                    }
                } catch(IllegalArgumentException e) {
                    p.sendMessage(ChatColor.RED + "ERROR: Keine Zahl!");
                    return false;
                }
                p.sendMessage(ChatColor.RED + " ERROR: Spieler offline!");
                return true;
            }
            return false;
            }
        //aflame command
        if(cmd.getName().equalsIgnoreCase("aflame")){
            if(p == null){ System.out.println("[KimoTools] Not a console command!"); return true; }
            if(!p.hasPermission("kimotools.aflame")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true; }
            if(args.length > 1){ p.sendMessage(ChatColor.RED + "ERROR: Zu viele Argumente!"); return false;}
            else if(args.length == 0){
                p.setFireTicks(200);
                p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + "Du hast dich selbst in Brand gesteckt! #FIRECHALLENGE");
                return true;
            }
            else if(args.length == 1){
                for(Player curp : plg.getServer().getOnlinePlayers()){
                    if(curp.getName().equalsIgnoreCase(args[0])){
                        curp.setFireTicks(200);
                        p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Der Spieler " + ChatColor.AQUA + curp.getName() + ChatColor.GREEN + " wurde in Brand gesetzt.");
                        curp.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Der Spieler " + ChatColor.AQUA + p.getName() + ChatColor.GREEN + " hat dich in Brand gesetzt.");
                        return true;
                    }
                }
                p.sendMessage(ChatColor.RED + " ERROR: Spieler offline!");
                return true;
            }
        }
        //beam command
        if(cmd.getName().equalsIgnoreCase("tp")) {
            if(p != null){
                if(args.length == 0){ p.sendMessage(ChatColor.RED + "ERROR: Zu wenig Argumente!"); return false;}
                else if (args.length == 1) {
                    if(p.hasPermission("kimotools.tp")){
                        for(Player curp : plg.getServer().getOnlinePlayers()){
                            if(curp.getName().equalsIgnoreCase(args[0])){
                                p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Beame zu " + ChatColor.AQUA + curp.getName());
                                p.playSound(curp.getLocation(), (Sound) Sound.ENTITY_ENDERMEN_TELEPORT, (float) 1, (float) 1);
                                curp.playSound(curp.getLocation(), (Sound) Sound.ENTITY_ENDERMEN_TELEPORT, (float) 1, (float) 1);
                                p.teleport(curp.getLocation());
                                p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Sie haben ihr Ziel erreicht!");
                                curp.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.AQUA + " " + p.getName() + ChatColor.GREEN + " hat sich zu ihnen gebeamt!");
                                return true;
                            }
                        }
                        p.sendMessage(ChatColor.RED + "ERROR: Spieler offline!");
                        return true;
                    } else { p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true;}
                }
                else if (args.length == 2) {
                    if(p.hasPermission("kimotools.tp.others")){
                        for(Player target1 : plg.getServer().getOnlinePlayers()){
                            if(target1.getName().equalsIgnoreCase(args[0])){
                                for(Player target2 : plg.getServer().getOnlinePlayers()){
                                    if(target2.getName().equalsIgnoreCase(args[1])){
                                        target1.teleport(target2.getLocation());
                                        target1.playSound(target2.getLocation(), (Sound) Sound.ENTITY_ENDERMEN_TELEPORT, (float) 1, (float) 1);
                                        target2.playSound(target2.getLocation(), (Sound) Sound.ENTITY_ENDERMEN_TELEPORT, (float) 1, (float) 1);
                                        target1.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Sie wurden zu " + ChatColor.AQUA + target2.getName() + ChatColor.GREEN + " gebeamt!");
                                        target2.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.AQUA + " " + target1.getName() + ChatColor.GREEN + " wurde zu ihnen gebeamt");
                                        return true;
                                    }
                                }
                            }
                        }
                        p.sendMessage(ChatColor.RED + "ERROR: Spieler offline!");
                        return true;
                    } else { p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true;}
                } else { p.sendMessage(ChatColor.RED + "ERROR: Zu viele Argumente!"); return false;}
            } else { System.out.println("[KimoTools] Not a console command!"); return true; }
        }
        //beam2me command
        if(cmd.getName().equalsIgnoreCase("tphere") || cmd.getName().equalsIgnoreCase("s")){
            if(p == null) { System.out.println("[KimoTools] Not a console command"); return true; }
            if(args.length != 1) { p.sendMessage(ChatColor.RED + "ERROR Es muss ein Argument angegeben werden!"); return false; }
            if(!p.hasPermission("kimotools.tp.here")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true;}
            for(Player curp : plg.getServer().getOnlinePlayers()){
                if(curp.getName().equalsIgnoreCase(args[0])){
                    p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Beame " + ChatColor.AQUA + curp.getName() + ChatColor.GREEN + " zu dir.");
                    curp.teleport(p.getLocation());
                    curp.playSound(p.getLocation(), (Sound) Sound.ENTITY_ENDERMEN_TELEPORT, (float) 1, (float) 1);
                    p.playSound(p.getLocation(), (Sound) Sound.ENTITY_ENDERMEN_TELEPORT, (float) 1, (float) 1);
                    p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Beam erfolgreich!");
                    curp.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.AQUA + " " + p.getName() + ChatColor.GREEN + " hat dich zu ihm gebeamt!");
                    return true;
                }
                p.sendMessage(ChatColor.RED + "ERROR: Spieler offline!");
                return true;
            }
        }
        //warp command
        if(cmd.getName().equalsIgnoreCase("warp")){
            if(p == null){ System.out.println("[KimoTools] Not a console command!"); return true;}
            if(args.length != 1){ p.sendMessage(ChatColor.RED + "ERROR: Es muss ein Argument angegeben werden!"); return false; }
            if(!p.hasPermission("kimotools.warp")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung! "); return true;}
            Location warpLoc = warpMgr.getWarp(args[0]);
            if(warpLoc == null) { p.sendMessage(ChatColor.RED + "ERROR: Dieser Warp existiert nicht!"); return true;}
            p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Beame zu Warp: " + ChatColor.AQUA + args[0]);
            p.teleport(warpLoc);
            p.playSound(warpLoc, (Sound) Sound.ENTITY_ENDERMEN_TELEPORT, (float) 1, (float) 1);
            p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Sie haben ihr Ziel erreicht!");
        }
        //setwarp command
        if(cmd.getName().equalsIgnoreCase("setwarp")){
            if(p == null){ System.out.println("[KimoTools] Not a console command!"); return true;}
            if(args.length != 1){ p.sendMessage(ChatColor.RED +  "ERROR: Es muss ein Argument angegeben werden!"); return false; }
            if(!p.hasPermission("kimotools.warp.mod")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true;}
            p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Setze Warp...");
            if(warpMgr.addWarp(args[0], p.getLocation()) == 0){
                p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Warp: " + ChatColor.AQUA + args[0] + ChatColor.GREEN + " gesetzt!");
            } else { p.sendMessage(ChatColor.RED + "ERROR: Warp existiert bereits oder sonstiger Fehler!"); return true;}
        }
        //delwarp command
        if(cmd.getName().equalsIgnoreCase("delwarp")){
            if(p == null){ System.out.println("[KimoTools] Not a console command!"); return true;}
            if(args.length != 1){ p.sendMessage(ChatColor.RED +  "ERROR: Es muss ein Argument angegeben werden!"); return false; }
            if(!p.hasPermission("kimotools.warp.mod")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true;}
            warpMgr.removeWarp(args[0]);
            p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Warp: " + ChatColor.AQUA + args[0] + ChatColor.GREEN + " gelöscht!");
        }
        //warplist command
        if (cmd.getName().equalsIgnoreCase("warps")){
            if(p == null){ System.out.println("[KimoTools] Not a console command!"); return true;}
            if(args.length != 0){ p.sendMessage(ChatColor.RED +  "ERROR: Es dürfen keine Argument angegeben werden!"); return false; }
            if(!p.hasPermission("kimotools.warp")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true;}
            String str_warps = "";
            Object[] obj_warps = warpMgr.getWarpList();
            for(int i = 0; i < obj_warps.length; i++){
                str_warps += String.valueOf(obj_warps[i]);
                if(!(i == obj_warps.length - 1)){
                    str_warps += ", ";
                }
            }
            p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Warps: " + ChatColor.AQUA + str_warps);
        }
        //home command
        if(cmd.getName().equalsIgnoreCase("home")){
            if(p == null){ System.out.println("[KimoTools] Not a console command!"); return true;}
            if(args.length != 1){ p.sendMessage(ChatColor.RED + "ERROR: Es muss ein Argument angegeben werden!"); return false; }
            if(!p.hasPermission("kimotools.home")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung! "); return true;}
            Location homeLoc = homeMgr.getHome(uuid, args[0]);
            if(homeLoc == null) { p.sendMessage(ChatColor.RED + "ERROR: Dieses Zuhause existiert nicht!"); return true;}
            p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Beame zu Zuhause: " + ChatColor.AQUA + args[0]);
            p.teleport(homeLoc);
            p.playSound(homeLoc, (Sound) Sound.ENTITY_ENDERMEN_TELEPORT, (float) 1, (float) 1);
            p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Sie haben ihr Ziel erreicht!");
        }
        //sethome command
        if(cmd.getName().equalsIgnoreCase("sethome")){
            if(p == null){ System.out.println("[KimoTools] Not a console command!"); return true;}
            if(args.length != 1){ p.sendMessage(ChatColor.RED +  "ERROR: Es muss ein Argument angegeben werden!"); return false; }
            if(!p.hasPermission("kimotools.home")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true;}
            p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Setze Zuhause...");
            if(homeMgr.addHome(uuid, args[0], p.getLocation()) == 0){
                p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Zuhause: " + ChatColor.AQUA + args[0] + ChatColor.GREEN + " gesetzt!");
            } else { p.sendMessage(ChatColor.RED + "ERROR: Zuhause existiert bereits oder sonstiger Fehler!"); return true;}
        }
        //delhome command
        if(cmd.getName().equalsIgnoreCase("delhome")){
            if(p == null){ System.out.println("[KimoTools] Not a console command!"); return true;}
            if(args.length != 1){ p.sendMessage(ChatColor.RED +  "ERROR: Es muss ein Argument angegeben werden!"); return false; }
            if(!p.hasPermission("kimotools.home")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true;}
            homeMgr.removeHome(uuid, args[0]);
            p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Zuhause: " + ChatColor.AQUA + args[0] + ChatColor.GREEN + " gelöscht!");
        }
        //homelist command
        if (cmd.getName().equalsIgnoreCase("homes")){
            String str_uuid = uuid.toString();
            if(p == null){ System.out.println("[KimoTools] Not a console command!"); return true;}
            if(args.length != 0){ p.sendMessage(ChatColor.RED +  "ERROR: Es dürfen keine Argument angegeben werden!"); return false; }
            if(!p.hasPermission("kimotools.home")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true;}
            String str_homes = "";
            Object[] obj_homes = homeMgr.getHomeList();
            for(byte i = 0; i < obj_homes.length; i++){
                if(String.valueOf(obj_homes[i]).contains(str_uuid)){
                    str_homes += String.valueOf(obj_homes[i]);
                    if(!(i == obj_homes.length - 1)){
                        str_homes += ", ";
                    }
                }
            }
            if(str_homes.contains(str_uuid)) {
                    str_homes = str_homes.replace(str_uuid,"");
            }
            p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Ihre R\u00fcckzugsorte: " + ChatColor.AQUA + str_homes);
        }
        //i (item) command
        if (cmd.getName().equalsIgnoreCase("i") || cmd.getName().equalsIgnoreCase("item")){
            if(p==null){ System.out.println("[KimoTools] Not a console command!"); return true;}
            if(args.length < 1){ p.sendMessage(ChatColor.RED + "ERROR: Es muss mindestens 1 Argument angegeben werden!"); return false;}
            if(args[0].equalsIgnoreCase("give")){
                if(!p.hasPermission("kimotools.item.give")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true;}
                if(args.length != 3){p.sendMessage(ChatColor.RED + "ERROR: Es müssen mindestens 3 Argument angegeben werden!"); return false;}
                Material mat = null;
                mat = mat.getMaterial(args[1].toUpperCase());
                if(mat == null){p.sendMessage(ChatColor.RED + "ERROR: Item nicht gefunden!"); return true;}
                try {
                    int amount = Integer.parseInt(args[2]);
                    ItemStack item = new ItemStack(mat, amount);
                    try{
                        p.getInventory().addItem(item);
                        p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Das Item wurde ihrem Inventar erfolgreich hinzugefügt!");
                        return true;
                    } catch(IllegalArgumentException e) {
                        p.sendMessage(ChatColor.RED + "ERROR: Item nicht gefunden!");
                        return true;
                    }
                } catch(NumberFormatException e){
                    p.sendMessage(ChatColor.RED + "ERROR: Die Anzahl muss eine Zahl sein!");
                    return true;
                }
                }
            if(args[0].equalsIgnoreCase("enchant")){
                if(!p.hasPermission("kimotools.item.enchant")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true;}
                if(args.length != 3){p.sendMessage(ChatColor.RED + "ERROR: Es müssen mindestens 3 Argument angegeben werden!"); return false;}
                ItemStack inHand = p.getInventory().getItemInMainHand();
                Enchantment ench = null;
                ench = ench.getByName(args[1].toUpperCase());
                if(ench == null){p.sendMessage(ChatColor.RED + "ERROR: Verzauberung nicht gefunden!"); return true;}
                try {
                    int ench_lvl = Integer.parseInt(args[2]);
                    inHand.addUnsafeEnchantment(ench, ench_lvl);
                    p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Verzauberung " + ChatColor.AQUA + ench.toString() + ChatColor.GREEN + " wurde zu " + ChatColor.AQUA + inHand.getData().getItemType().toString() + ChatColor.GREEN + " hinzugefügt!");
                    return true;
                } catch(NumberFormatException e){
                    p.sendMessage(ChatColor.RED + "ERROR: Das Verzauberunglevel muss eine Zahl sein!");
                    return true;
                }
            }
            if(args[0].equalsIgnoreCase("clone")){
                if(!p.hasPermission("kimotools.item.clone")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true;}
                if(args.length != 1){ p.sendMessage(ChatColor.RED +  "ERROR: Es muss ein Argument angegeben werden!"); return false; }
                p.getInventory().addItem(p.getInventory().getItemInMainHand());
                p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Das Item wurde erfolgreich geklont und ihrem Inventar hinzugefügt!");
                return true;
            } else if(args[0].equalsIgnoreCase("info")){
                if(args.length != 1){ p.sendMessage(ChatColor.RED +  "ERROR: Es muss ein Argument angegeben werden!"); return false; }
                String str_info = p.getInventory().getItemInMainHand().toString();
                p.sendMessage("[WIP] " + str_info);
                return true;
            }
            if(args[0].equalsIgnoreCase("list")){
                if(args.length != 1){ p.sendMessage(ChatColor.RED +  "ERROR: Es muss ein Argument angegeben werden!"); return false; }
                p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Verzauberungen: " + ChatColor.AQUA + "https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/enchantments/Enchantment.html");
                p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Items/Bl\u00f6cke: " + ChatColor.AQUA + "https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html");
                return true;
            }
            p.sendMessage(ChatColor.RED + "ERROR: Sub-befehl nicht gefunden");
            return false;
        }
        //setspawn
        if(cmd.getName().equalsIgnoreCase("setspawn")){
            if(p==null){ System.out.println("[KimoTools] Not a console command!"); return true;}
            if(!p.hasPermission("kimotools.spawn.set")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true;}
            if(args.length == 0){
                Location loc = p.getLocation();
                p.sendMessage(ChatColor.GOLD + "[KimoTools] " + ChatColor.GREEN + "Der Spawnpunkt für diese Welt wird an ihrem Standort gesetzt!");
                if(loc.getWorld().setSpawnLocation((int) loc.getX(), (int) loc.getY(), (int) loc.getZ()) == true){
                    p.sendMessage(ChatColor.GOLD + "[KimoTools] " + ChatColor.GREEN + "Spawnpunkt erfolgreich gesetzt!");
                    p.sendMessage(ChatColor.GOLD + "[KimoTools] " + ChatColor.GREEN + "Spawn: " + ChatColor.AQUA + loc.toString());
                    return true;
                } else {
                    p.sendMessage(ChatColor.RED + "ERROR: Fehler!");
                    return true;
                }
            } else { p.sendMessage(ChatColor.RED +  "ERROR: Es muss darf kein Argument angegeben werden!"); return false;}
        }
        //spawn
        if(cmd.getName().equalsIgnoreCase("spawn")){
            if(p==null){ System.out.println("[KimoTools] Not a console command!"); return true;}
            if(args.length != 0){p.sendMessage(ChatColor.RED +  "ERROR: Es darf kein Argument angegeben werden!"); return false; }
            p.sendMessage(ChatColor.GOLD + "[KimoTools] " + ChatColor.GREEN + "Beame zum Spawn...");
            p.teleport(p.getWorld().getSpawnLocation());
            p.playSound(p.getWorld().getSpawnLocation(), (Sound) Sound.ENTITY_ENDERMEN_TELEPORT, (float) 1, (float) 1);
            p.sendMessage(ChatColor.GOLD + "[KimoTools] " + ChatColor.GREEN + "Sie haben ihr Ziel erreicht!");
        }
        //whois
        if(cmd.getName().equalsIgnoreCase("whois")){
            if(p==null){ System.out.println("[KimoTools] Not a console command!"); return true;}
            if(!p.hasPermission("kimotools.whois")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true;}
            if(args.length != 1){p.sendMessage(ChatColor.RED + "ERROR: Es muss ein Argument angegeben werden!"); return false;}
            if(args.length == 1){
                for(Player curp : plg.getServer().getOnlinePlayers()){
                    if(curp.getName().equalsIgnoreCase(args[0])){
                        p.sendMessage(ChatColor.GREEN + "Info zu Spieler: " + ChatColor.AQUA + curp.getName());
                        p.sendMessage(ChatColor.GREEN + "Name: " + ChatColor.AQUA + curp.getName());
                        p.sendMessage(ChatColor.GREEN + "IP: " + ChatColor.AQUA + curp.getAddress().toString());
                        p.sendMessage(ChatColor.GREEN + "UUID: " + ChatColor.AQUA + curp.getUniqueId().toString());
                        p.sendMessage(ChatColor.GREEN + "Position:" + ChatColor.AQUA + curp.getLocation().toString());
                        p.sendMessage(ChatColor.GREEN + "Leben: " + ChatColor.AQUA + (curp.getHealth() / 2));
                        return true;
                    }
                    else{
                       p.sendMessage(ChatColor.RED + "ERROR: Spieler offline!");
                        return true;
                    }
                }
            }
        }
        //gamemode
	      if(cmd.getName().equalsIgnoreCase("gamemode") || cmd.getName().equalsIgnoreCase("gm")){
            if(p==null){ System.out.println("[KimoTools] Not a console command!"); return true;}
            if(args.length == 0 || args.length > 2){p.sendMessage(ChatColor.RED + "ERROR: Es muss ein/zwei Argument/e angegeben werden!"); return false;}
            if(args.length == 1){
                if(!p.hasPermission("kimotools.gamemode")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true;}
                if(args[0].equalsIgnoreCase("survival") || args[0].equals("0")){
                    p.setGameMode(GameMode.SURVIVAL);
                    p.sendMessage(ChatColor.GOLD + "[KimoTools] " + ChatColor.GREEN + "Gamemode gesetzt zu " + ChatColor.AQUA + "Survival");
                    return true;
                } else if(args[0].equalsIgnoreCase("creative") || args[0].equals("1")){
                    p.setGameMode(GameMode.CREATIVE);
                    p.sendMessage(ChatColor.GOLD + "[KimoTools] " + ChatColor.GREEN + "Gamemode gesetzt zu " + ChatColor.AQUA + "Creative");
                    return true;
                } else if(args[0].equalsIgnoreCase("adventure") || args[0].equals("2")){
                    p.setGameMode(GameMode.ADVENTURE);
                    p.sendMessage(ChatColor.GOLD + "[KimoTools] " + ChatColor.GREEN + "Gamemode gesetzt zu " + ChatColor.AQUA + "Adventure");
                    return true;
                } else if(args[0].equalsIgnoreCase("spectator") || args[0].equals("3")){
                    p.setGameMode(GameMode.SPECTATOR);
                    p.sendMessage(ChatColor.GOLD + "[KimoTools] " + ChatColor.GREEN + "Gamemode gesetzt zu " + ChatColor.AQUA + "Spectator");
                    return true;
                } else { p.sendMessage(ChatColor.RED + "ERROR: Kein Gamemode!"); return false;}
            }
            if(args.length == 2){
            if(!p.hasPermission("kimotools.gamemode.others")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true;}
		          for(Player curp : plg.getServer().getOnlinePlayers()){
          			if(curp.getName().equalsIgnoreCase(args[1])){
          			    if(args[0].equalsIgnoreCase("survival") || args[0].equals("0")){
          				    curp.setGameMode(GameMode.SURVIVAL);
          				    curp.sendMessage(ChatColor.GOLD + "[KimoTools] " + ChatColor.GREEN + "Gamemode gesetzt zu " + ChatColor.AQUA + "Survival" + ChatColor.GREEN + " durch " + ChatColor.AQUA + p.getName());
          				    p.sendMessage(ChatColor.GOLD + "[KimoTools] " + ChatColor.GREEN + "Gamemode von " + ChatColor.AQUA + curp.getName() + ChatColor.GREEN + " gesetzt zu " + ChatColor.AQUA + "Survival");
          				    return true;
          				} else if(args[0].equalsIgnoreCase("creative") || args[0].equals("1")){
          				    curp.setGameMode(GameMode.CREATIVE);
          				    curp.sendMessage(ChatColor.GOLD + "[KimoTools] " + ChatColor.GREEN + "Gamemode gesetzt zu " + ChatColor.AQUA + "Creative" + ChatColor.GREEN + " durch " + ChatColor.AQUA + p.getName());
          				    p.sendMessage(ChatColor.GOLD + "[KimoTools] " + ChatColor.GREEN + "Gamemode von " + ChatColor.AQUA + curp.getName() + ChatColor.GREEN + " gesetzt zu " + ChatColor.AQUA + "Creative");
          				    return true;
          				} else if(args[0].equalsIgnoreCase("adventure") || args[0].equals("2")){
          				    curp.setGameMode(GameMode.ADVENTURE);
          				    curp.sendMessage(ChatColor.GOLD + "[KimoTools] " + ChatColor.GREEN + "Gamemode gesetzt zu " + ChatColor.AQUA + "Adventure" + ChatColor.GREEN + " durch " + ChatColor.AQUA + p.getName());
          				    p.sendMessage(ChatColor.GOLD + "[KimoTools] " + ChatColor.GREEN + "Gamemode von " + ChatColor.AQUA + curp.getName() + ChatColor.GREEN + " gesetzt zu " + ChatColor.AQUA + "Adventure");
          				    return true;
          				} else if(args[0].equalsIgnoreCase("spectator") || args[0].equals("3")){
          				    curp.setGameMode(GameMode.SPECTATOR);
          				    curp.sendMessage(ChatColor.GOLD + "[KimoTools] " + ChatColor.GREEN + "Gamemode gesetzt zu " + ChatColor.AQUA + "Spectator" + ChatColor.GREEN + " durch " + ChatColor.AQUA + p.getName());
          				    p.sendMessage(ChatColor.GOLD + "[KimoTools] " + ChatColor.GREEN + "Gamemode von " + ChatColor.AQUA + curp.getName() + ChatColor.GREEN + " gesetzt zu " + ChatColor.AQUA + "Spectator");
          				    return true;
          				} else { p.sendMessage(ChatColor.RED + "ERROR: Kein Gamemode!"); return false;}
                }
              }
              p.sendMessage(ChatColor.RED + "ERROR: Spieler offline!");
              return true;
            }
        }return true;
    }
}
