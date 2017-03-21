package zain.drozal.effects;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	ArrayList<UUID> flame = new ArrayList<UUID>();
	ArrayList<UUID> water = new ArrayList<UUID>();
	
	public void onEnable(){
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
		final FileConfiguration config = this.getConfig();

        config.options().copyDefaults(true);
        saveConfig();
	}
	
	
	//TODO CREATE INVENTORY FOR THIS,WATCH VIDEO ON COMPLEX PARTICLES,ADD THESE TO INVENTORY
	

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		String cmd = command.getName();
		Player p = (Player) sender;

		if (cmd.equalsIgnoreCase("flame")) {
			p.sendMessage("on");
			PacketUtils.sendTitle(p, ChatColor.RED+"Flame Effect", ChatColor.BOLD+"Has Been Enabled", 1, 2, 1);
			flame.add(p.getUniqueId());
			getConfig().set(p.getName()+"'s Flame.enabled", "yes");
			saveConfig();
	}
		
		if (cmd.equalsIgnoreCase("watereff")) {
			p.sendMessage("on");
			PacketUtils.sendTitle(p, ChatColor.AQUA+"Water Effect", ChatColor.BOLD+"Has Been Enabled", 1, 2, 1);
			water.add(p.getUniqueId());
			getConfig().set(p.getName()+"'s water.enabled", "yes");
			saveConfig();
	}
		
		if (cmd.equalsIgnoreCase("wateroff")) {
			p.sendMessage("off");
			PacketUtils.sendTitle(p, ChatColor.AQUA+"Water Effect", ChatColor.BOLD+"Has Been Disabled", 1, 2, 1);
			water.remove(p.getUniqueId());
			getConfig().set(p.getName()+"'s water.enabled", "no");
			saveConfig();
		}
		
		if (cmd.equalsIgnoreCase("flameoff")) {
			p.sendMessage("off");
			PacketUtils.sendTitle(p, ChatColor.RED+"Flame Effect", ChatColor.BOLD+"Has Been Disabled", 1, 2, 1);
			flame.remove(p.getUniqueId());
			getConfig().set(p.getName()+"'s Flame.enabled", "no");
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