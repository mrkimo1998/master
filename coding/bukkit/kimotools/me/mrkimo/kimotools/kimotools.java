package me.mrkimo.kimotools;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.StringTokenizer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

public class kimotools extends JavaPlugin implements Listener {
	
	WarpManager warpMgr;
	final String path1 = "Configuration.serverteam";
	final String path2 = "Configuration.owner";

	@Override
	public void onDisable() {
		System.out.println("[KimoTools] Plugin wurde deaktiviert.");
	}
	
	@Override
	public void onEnable() {
		System.out.println("==================================");
		System.out.println("[KimoTools] Plugin wird geladen.");
		//Config
		loadConfig();
		//WarpManager
		warpMgr = new WarpManager("warps.hashmap", this);
		//Listener
		getServer().getPluginManager().registerEvents(this, this);
		System.out.println("[KimoTools] Geladen!");
		System.out.println("[KimoTools] Plugin by MrKimo.");
		System.out.println("==================================");
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		String owner = this.getConfig().getString(path2);
		if(event.getPlayer().getName().equals(owner)){
			event.setJoinMessage(ChatColor.RED + "Der Servereigentümer " + ChatColor.AQUA + event.getPlayer().getName() + ChatColor.RED + " hat sich eingeloggt!");
		} else {
			event.setJoinMessage(ChatColor.GREEN + "Spieler " + ChatColor.AQUA + event.getPlayer().getName() + ChatColor.GREEN + " hat sich eingeloggt!");
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		Player p = null;
		if(sender instanceof Player){
			p = (Player)sender;
		}
		double health;
		String serverteam = this.getConfig().getString(path1);
			
		//serverteam command
		if(cmd.getName().equalsIgnoreCase("serverteam")){
			if(p != null){
				if(args.length == 0){
					p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Das Serverteam besteht aus " + ChatColor.RED + ChatColor.UNDERLINE + serverteam + ChatColor.RESET + ChatColor.GREEN + ".");
					return true;
				} else {
					return false;
				}
			} else { System.out.println("[KimoTools] Not a console command!"); return true; }
		}
		//gethealth command
		else if(cmd.getName().equalsIgnoreCase("gethealth")){
			if(p != null){	
				if(p.hasPermission("kimotools.gethealth")){
					if(args.length > 1){
						p.sendMessage(ChatColor.RED + "ERROR: Zu viele Argumente!");
						return false;
					}	
					else if(args.length == 0){
						health = p.getHealth();
						p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Ihr Leben liegt bei " + ChatColor.RED + health/2 + ChatColor.GREEN + " Herzen.");
						return true;
					}
					else if(args.length == 1){
						for(Player curp : this.getServer().getOnlinePlayers()){
							if(curp.getName().equalsIgnoreCase(args[0])){
								health = curp.getHealth();
								p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Die Leben des Spielers " + ChatColor.AQUA + curp.getName() + ChatColor.GREEN + " liegen bei " + ChatColor.RED + health/2 + ChatColor.GREEN + " Herzen.");
								return true;
							}
						} 
						p.sendMessage(ChatColor.RED + " ERROR: Spieler offline!");
						return true;
					} else {
						return false;
					}
				} else { p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true; }
			} else { System.out.println("[KimoTools] Not a console command!"); return true; }
		}
		//aflame command
		else if(cmd.getName().equalsIgnoreCase("aflame")){
			if(p != null){
				if(p.hasPermission("kimotools.aflame")){
					if(args.length > 1){ p.sendMessage(ChatColor.RED + "ERROR: Zu viele Argumente!"); return false;}
					else if(args.length == 0){
						p.setFireTicks(200);
						p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + "Du hast dich selbst in Brand gesteckt! #FIRECHALLENGE");
						return true;
					}
					else if(args.length == 1){
						for(Player curp : this.getServer().getOnlinePlayers()){
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
				} else { p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true; }
			} else { System.out.println("[KimoTools] Not a console command!"); return true; }
		}
		//beam command
		else if(cmd.getName().equalsIgnoreCase("tp")) {
			if(p != null){
				if(args.length == 0){ p.sendMessage(ChatColor.RED + "ERROR: Zu wenig Argumente!"); return false;}
				else if (args.length == 1) {
					if(p.hasPermission("kimotools.tp")){
						for(Player curp : this.getServer().getOnlinePlayers()){
							if(curp.getName().equalsIgnoreCase(args[0])){
								p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Beame zu " + ChatColor.AQUA + curp.getName());
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
						for(Player target1 : this.getServer().getOnlinePlayers()){
							if(target1.getName().equalsIgnoreCase(args[0])){
								for(Player target2 : this.getServer().getOnlinePlayers()){
									if(target2.getName().equalsIgnoreCase(args[1])){
										target1.teleport(target2.getLocation());
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
		else if(cmd.getName().equalsIgnoreCase("tphere")){
			if(p == null) { System.out.println("[KimoTools] Not a console command"); return true; }
			if(args.length != 1) { p.sendMessage(ChatColor.RED + "ERROR Es muss ein Argument angegeben werden!"); return false; }
			if(!p.hasPermission("kimotools.tp.here")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true;}
			for(Player curp : this.getServer().getOnlinePlayers()){
				if(curp.getName().equalsIgnoreCase(args[0])){
					p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Beame " + ChatColor.AQUA + curp.getName() + ChatColor.GREEN + " zu dir.");
					curp.teleport(p.getLocation());
					p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Beam erfolgreich!");
					curp.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.AQUA + " " + p.getName() + ChatColor.GREEN + " hat dich zu ihm gebeamt!");
					return true;
				}
			}
			p.sendMessage(ChatColor.RED + "ERROR: Spieler offline!");
			return true;
		}
		//warp command
		else if(cmd.getName().equalsIgnoreCase("warp")){
			if(p != null){ System.out.println("[KimoTools] Not a console command1"); return true;}
			if(args.length != 1){ p.sendMessage(ChatColor.RED + "ERROR: Es muss ein Argument angegeben werden!"); return false; }
			if(!p.hasPermission("kimotools.warp")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung! "); return true;}
			Location warpLoc = warpMgr.getWarp(args[0]);
			if(warpLoc == null) { p.sendMessage(ChatColor.RED + "ERROR: Dieser Warp existiert nicht!"); return true;}
			p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Beame zu Warp: " + ChatColor.AQUA + args[0]);
			p.teleport(warpLoc);
			p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Sie haben ihr Ziel erreicht!");
		}
		//setwarp command
		else if(cmd.getName().equalsIgnoreCase("setwarp")){
			if(p == null){ System.out.println("[KimoTools] Not a console command!"); return true;}
			if(args.length != 1){ p.sendMessage(ChatColor.RED +  "ERROR: Es muss ein Argument angegeben werden!"); return false; }
			if(!p.hasPermission("kimotools.warp.mod")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true;}
			p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Setze Warp...");
			if(warpMgr.addWarp(args[0], p.getLocation()) == 0){
				p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Warp: " + ChatColor.AQUA + args[0] + ChatColor.GREEN + " gesetzt!");
			} else { p.sendMessage(ChatColor.RED + "ERROR: Warp existiert bereits oder sonstiger Fehler!"); return true;}
		}
		//delwarp command
		else if(cmd.getName().equalsIgnoreCase("delwarp")){
			if(p == null){ System.out.println("[KimoTools] Not a console command!"); return true;}
			if(args.length != 1){ p.sendMessage(ChatColor.RED +  "ERROR: Es muss ein Argument angegeben werden!"); return false; }
			if(!p.hasPermission("kimotools.warp.mod")){ p.sendMessage(ChatColor.RED + "ERROR: Keine Berechtigung!"); return true;}
			warpMgr.removeWarp(args[0]);
			p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Warp: " + ChatColor.AQUA + args[0] + ChatColor.GREEN + " gelöscht!");
		}
		//warplist command
		else if (cmd.getName().equalsIgnoreCase("warps")){
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
		return true;
	}
	private void loadConfig(){
		System.out.println("[KimoTools] Config geladen!");
		this.getConfig().addDefault(path1, "Player, Player, Player (Standard Meldung, bitte \u00e4ndern)");
		this.getConfig().addDefault(path2, "MrKimo");
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
	}
}
