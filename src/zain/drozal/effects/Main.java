package zain.drozal.effects;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	Inventory inv = Bukkit.createInventory(null, 9, ChatColor.AQUA+"EffectGui");
	
	// Flame On
    ItemStack flameon = new ItemStack(Material.FLINT_AND_STEEL); {
        ItemMeta flameonmeta = flameon.getItemMeta();
        flameonmeta.setDisplayName(ChatColor.GREEN+"Enable Flame Effect");
        flameonmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY+"Turn on fire effect");
        flameonmeta.setLore(lore);
        flameon.setItemMeta(flameonmeta);
        inv.setItem(0, flameon);
    }
    
	// Flame off
    ItemStack flameoff = new ItemStack(Material.REDSTONE_BLOCK); {
        ItemMeta flameoffmeta = flameoff.getItemMeta();
        flameoffmeta.setDisplayName(ChatColor.RED+"Disable Flame Effect");
        flameoffmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY+"Turn off fire effect");
        flameoffmeta.setLore(lore);
        flameoff.setItemMeta(flameoffmeta);
        inv.setItem(1, flameoff);
    }
	
	ArrayList<UUID> flame = new ArrayList<UUID>();
	ArrayList<UUID> water = new ArrayList<UUID>();
	
	public void onEnable(){
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		final FileConfiguration config = this.getConfig();
        config.options().copyDefaults(true);
        saveConfig();
	}
	
	
	//TODO CREATE INVENTORY FOR THIS,WATCH VIDEO ON COMPLEX PARTICLES,ADD THESE TO INVENTORY
	
	@EventHandler
	public void onClick(InventoryClickEvent e){
		Inventory inv = e.getInventory();
		Player p = (Player) e.getWhoClicked();
		if(inv.getName().equals(ChatColor.AQUA+"EffectGui")){
		
			if(e.getSlot() == 0){
				PacketUtils.sendTitle(p, ChatColor.RED+"Flame Effect", "Has Been Enabled", 1, 2, 1);
				flame.add(p.getUniqueId());
				getConfig().set(p.getName()+"'s Flame.enabled", "yes");
				saveConfig();
				p.closeInventory();
				e.setCancelled(true);
			}
			
			if(e.getSlot() == 1){
				PacketUtils.sendTitle(p, ChatColor.RED+"Flame Effect", "Has Been Disabled", 1, 2, 1);
				flame.remove(p.getUniqueId());
				getConfig().set(p.getName()+"'s Flame.enabled", "no");
				saveConfig();
				p.closeInventory();
				e.setCancelled(true);
			}
		
		}
}
	

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String cmd = command.getName();
		Player p = (Player) sender;

		if (cmd.equalsIgnoreCase("effectgui")) {
			p.sendMessage(ChatColor.ITALIC+"Opened EffectsMenu");
			p.openInventory(inv);
	}
		
		if (cmd.equalsIgnoreCase("water")) {
			PacketUtils.sendTitle(p, ChatColor.AQUA+"Water Effect", "Has Been Enabled", 1, 2, 1);
			water.add(p.getUniqueId());
			getConfig().set(p.getName()+"'s water.enabled", "yes");
			saveConfig();
	}
		
		if (cmd.equalsIgnoreCase("wateroff")) {
			PacketUtils.sendTitle(p, ChatColor.AQUA+"Water Effect", "Has Been Disabled", 1, 2, 1);
			water.remove(p.getUniqueId());
			getConfig().set(p.getName()+"'s water.enabled", "no");
			saveConfig();
		}
		return false;

	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPMove(PlayerMoveEvent e){
		Player t = e.getPlayer();
		
		if(flame.contains(e.getPlayer().getUniqueId())){
			e.getPlayer().playEffect(e.getPlayer().getLocation(), Effect.MOBSPAWNER_FLAMES, 500);
		}
		Location tl8 = new Location(t.getWorld(), t.getLocation().getX(), t.getLocation().getY() + 2.0D, t.getLocation().getZ());
		if(water.contains(e.getPlayer().getUniqueId())){
			e.getPlayer().playEffect(tl8, Effect.WATERDRIP, 500);
		}
	}
}