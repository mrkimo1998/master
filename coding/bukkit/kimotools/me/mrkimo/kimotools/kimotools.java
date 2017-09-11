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
import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.Sound;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.bukkit.enchantments.Enchantment;


public class kimotools extends JavaPlugin implements Listener {

	WarpManager warpMgr;
	HomeManager homeMgr;
	ComManager cmdMgr;
	final String path1 = "Configuration.serverteam";
	final String path2 = "Configuration.owner";
	String serverteam = "TEAM";
	String owner = "MrKimo";

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
		System.out.println("[KimoTools] Konfiguration geladen.");
		//WarpManager
		warpMgr = new WarpManager("warps.hashmap", this);
		System.out.println("[KimoTools] WarpManager geladen.");
		//HomeManager
		homeMgr = new HomeManager("homes.hashmap", this);
		System.out.println("[KimoTools] HomeManager geladen.");
		//CommandManager
		cmdMgr = new ComManager((JavaPlugin) this, warpMgr, homeMgr);
		System.out.println("[KimoTools] CommandManager geladen.");
		//Listener
		getServer().getPluginManager().registerEvents(this, this);
		System.out.println("[KimoTools] Events registriert.");
		System.out.println("[KimoTools] ~~Fertig~~");
		System.out.println("[KimoTools] Plugin von MrKimo.");
		System.out.println("[KimoTools] mrkimo.ddns.net");
		System.out.println("==================================");
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent ejoin) {
		if(ejoin.getPlayer().getName().equals(owner)){
			ejoin.setJoinMessage(ChatColor.RED + "Der Servereigentümer " + ChatColor.AQUA + ejoin.getPlayer().getName() + ChatColor.RED + " hat sich eingeloggt!");
		} else {
			ejoin.setJoinMessage(ChatColor.GREEN + "Spieler " + ChatColor.AQUA + ejoin.getPlayer().getName() + ChatColor.GREEN + " hat sich eingeloggt!");
		}
	}

  @EventHandler
	public void onSignChange(SignChangeEvent esignchange){
		if(esignchange.getLine(0).toLowerCase().contains("warp")) {
			if(esignchange.getPlayer().hasPermission("kimotools.signwarp.create")){
				if(!esignchange.getLine(2).isEmpty() && (warpMgr.getWarp(esignchange.getLine(2)) != null)){
					esignchange.setLine(0, ChatColor.GREEN + "[Warp]");
					esignchange.setLine(1, "");
					esignchange.setLine(3, "");
					esignchange.getPlayer().sendMessage("Warpschild nach " + esignchange.getLine(2) + " wurde erstellt!");
				}
			}
		}
	}

	@EventHandler
	public void onSignClick(PlayerInteractEvent eint){
		if(eint.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			if(eint.getClickedBlock().getState() instanceof Sign){
				Sign s = (Sign) eint.getClickedBlock().getState();
				String[] slines = s.getLines();
				if(slines[0].contains("[Warp]")){
					if(!slines[2].isEmpty()){
						Location warpLoc = warpMgr.getWarp(slines[2]);
            if(warpLoc != null) {
	            eint.getPlayer().sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Beame zu Warp: " + ChatColor.AQUA + slines[2]);
	            eint.getPlayer().teleport(warpLoc);
	            eint.getPlayer().playSound(warpLoc, (Sound) Sound.ENTITY_ENDERMEN_TELEPORT, (float) 1, (float) 1);
	            eint.getPlayer().sendMessage(ChatColor.GOLD + "[KimoTools]" + ChatColor.GREEN + " Sie haben ihr Ziel erreicht!");
						}
					}
				}
			}
		}
	}


	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		cmdMgr.command(sender, cmd, cmdLabel, args);
		return true;
	}
	public void loadConfig(){
		this.getConfig().addDefault(path1, serverteam);
		this.getConfig().addDefault(path2, owner);
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
		serverteam = this.getConfig().getString(path1);
		owner = this.getConfig().getString(path2);
	}
}
