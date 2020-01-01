package org.voidane.aig;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.plugin.java.JavaPlugin;
import org.voidane.aig.genInteract.InventoryInteraction;
import org.voidane.aig.genInteract.generatorInteraction;
import org.voidane.aig.genPlacement.DataDiamondGen;
import org.voidane.aig.generators.DiamondGen;
import org.voidane.aig.sql.InitialConnection;
import org.voidane.aig.sql.MySQLNoValidInput;

public class Plugin extends JavaPlugin {

	
	public String version = "1.0";
	public final String PREFIXCOLOR = translateChatColor("&f[&bAdvanced Item Generators&f] &6");
	public final String PREFIX = "[Advanced Item Generators] ";
	
	@Override
	public void onEnable() {
		
		consoleSender(PREFIX + "Plugin loading in");
		
		// Scanning config.yml for data mismatches
		if (checkDataOptions()) {
			return;
		}
		
		new Configurator();
		new DataDiamondGen();
		new MySQLNoValidInput();
		new InitialConnection();
		new generatorInteraction();
		new InventoryInteraction();
	}
	
	
	
	
	
	@Override
	public void onDisable() {
		
		consoleSender(PREFIX + "Plugin has been force closed");
		
	}
	
	
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {
		
		Player player = (Player) sender;
		
		if (cmd.getName().equalsIgnoreCase("dhead")) {
			
			if (Integer.parseInt(args[0]) < 11 || Integer.parseInt(args[0]) > 0 ) {
				player.getInventory().addItem(new DiamondGen().DiamondGenItemStack(Integer.parseInt(args[0])));
				return true;
			}
			
			player.sendMessage("Not Valid");

			return true;
		}
		
		return false;
	}
	
	
	
	
	
	public String translateChatColor(String chat) {
		chat = ChatColor.translateAlternateColorCodes('&', chat);
		return chat;
	}
	
	
	
	
	
	
	public void consoleSender(String consoleMsg) {
		Bukkit.getServer().getConsoleSender().sendMessage(consoleMsg);
	}
	
	
	
	
	
	public String[] translateChatColorArray(String[] chat) {
		for ( int i = 0 ; i < chat.length ; i++ ) {
		chat[i] = ChatColor.translateAlternateColorCodes('&', chat[i]);
		}
		return chat;
	}
	
	
	
	
	
	public boolean hasPermission(Player player, String commandOrAction) {
	
		if (player.hasPermission(commandOrAction)) {
			return true;
		}
		return false;
	}

	
	
	
	
	public List<String> translateChatColorArray(List<String> lore) {
		
		for ( int i = 0 ; i < lore.size() ; i++ ) {
			lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
		}
		return lore;
	}
	
	
	
	
	private boolean checkDataOptions() {
		
		if (getConfig().getBoolean("Server Files") && getConfig().getBoolean("Database")) {
			consoleSender(PREFIXCOLOR + translateChatColor("&4[WARNING] You are using server files and a database to try and store files, please only set one to true in your config.yml"));
			consoleSender(PREFIXCOLOR + translateChatColor("&4[WARNING] Plugin is shutting down"));
			return true;
		}
		return false;
	}
	
	
}

