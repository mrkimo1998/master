package me.mrkimo.kimotools;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class kimotools extends JavaPlugin {

	@Override
	public void onDisable() {
		System.out.println("[KimoTools] Plugin wurde deaktiviert.");
	}
	
	@Override
	public void onEnable() {
		System.out.println("==================================");
		System.out.println("[KimoTools] Plugin wurde geladen.");
		System.out.println("[KimoTools] Plugin by MrKimo.");
		System.out.println("==================================");
		loadConfig();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		Player p = null;
		if(sender instanceof Player){
			p = (Player)sender;
		}
		double health;
		String Serverteam = this.getConfig().getString("Configuration.serverteam");
		
		//serverteam command
		if(cmd.getName().equalsIgnoreCase("serverteam")){
			if(p != null){
				if(args.length == 0){
					p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Das Serverteam besteht aus " + ChatColor.RED + ChatColor.UNDERLINE + Serverteam + ChatColor.RESET + ChatColor.GREEN + ".");
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
						p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.RED + " ERROR: Spieler offline!");
						return true;
					} else {
						return false;
					}
				} else { p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.RED + "ERROR: Keine Berechtigung!"); return true; }
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
						p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.RED + " ERROR: Spieler offline!");
						return true;
					}
				} else { p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.RED + "ERROR: Keine Berechtigung!"); return true; }
			} else { System.out.println("[KimoTools] Not a console command!"); return true; }
		}
		//beam command
		else if(cmd.getName().equalsIgnoreCase("beam")) {
			if(p != null){
				if(args.length == 0){ p.sendMessage(ChatColor.RED + "ERROR: Zu wenig Argumente!"); return false;}
				else if (args.length == 1) {
					if(p.hasPermission("kimotools.beam")){
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
					if(p.hasPermission("kimotools.beam.others")){
						for(Player target1 : this.getServer().getOnlinePlayers()){
							if(target1.getName().equalsIgnoreCase(args[0])){
								for(Player target2 : this.getServer().getOnlinePlayers()){
									if(target2.getName().equalsIgnoreCase(args[0])){
										target1.teleport(target2.getLocation());
										target1.sendMessage(ChatColor.GREEN + " Sie wurden zu " + ChatColor.AQUA + target2.getName() + ChatColor.GREEN + " gebeamt!");
										target2.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.AQUA + " " + target1.getName() + ChatColor.GREEN + " wurde zu ihnen gebeamt!");
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
		return false;
	}
	private void loadConfig(){
		System.out.println("[KimoTools] Config geladen!");
		String path1 = "Configuration.serverteam";
		this.getConfig().addDefault(path1, "Player, Player, Player (Standard Meldung, bitte \u00e4ndern)");
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
	}
	
	
}

