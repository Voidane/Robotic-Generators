package org.voidane.aig.sql;

import org.bukkit.configuration.file.FileConfiguration;
import org.voidane.aig.Configurator;
import org.voidane.aig.Plugin;
import com.mysql.jdbc.Connection;

public class InitialConnection {

	private Connection connection;
	Plugin plugin = Plugin.getPlugin(Plugin.class);
	
	
	
	
	public InitialConnection() {
	
			FileConfiguration configuration = new Configurator().getMySQLconfig();
		
	         
		   
	}
}
