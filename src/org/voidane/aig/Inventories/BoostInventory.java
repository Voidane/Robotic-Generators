package org.voidane.aig.Inventories;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.voidane.aig.Configurator;
import org.voidane.aig.Plugin;

public class BoostInventory implements Listener {

	Plugin plugin = Plugin.getPlugin(Plugin.class);
	private int getCount;
	private int stacker=0;
	Inventory inventory;
	
	public BoostInventory() {
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	
	
	
	
	public Inventory getSpeedBoostInventory(Player player) {
		
		FileConfiguration configuration = new Configurator().getBoosterInv();
		inventory = Bukkit.createInventory(null, 27, plugin.translateChatColor(configuration.getString("Speed Booster Inventory.Title")));
		
		for (int i = 0 ; i < 27 ; i++ ) {
			
			if (i % 2 != 0) {
				inventory.setItem(i, getEmptySlotReplacer());
			}
				

						
		}
				
		ItemStack[] itemStack = getConfigUpgraders(player, configuration);
		
		for (int i = 0 ; i < 27 ; i++ ) {	
			if (i % 2 == 0) {
		
		inventory.setItem(i, itemStack[stacker]);
		stacker++;
			}
		}
		return inventory;
	}
	
	
	
	
	
	
	private ItemStack getEmptySlotReplacer() {
		
		ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1);
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(" ");
		itemStack.setItemMeta(itemMeta);
		return itemStack;
		
	}
	
	
	
	
	
	
	private ItemStack[] getConfigUpgraders(Player player, FileConfiguration configuration) {
		
		FileConfiguration getDataConfiguration = new Configurator().getGeneratorData();
		FileConfiguration fetchDataConfigurationLocConfiguration = new Configurator().getRecentPlayerInteractions();
		String locateFindRobot = fetchDataConfigurationLocConfiguration.getString(player.getUniqueId()+".Generator");
		
		ItemStack[] itemStack = new ItemStack[14];
		ItemMeta[] itemMeta = new ItemMeta[14];
		
		
		for ( int i = 0 ; i < 14 ; i++ ) {
			
			getCount++;
			itemStack[i] = new ItemStack(Material.NETHER_STAR, 1);
			itemMeta[i] = itemStack[i].getItemMeta();
			itemMeta[i].setDisplayName(plugin.translateChatColor(configuration.getString("Speed Booster Inventory.Upgrade "+getCount+".Name")));
			
			List<String> lore = configuration.getStringList("Speed Booster Inventory.Upgrade "+getCount+".Lore");
			for (int j = 0 ; j < lore.size() ; j++) {
				lore.set(j, configuration.getStringList
						("Speed Booster Inventory.Upgrade "+getCount+".Lore").get(j).
						replace("%currentspeed%", getDataConfiguration.getDouble(locateFindRobot+"Speed Boost")+""));
				
				getDataConfiguration.getDouble(locateFindRobot+"Extra Drop Chance");
				
				lore.set(j, configuration.getStringList
						("Speed Booster Inventory.Upgrade "+getCount+".Lore").get(j).
						replace("%newspeed%", ""));
			
			}
			itemMeta[i].setLore(plugin.translateChatColorArray(lore));
			itemStack[i].setItemMeta(itemMeta[i]);
			
			
		}
		
		return itemStack;
	}
	
}
