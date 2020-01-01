package org.voidane.aig.genInteract;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.voidane.aig.Plugin;
import org.voidane.aig.Inventories.BoostInventory;
import org.voidane.aig.Inventories.GenInventorySetup;

public class InventoryInteraction implements Listener {

	Plugin plugin = Plugin.getPlugin(Plugin.class);
	
	public InventoryInteraction() {
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
		
	}
	
	
	
	
	
	@EventHandler
	public void clickedSpeedBoostUpgrade(InventoryClickEvent event) {
		
		if (event.getCurrentItem() == null) {
			return;
		}
		
		if (!event.getView().getTitle().contains("Diamond")
				|| !event.getCurrentItem().hasItemMeta()) {
			return;
		}
		
		if (event.getSlot() == 0) {
			Player player = (Player) event.getWhoClicked();
			player.openInventory(new BoostInventory().getSpeedBoostInventory(player));
		}
		event.setCancelled(true);
		
	}
	
}
