package org.voidane.aig;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Configurator {

	private Plugin plugin = Plugin.getPlugin(Plugin.class);
	
	
	
	public Configurator() {

		
		if (!doesFileExist(new File(plugin.getDataFolder(), "config.yml"))) {
			createDefaultConfig();
			plugin.saveResource("booster inventories.yml", false);
			plugin.consoleSender(plugin.PREFIXCOLOR + plugin.translateChatColor("&bSuccessfuly created config.yml"));
		}
		
		
		createGeneratorNames();
		createMySQLConfigSetup();
		createdGeneratorDataSetup();
		createInventoryLayout();
		createdRecentPlayerInteraction();
	}
	public FileConfiguration getBoosterInv() {
		
		File file = new File(plugin.getDataFolder(),"booster inventories.yml");
		@SuppressWarnings("static-access")
		FileConfiguration fileConfiguration = new YamlConfiguration().loadConfiguration(file);
		return fileConfiguration;
		
	}
	
	
	
	
	private void createDefaultConfig() {
		
		plugin.saveResource("config.yml", false);
		
		plugin.consoleSender(plugin.PREFIXCOLOR + plugin.translateChatColor("&6First time running plugin, attempting to create config.yml"));
	
	}
	
	
	
	
	
	private void createGeneratorNames() {
		
		if (!doesFileExist(new File(plugin.getDataFolder(),"generator names.yml"))) {
			plugin.consoleSender(plugin.PREFIXCOLOR + plugin.translateChatColor("&6Attempting to create file ( generator names.yml )"));
			plugin.saveResource("generator names.yml", false);
			return;
		}
		
	
	}
	
	public FileConfiguration getGeneratorNameConfiguration() {
		
		File file = new File(plugin.getDataFolder(),"generator names.yml");
		@SuppressWarnings("static-access")
		FileConfiguration fileConfiguration = new YamlConfiguration().loadConfiguration(file);
		return fileConfiguration;
		
	}
	
	
	
	
	
	private void createMySQLConfigSetup() {
		
		if (!doesFileExist(new File(plugin.getDataFolder(),"database.yml"))) {
			plugin.consoleSender(plugin.PREFIXCOLOR + plugin.translateChatColor("&6Attempting to create file ( database.yml )"));
			plugin.saveResource("database.yml", false);
			
		}
		
		
	}
	
	public FileConfiguration getMySQLconfig() {
		
		File file = new File(plugin.getDataFolder(), "database.yml" );
		@SuppressWarnings("static-access")
		FileConfiguration fileConfiguration = new YamlConfiguration().loadConfiguration(file);
		return fileConfiguration;
		
	}
	
	
	
	
	
	private void createdGeneratorDataSetup() {
		
		File file = new File(plugin.getDataFolder(), "generator data.yml");
		FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		
		if (doesFileExist(file)) {
			return;
		}
		
		try {
			configuration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public FileConfiguration getGeneratorData() {
		
		@SuppressWarnings("static-access")
		FileConfiguration configuration = new YamlConfiguration().loadConfiguration(new File(plugin.getDataFolder(),"generator data.yml"));
		return configuration;
	}
	
	
	
	
	
	private void createInventoryLayout() {
		
		if (!doesFileExist(new File(plugin.getDataFolder(),"inventory layout.yml"))) {
			plugin.saveResource("inventory layout.yml", false);
		}
		
	}
	
	public FileConfiguration getInventory() {
		
		@SuppressWarnings("static-access")
		FileConfiguration configuration = new YamlConfiguration().loadConfiguration(new File(plugin.getDataFolder(), "inventory layout.yml"));
		return configuration;
	}
	
	
	
	
	
private void createdRecentPlayerInteraction() {
		
		File file = new File(plugin.getDataFolder(), "player interaction data.yml");
		FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		
		if (doesFileExist(file)) {
			return;
		}
		
		try {
			configuration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public FileConfiguration getRecentPlayerInteractions() {
		
		@SuppressWarnings("static-access")
		FileConfiguration configuration = new YamlConfiguration().loadConfiguration(new File(plugin.getDataFolder(),"player interaction data.yml"));
		return configuration;
	}
	
	
	
	
	
	
	private boolean doesFileExist(File file) {

		if (!file.exists()) {
			return false;
		}
		return true;
	
	}
	
	
}
