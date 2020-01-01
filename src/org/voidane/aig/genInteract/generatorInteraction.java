package org.voidane.aig.genInteract;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.voidane.aig.Configurator;
import org.voidane.aig.Plugin;
import org.voidane.aig.Inventories.GenInventorySetup;

public class generatorInteraction implements Listener {

	Plugin plugin = Plugin.getPlugin(Plugin.class);
	
	
	public generatorInteraction() {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void robotDamagingEvent(EntityDamageByEntityEvent event) {
		
		
		if (event.getCause().equals(DamageCause.ENTITY_EXPLOSION)) {
			return;
		}
		
		if (!event.getEntityType().equals(EntityType.ARMOR_STAND)) {
			return;
		}
		
		Entity entity = event.getDamager();
		if (!event.getDamager().equals(entity)) {
			return;
		}
		
		event.setCancelled(true);
		
		Player player = (Player) event.getDamager();
		saveLastInteractionToFile(player, event.getEntity().getLocation(), event.getEntity().getWorld());
		
		if (new GenInventorySetup().setupInventory(player) == null) {
			player.sendMessage(plugin.PREFIXCOLOR + " &cMajor error, please tell an admin to check the console!");
			return;
		}
		
		player.openInventory(new GenInventorySetup().setupInventory(player));
		
	}
	
	
	
	
	
	
	private void saveLastInteractionToFile(Player player, Location location, World world) {
		
		FileConfiguration configuration = new Configurator().getRecentPlayerInteractions();
		
		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();
		String worldName = world.getName();
		String replacerString = worldName+x+y+z;
		configuration.set(player.getUniqueId()+".Generator", replacerString.replace('.', '@'));
		
		try {
			configuration.save(new File(plugin.getDataFolder(), "player interaction data.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
