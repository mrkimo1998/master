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
		if(cmd.getName().equalsIgnoreCase("gethealth")){
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
				} else {
					p.sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.RED + "ERROR: Keine Berechtigung!");
					return true;
				}
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

