package org.voidane.aig.generators;

import java.util.UUID;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.voidane.aig.Configurator;
import org.voidane.aig.Plugin;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class DiamondGen {

	
	
	Plugin plugin = Plugin.getPlugin(Plugin.class);
	
	
	
	
	
	public ItemStack DiamondGenItemStack(int level) {
		
		ItemStack itemStack = genCharacteristics("http://textures.minecraft.net/texture/fb1c268efec8d7d88a1cb88c2bfa097fa57037942299f7d202159fc93cd3036d", level);
		return itemStack;
	
	}
	
	
	
	
	
	private ItemStack genCharacteristics(String url, int level) {
		
		if (level > 0) {
			level = level-1;
		}
		
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        if(url.isEmpty())return head;
       
        FileConfiguration generator = new Configurator().getGeneratorNameConfiguration(); 
        
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        
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
        Bukkit.getConsoleSender().sendMessage(generator.getString("Diamond Ore Generator.Name"));
        headMeta.setDisplayName(plugin.translateChatColor(generator.getString("Diamond Ore Generator.Name")) + " " + plugin.translateChatColor(generator.getStringList("Generator Levels").get(level)));
        headMeta.setLore(plugin.translateChatColorArray(generator.getStringList("Diamond Ore Generator.Lore")));
        head.setItemMeta(headMeta);
        
        return head;
	
	}
	
	
	
}
