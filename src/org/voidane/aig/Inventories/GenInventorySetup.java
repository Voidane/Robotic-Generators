package org.voidane.aig.Inventories;

import java.util.List;
import java.util.UUID;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.voidane.aig.Configurator;
import org.voidane.aig.Plugin;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class GenInventorySetup {

	Plugin plugin = Plugin.getPlugin(Plugin.class);
	private int storageAmount;
	
	public GenInventorySetup() {
		
	
	}
	
	
	public Inventory setupInventory(Player player) {
		
		
		if (setInventoryTitle(player) == null) {
			return null;
		}
		
		
		Inventory inventory = Bukkit.createInventory(null, 54, setInventoryTitle(player));
		FileConfiguration configuration = new Configurator().getInventory();	
		
		
		
		for ( int i = 0 ; i < 55 ; i++ ) {
			if ( i == 0 ) {
				 i = 1;
			}
			if (i == 20) {
				i=27;
			}
			if (i == 29) {
				i=36;
			}
			
			plugin.consoleSender(i+"");
			if (configuration.getString(i + ".Material") == null) {
				inventory.setItem(i, new ItemStack(Material.AIR));
				i++;
			}
			ItemStack itemStack = new ItemStack(Material.getMaterial(configuration.getString(i+".Material")), 1, (short) configuration.getInt(i+".Material Damage"));
			ItemMeta meta = itemStack.getItemMeta();
			
			meta.setDisplayName(plugin.translateChatColor(configuration.getString(i+".Name")));
			meta.setLore(plugin.translateChatColorArray(configuration.getStringList(i+".Lore")));
			
			itemStack.setItemMeta(meta);
			inventory.setItem(i-1, itemStack);
		
		}
		
		
		
		
		
		
		configuration = new Configurator().getRecentPlayerInteractions();
		String locate = configuration.getString(player.getUniqueId()+".Generator");
		configuration = new Configurator().getGeneratorData();
		double speedBoost = configuration.getDouble(locate+".SB");
		double extraDropChance = configuration.getDouble(locate+".EDC");
		double sellingMultiplier = configuration.getDouble(locate+".SM");
		int storageUpgrader = configuration.getInt(locate+".SU");
		int tierUpgrader = configuration.getInt(locate+".Tier Upgrader");
		
		configuration = new Configurator().getInventory();
		
		List<String> loreListSpeedBoost = configuration.getStringList("Speed Boost.Lore");
		for (int i = 0 ; i < loreListSpeedBoost.size() ; i++ ) {
			loreListSpeedBoost.set(i, configuration.getStringList("Speed Boost.Lore").get(i).replace("%speedboost%", speedBoost+""));	
		}
		
		List<String> loreListExtraDropChance = configuration.getStringList("Extra Drop Chance.Lore");
		for (int i = 0 ; i < configuration.getStringList("Extra Drop Chance.Lore").size() ; i++ ) {
		loreListExtraDropChance.set(i,configuration.getStringList("Extra Drop Chance.Lore").get(i).replace("%extradropchance%", extraDropChance+""));
		}
		
		List<String> loreListSellingMultiplier = configuration.getStringList("Selling Multiplier.Lore");
		for (int i = 0 ; i < configuration.getStringList("Selling Multiplier.Lore").size() ; i++ ) {
		loreListSellingMultiplier.set(i,configuration.getStringList("Selling Multiplier.Lore").get(i).replace("%sellingmultiplier%", sellingMultiplier+""));
		}
		if (storageUpgrader == 0) {
			storageAmount=64*14;
		}
		List<String> loreListStorageUpgrader = configuration.getStringList("Storage Upgrader.Lore");
		for (int i = 0 ; i < configuration.getStringList("Storage Upgrader.Lore").size() ; i++ ) {
			
		loreListStorageUpgrader.set(i,configuration.getStringList("Storage Upgrader.Lore").get(i).replace("%storageupgrader%", storageAmount+""));

		}
		
		List<String> loreListTierUpgrader = configuration.getStringList("Tier Upgrader.Lore");
		for (int i = 0 ; i < configuration.getStringList("Tier Upgrader.Lore").size() ; i++ ) {
		loreListTierUpgrader.set(i,configuration.getStringList("Tier Upgrader.Lore").get(i).replace("%tierupgrader%", tierUpgrader+""));	
		}
		
		
		
		
		
		inventory.setItem(0, setItemStackForBasics(Material.BEACON, (short) 0, plugin.translateChatColor(configuration.getString("Speed Boost.Name")), 
				plugin.translateChatColorArray(loreListSpeedBoost)));
		inventory.setItem(2, setItemStackForBasics(Material.DIAMOND, (short) 0, plugin.translateChatColor(configuration.getString("Extra Drop Chance.Name")), 
				plugin.translateChatColorArray(loreListExtraDropChance)));
		
		inventory.setItem(49, setConcreteItemDefaultRobot(player,1));
		
		inventory.setItem(4, setItemStackForBasics(Material.EMERALD, (short) 0, plugin.translateChatColor(configuration.getString("Selling Multiplier.Name")), 
				plugin.translateChatColorArray(loreListSellingMultiplier)));
		inventory.setItem(6, setItemStackForBasics(Material.CHEST, (short) 0, plugin.translateChatColor(configuration.getString("Storage Upgrader.Name")), 
				plugin.translateChatColorArray(loreListStorageUpgrader)));
		inventory.setItem(8, setItemStackForBasics(Material.HOPPER, (short) 0, plugin.translateChatColor(configuration.getString("Tier Upgrader.Name")), 
				plugin.translateChatColorArray(loreListTierUpgrader)));
		
		return inventory;
		
	}
	
	
	
	

	
	private ItemStack setItemStackForBasics(Material material, short damage, String name, List<String> lore) {
		
		ItemStack item = new ItemStack(material, 1, damage);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(plugin.translateChatColor(name));
		meta.setLore(plugin.translateChatColorArray(lore));
		
		item.setItemMeta(meta);
		
		return item;
		
	}
	
	
	
	
	
	
	private String setInventoryTitle(Player player) {
		
		FileConfiguration configuration = new Configurator().getRecentPlayerInteractions();
		String getGenFromFile = configuration.getString(player.getUniqueId()+".Generator");
		configuration = new Configurator().getGeneratorData();
		if (plugin.translateChatColor(configuration.getString(getGenFromFile+".Type")).toString().length() > 35) {
			
			plugin.consoleSender(plugin.PREFIXCOLOR + plugin.translateChatColor("&4WARNING, INVENTORY NAME TO BIG FOR = "+
			plugin.translateChatColor(configuration.getString(getGenFromFile+".Type")).toString()+" Has to be less than 36 characters!"));
			return null;
		}
		return plugin.translateChatColor(configuration.getString(getGenFromFile+".Type")).toString();
	
	}
	
	
	
	
	private String url;
	private ItemStack setConcreteItemDefaultRobot(Player player, int robotVersion) {
		
		if (robotVersion == 1) {
		url = "http://textures.minecraft.net/texture/4851395b3ab335d05a60d7967b0a831b7412d668ac9a62982d0ae37e6d3d9888";
		} else {
		url = "http://textures.minecraft.net/texture/d5c6dc2bbf51c36cfc7714585a6a5683ef2b14d47d8ff714654a893f5da622";
		}
		
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        
        SkullMeta headMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        
        java.lang.reflect.Field profileField = null;
        
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        
        headMeta.setDisplayName(plugin.translateChatColor("&6Mechanical Robot"));
        
        if (robotVersion == 1) {
        
        FileConfiguration configuration = new Configurator().getRecentPlayerInteractions();
		String getGenFromFile = configuration.getString(player.getUniqueId()+".Generator");
		configuration = new Configurator().getGeneratorData();
        
		int speedEnhanced = configuration.getInt(getGenFromFile+".Speed");
		int bonusBoost = configuration.getInt(getGenFromFile+".Bonus Boost");
		int bonusBulk = configuration.getInt(getGenFromFile+".Bonus Bulk");
		
		configuration = new Configurator().getInventory();
		
		List<String> loreList = configuration.getStringList("Mechanical Robot.Lore");

		for (int i=0;i<loreList.size();i++) {
			
			loreList.set(i, loreList.get(i).replace("%speed%", speedEnhanced+""));
			loreList.set(i, loreList.get(i).replace("%bonusboost%", bonusBoost+""));
			loreList.set(i, loreList.get(i).replace("%bonusbulk%", bonusBulk+""));
			
		}
		
        headMeta.setDisplayName(plugin.translateChatColor(configuration.getString("Mechanical Robot.Name")));
        headMeta.setLore(plugin.translateChatColorArray(loreList));

        item.setItemMeta(headMeta);
        return item;
        }
        
        if (robotVersion == 2) {
        	FileConfiguration configuration = new Configurator().getInventory();
        	headMeta.setDisplayName(plugin.translateChatColor(configuration.getString("Store Robot.Name")));
        	headMeta.setLore(plugin.translateChatColorArray(configuration.getStringList("Store Robot.Lore")));
        	item.setItemMeta(headMeta);
            return item;
            
        }
        
        
		return null;
	}
	
	
	
	
}
