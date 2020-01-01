package org.voidane.aig.sql;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.voidane.aig.Configurator;
import org.voidane.aig.Plugin;

public class MySQLNoValidInput {

	
	Plugin plugin = Plugin.getPlugin(Plugin.class);
	
	
	public MySQLNoValidInput() {
		
		tellConsoleToInputDatabase();
		tellConsoleDatabase();
		
	}
	
	
	
	
	public void tellConsoleDatabase() {

		FileConfiguration configuration = new Configurator().getMySQLconfig();
		
		
		
		plugin.consoleSender(plugin.PREFIX + "=============[DATABASE]===========");
		plugin.consoleSender(plugin.PREFIX + "HOST: " + configuration.getString("Host"));
		plugin.consoleSender(plugin.PREFIX + "PORT: " + configuration.getInt("Port"));
		plugin.consoleSender(plugin.PREFIX + "DATABASE: ' ( No Input Needed )' ");
		plugin.consoleSender(plugin.PREFIX + "USERNAME: " + configuration.getString("Username"));
		plugin.consoleSender(plugin.PREFIX + "PASSWORD: " + "********");
		plugin.consoleSender(plugin.PREFIX + "==================================");
		
		
	}
	
	
	private void tellConsoleToInputDatabase() {
		
		File file = new File(plugin.getDataFolder(),"database.yml");
		FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		
		if (configuration.getString("Host") == null) {
			plugin.consoleSender(plugin.PREFIXCOLOR + plugin.translateChatColor("&f( You are trying to run the database with no host! )"
					+ " &4WARNING INVALID HOST NAME &f( Plugins > Advanced Item Generator > database.yml )"));
		plugin.consoleSender(plugin.PREFIXCOLOR + plugin.translateChatColor("&f( Check the config on to how to setup ) "));
		}
		if (configuration.getString("Username") == null) {
			plugin.consoleSender(plugin.PREFIXCOLOR + plugin.translateChatColor("&f( You are trying to run the database with no username! )"
					+ " &4WARNING INVALID USERNAME ( THERE IS NO USERNAME FOUND ) &f( Plugins > Advanced Item Generator > database.yml )"));
		plugin.consoleSender(plugin.PREFIXCOLOR + plugin.translateChatColor("&f( Check the config on to how to setup ) "));
		}
		if (configuration.getString("Password") == null) {
			plugin.consoleSender(plugin.PREFIXCOLOR + plugin.translateChatColor("&c( You are trying to run the database with no password! )"
					+ " Only localhost have this capability, otherwise ignore this error &f( Plugins > Advanced Item Generator > database.yml )"));
		}
	}
	
	
}
