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
	public void onJoin(PlayerJoinEvent event) {
		if(event.getPlayer().getName().equals(owner)){
			event.setJoinMessage(ChatColor.RED + "Der Servereigentümer " + ChatColor.AQUA + event.getPlayer().getName() + ChatColor.RED + " hat sich eingeloggt!");
		} else {
			event.setJoinMessage(ChatColor.GREEN + "Spieler " + ChatColor.AQUA + event.getPlayer().getName() + ChatColor.GREEN + " hat sich eingeloggt!");
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
