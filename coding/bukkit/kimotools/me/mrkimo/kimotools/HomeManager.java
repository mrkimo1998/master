package me.mrkimo.kimotools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Setzt, liest und entfernt Objekte vom Typ org.bukkit.Location.Location<br>
 * als HashMap in eine und aus einer Datei.
 * 
 * Modifiziert als HomeManager! Von MrKimo
 *
 * @author DeBukkIt,MrKimo
 * @version 1.0.0
 *
 */
public class HomeManager {

	HashMap<String, String> map; // Still the same because a combined variable of the uuid (as string) and name is used
	
	String homeFileLocation;
	
	public HomeManager(String homeFileName, JavaPlugin plugin){
		this.homeFileLocation = plugin.getDataFolder() + File.separator + homeFileName;
		System.out.println("HomeFileLocation is " + homeFileLocation);
	}
	
	/**
	 * Returns the HomePoint with the specified name.
	 * If there is no HomePoint with that name, null will be returned
	 * @param uuid, name
	 * @return HomePoint, if available; else null
	 */
	public Location getHome(UUID uuid, String name){
		//Load Map (Update)
		loadMap();
		//Combovar of Homename & UUID of Player
		String uniqhome = uuid.toString() + name;

		//Reads the HomePoint and returns its Location
		try{
			StringTokenizer toker = new StringTokenizer(map.get(uniqhome), ";");
			Location loc = new Location(Bukkit.getWorld(toker.nextToken()),
										Double.valueOf(toker.nextToken()), Double.valueOf(toker.nextToken()), Double.valueOf(toker.nextToken()),
										Float.valueOf(toker.nextToken()), Float.valueOf(toker.nextToken()));
			
			return loc;
		
		} catch(NullPointerException e){
			return null;
		}
	}
	
	/**
	 * Removes the HomePoint with the specified name.<br>
	 * If there is no HomePoint with that name, nothing will happen.
	 * @param uuid, name
	 */
	public void removeHome(UUID uuid, String name){
		//Load Map (Update)
		loadMap();

		//Combovar of Homename & UUID of Player
		String uniqhome = uuid.toString() + name;
		
		//Remove HomePoint from Map
		map.remove(uniqhome);
		
		//Save Map
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(homeFileLocation));
			oos.writeObject(map);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds/Saves a new HomePoint
	 * @param uuid  UUID of Player
	 * @param name	Name of the HomePoint
	 * @param loc	Location of the HomePoint
	 * @return  Status-Integer<br>
	 * 			0 : Success
	 * 			1 : HomePoint with that name already exists
	 * 			2 : Other error (cf. console error log)
	 */
	public int addHome(UUID uuid, String name, Location loc){
		//Load Map (Update)
		loadMap();

		//Combovar of Homename & UUID of Player
		String uniqhome = uuid.toString() + name;
		
		//Create new HomePoint and write it to Map
		if(!map.containsKey(uniqhome)){
			map.put(uniqhome, loc.getWorld().getName() + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getYaw() + ";" + loc.getPitch());
		} else {
			return 1;
		}		
		
		//Save Map
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(homeFileLocation));
			oos.writeObject(map);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
			return 2;
		}
		
		return 0;
	}
	
	/**
	 * Returns a list of all existing HomePoints
	 * @return Array of Objects (all of them Strings)
	 *
	*/
	public Object[] getHomeList(){
		//Map laden
		loadMap();
		return map.keySet().toArray();
	}
	
	//--	PRIVATE METHOD TO LOAD THE MAP	
	
	@SuppressWarnings("unchecked")
	private void loadMap(){
		//Datei anlegen, falls nicht vorhanden
		try {
			File file = new File(homeFileLocation);
			if(!file.exists()){
				file.createNewFile();
				System.out.println("Created new File.");
				
				//Leere Map schreiben
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(homeFileLocation));
				oos.writeObject(new HashMap<String, String>());
				oos.flush();
				oos.close();
				
				System.out.println("Wrote empty HashMap.");
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//Map laden
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(homeFileLocation));
			this.map = (HashMap<String, String>) ois.readObject();
			ois.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
} 
