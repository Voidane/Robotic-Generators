package org.voidane.aig.genPlacement;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;
import org.voidane.aig.Configurator;
import org.voidane.aig.Plugin;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class DataDiamondGen implements Listener {
	
	Plugin plugin = Plugin.getPlugin(Plugin.class);
	
	private String genName = null;
	private double speedBoost = 0;
	private int speedlevel = 0;
	private int extradropchancelevel = 0;
	private int sellingmultiplierlevel = 0;
	private int storageupgraderlevel = 0;
	private double extraDropChance = 0;
	private double sellingMultiplier = 0;
	private double storageUpgrader = 0;
	
	public DataDiamondGen() {
		
		Bukkit.getPluginManager().registerEvents(this, plugin);
		
		
	}


	
	
	
	
	@EventHandler
	public void isDiamondGenerator(BlockPlaceEvent event) {
		
		Player player = event.getPlayer();

		if (!event.getItemInHand().hasItemMeta()) {

			return;
		}
	
		FileConfiguration configuration = new Configurator().getGeneratorNameConfiguration();
		if (!event.getItemInHand().getItemMeta().getDisplayName().toString().toLowerCase().contains(plugin.translateChatColor(configuration.getString("Diamond Ore Generator.Name").toLowerCase()))) {
			return;
		}
	
		if (!checkTypeBlock(event.getBlock())) {
			return;
		}
		
		int level = 0;
		
		for (int i = 0 ; i < configuration.getStringList("Generator Levels").size() ; i++ ) {
			if (event.getItemInHand().getItemMeta().getDisplayName().contains(plugin.translateChatColor(
					configuration.getStringList("Generator Levels").get(i)))) {
				
				level = i+1;
				break;
				
			}
			
		}
		genName = configuration.getString("Diamond Ore Generator.Name");
		
		for (int i = 0; i < event.getItemInHand().getItemMeta().getLore().size() ; i++ ) {
			
			if (event.getItemInHand().getItemMeta().getLore().get(i).contains("ID Number")) {
				configuration = new Configurator().getGeneratorData();
				configuration.get("ID Number"+".Speed Boost");
				break;
			}
			
			if (i-1 == event.getItemInHand().getItemMeta().getLore().size()) {
				speedBoost = 0;
				extraDropChance = 0;
				sellingMultiplier = 0;
				storageUpgrader = 0;
				speedlevel = 0;
				extradropchancelevel = 0;
				storageupgraderlevel = 0;
				sellingmultiplierlevel = 0;
			}
			
		}
		
		
		
		if (plugin.getConfig().getBoolean("Database")) {
		storeGeneratorPlacementMYSQL(event.getBlock(), player, level, event.getBlock().getLocation(), event.getBlock().getWorld(), new Configurator().getMySQLconfig());
		} else {
			
		storeGeneratorPlacementFILES(event.getBlock(), player, level, event.getBlock().getLocation(), event.getBlock().getWorld(), new Configurator().getGeneratorData());
			
		
		}
		
		ArmorStand stand = (ArmorStand) event.getBlock().getWorld().spawnEntity(event.getBlock().getLocation(), EntityType.ARMOR_STAND);
		
		
		
		if (getDirection(player).equals("N")) {
			stand.teleport(event.getBlock().getLocation().add(0.5, 0, 0.5).setDirection(new Vector(-0, -0.6, 0.7)));

		}
		if (getDirection(player).equals("E")) {
			stand.teleport(event.getBlock().getLocation().add(0.5, 0, 0.5).setDirection(new Vector(-0.7, -0.6, -0)));

		}
		if (getDirection(player).equals("S")) {
			stand.teleport(event.getBlock().getLocation().add(0.5, 0, 0.5).setDirection(new Vector(-0, -0.6, -0.7)));

		}
		if (getDirection(player).equals("W")) {
			stand.teleport(event.getBlock().getLocation().add(0.5, 0, 0.5).setDirection(new Vector(0.7, -0.6, 0)));

		}
		
		
		stand.setBasePlate(false);
		stand.setSmall(true);
		stand.setHelmet(genHeadSkulls());
		stand.setChestplate(setArmorColor(new ItemStack(Material.LEATHER_CHESTPLATE)));
		stand.setLeggings(setArmorColor(new ItemStack(Material.LEATHER_LEGGINGS)));
		stand.setBoots(setArmorColor(new ItemStack(Material.LEATHER_BOOTS)));
		stand.setItemInHand(new ItemStack(Material.DIAMOND_PICKAXE));
		stand.setCustomName(plugin.translateChatColor(player.getItemInHand().getItemMeta().getDisplayName()));
		
		World world = event.getBlock().getWorld();
		world.getBlockAt(stand.getLocation().add(0, -1, 0)).setType(Material.BEDROCK);
		world.getBlockAt(stand.getLocation().add(-1, -1, 0)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(-1, -1, -1)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(0, -1, -1)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(1, -1, -1)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(1, -1, 0)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(1, -1, 1)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(0, -1, 1)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(-1, -1, 1)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(-2, -1, 1)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(-2, -1, 0)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(-2, -1, -1)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(-2, -1, -2)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(-1, -1, -2)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(0, -1, -2)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(1, -1, -2)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(2, -1, -2)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(2, -1, -1)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(2, -1, 0)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(2, -1, 1)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(2, -1, 2)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(1, -1, 2)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(0, -1, 2)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(-1, -1, 2)).setType(Material.DIAMOND_ORE);
		world.getBlockAt(stand.getLocation().add(-2, -1, 2)).setType(Material.DIAMOND_ORE);
		
		stand.setCustomNameVisible(true);
		

		
	}

	
	
	
	
	
	private ItemStack setArmorColor(ItemStack itemStack) {
		
		LeatherArmorMeta armorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
		org.bukkit.Color color = org.bukkit.Color.AQUA;
		armorMeta.setColor(color);
		itemStack.setItemMeta(armorMeta);
		
		return itemStack;
	
	}

	
	
	
	
	
	private void storeGeneratorPlacementMYSQL(Block block, Player player, int level, Location location, World world, FileConfiguration configuration) {
		
		String getLocationString = location.toString().replace('.', '_');
		
		
	}
	
	
	
	
	
	
	private void storeGeneratorPlacementFILES(Block block, Player player, int level, Location location, World world, FileConfiguration configuration) {
		
		double x = location.getX()+0.5;
		double y = location.getY();
		double z = location.getZ()+0.5;
		String worldName = world.getName();
		String replacerString = worldName+x+y+z;
		replacerString = replacerString.replace('.', '@');
		configuration.set(replacerString+".O", player.getUniqueId().toString());
		configuration.set(replacerString+".T", genName);
		configuration.set(replacerString+".L", level);
		configuration.set(replacerString+".SB", speedBoost);
		configuration.set(replacerString+".EDC", extraDropChance);
		configuration.set(replacerString+".SM", sellingMultiplier);
		configuration.set(replacerString+".SU", storageUpgrader);
		configuration.set(replacerString+".SBL", speedlevel);
		configuration.set(replacerString+"EDCL", extradropchancelevel);
		configuration.set(replacerString+".SML", sellingmultiplierlevel);
		configuration.set(replacerString+"SUL", storageupgraderlevel);
		
		try {
			configuration.save(new File(plugin.getDataFolder(),"generator data.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	public String getDirection(Player player) {
        double rotation = (player.getLocation().getYaw());
         if (rotation >= 135.0 && rotation <= 225) {
        	 return "N";
         } else if (rotation >= 226 && rotation <= 315) {
        	 return "E";
         } else if (rotation >= 316 || rotation <= 45) {
        	 return "S";
         } else {
        	 return "W";
         }

         
	}
         
         
         
         
         
	private ItemStack genHeadSkulls() {
		
		String url = "http://textures.minecraft.net/texture/4851395b3ab335d05a60d7967b0a831b7412d668ac9a62982d0ae37e6d3d9888";
		
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
        
        item.setItemMeta(headMeta);
        
        return item;
		
	}
	
	
	
	
	
	private boolean checkTypeBlock(Block block) {
		
		if (block.getType().equals(Material.SKULL)) {
			return true;
		}
		return false;
		
	}
	

	
}
