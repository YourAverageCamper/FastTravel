
package me.zeus.FastTravel;


import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import me.zeus.FastTravel.IconMenu.OptionClickEvent;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;



public class FastTravel extends JavaPlugin implements Listener
{
	
	
	IconMenu menu;
	HashMap<Integer, EasyOption> slots;
	ItemStack book;
	ItemStack compass;
	
	
	
	@Override
	public void onEnable()
	{
		slots = new HashMap<Integer, EasyOption>();
		
		compass = new ItemStack(Material.COMPASS, 1);
		ItemMeta meta = compass.getItemMeta();
		meta.setDisplayName("§aFast Travel");
		meta.setLore(Arrays.asList("Navigate with ease!"));
		compass.setItemMeta(meta);
		
		File config = new File(getDataFolder() + "/config.yml");
		if (!config.exists())
			saveDefaultConfig();
		
		ConfigurationSection options = getConfig().getConfigurationSection("option");
		Set<String> keys = options.getKeys(false);
		String[] keysArray = keys.toArray(new String[keys.size()]);
		
		for (int i = 0; i < keysArray.length; i++)
		{
			String name = options.getString(keysArray[i] + ".name");
			String info = options.getString(keysArray[i] + ".info");
			String cmd = options.getString(keysArray[i] + ".command");
			String[] aa = options.getString(keysArray[i] + ".item").toString().split(";");
			ItemStack stack = new ItemStack(Material.getMaterial(Integer.parseInt(aa[0])), 1, Short.parseShort(aa[1]));
			slots.put(i, new EasyOption(name, info, stack, cmd));
		}
		//!f
		menu = new IconMenu("§aFast Travel", 9, new IconMenu.OptionClickEventHandler()
		{
			@Override
			public void onOptionClick(OptionClickEvent event)
			{
				if(event.getName().equalsIgnoreCase(slots.get(0).getName()))
	                event.getPlayer().performCommand(slots.get(0).getCommand());
                else if(event.getName().equalsIgnoreCase(slots.get(1).getName()))
	                event.getPlayer().performCommand(slots.get(1).getCommand());
                else  if(event.getName().equalsIgnoreCase(slots.get(2).getName()))
	                event.getPlayer().performCommand(slots.get(2).getCommand());
                else  if(event.getName().equalsIgnoreCase(slots.get(3).getName()))
	                event.getPlayer().performCommand(slots.get(3).getCommand());
                else  if(event.getName().equalsIgnoreCase(slots.get(4).getName()))
	                event.getPlayer().performCommand(slots.get(4).getCommand());
                else  if(event.getName().equalsIgnoreCase(slots.get(5).getName()))
	                event.getPlayer().performCommand(slots.get(5).getCommand());
                else  if(event.getName().equalsIgnoreCase(slots.get(6).getName()))
	                event.getPlayer().performCommand(slots.get(6).getCommand());
                else  if(event.getName().equalsIgnoreCase(slots.get(7).getName()))
	                event.getPlayer().performCommand(slots.get(7).getCommand());
                else  if(event.getName().equalsIgnoreCase(slots.get(8).getName()))
	                event.getPlayer().performCommand(slots.get(8).getCommand());
				event.setWillClose(true);
			}
		}, this)
		.setOption(0, slots.get(0).getItem(), slots.get(0).getName(), slots.get(0).getInfo())
		.setOption(1, slots.get(1).getItem(), slots.get(1).getName(), slots.get(1).getInfo())
		.setOption(2, slots.get(2).getItem(), slots.get(2).getName(), slots.get(2).getInfo())
		.setOption(3, slots.get(3).getItem(), slots.get(3).getName(), slots.get(3).getInfo())
		.setOption(4, slots.get(4).getItem(), slots.get(4).getName(), slots.get(4).getInfo())
		.setOption(5, slots.get(5).getItem(), slots.get(5).getName(), slots.get(5).getInfo())
		.setOption(6, slots.get(6).getItem(), slots.get(6).getName(), slots.get(6).getInfo())
		.setOption(7, slots.get(7).getItem(), slots.get(7).getName(), slots.get(7).getInfo())
		.setOption(8, slots.get(8).getItem(), slots.get(8).getName(), slots.get(8).getInfo());
		//f		
		
		getServer().getPluginManager().registerEvents(this, this);
		loadBook();
	}
	
	
	
	@EventHandler
	public void onClick(PlayerInteractEvent e)
	{
		if (e.getPlayer().getItemInHand() == null)
			return;
		
		if (e.getPlayer().getItemInHand() != null && e.getPlayer().getItemInHand().getType().equals(Material.COMPASS) && e.getPlayer().getItemInHand().getItemMeta().hasDisplayName())
		{
			if (!e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§aFast Travel"))
				return;
			menu.open(e.getPlayer());
			e.setCancelled(true);
		}
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (cmd.getName().equalsIgnoreCase("travel"))
			if (args.length < 1)
			{
				((Player) sender).getInventory().addItem(compass);
				((Player) sender).getInventory().addItem(book);
			}
			else if (sender.hasPermission("FastTravel.reload"))
			{
				reloadConfig();
				sender.sendMessage(ChatColor.GREEN + "Config reloaded.");
			}
		return false;
	}
	
	
	
	private void loadBook()
	{
		book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta meta = (BookMeta) book.getItemMeta();
		
		ConfigurationSection cs = getConfig().getConfigurationSection("book.page");
		Set<String> keys = cs.getKeys(false);
		String[] keys2 = keys.toArray(new String[keys.size()]);
		
		meta.setTitle(getConfig().getString("book.title").replace("&", "§"));
		meta.setAuthor(getConfig().getString("book.author").replace("&", "§"));
		meta.setDisplayName(getConfig().getString("book.title").replace("&", "§"));
		for (int i = 0; i < keys.size(); i++)
			meta.addPage(cs.getString(keys2[i]).replace("&", "§"));
		
		book.setItemMeta(meta);
	}
	
	
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e)
	{
		if (!e.getItemDrop().getItemStack().hasItemMeta())
			return;
		
		if (e.getItemDrop().getItemStack().hasItemMeta() && e.getItemDrop().getItemStack().getItemMeta().hasDisplayName())
		{
			ItemStack dropped = e.getItemDrop().getItemStack();
			if (dropped.hasItemMeta() && dropped.getItemMeta().hasDisplayName())
			{
				ItemMeta meta = dropped.getItemMeta();
				if (meta.getDisplayName().equalsIgnoreCase(book.getItemMeta().getDisplayName()))
					e.setCancelled(true);
				else if (meta.getDisplayName().equalsIgnoreCase(compass.getItemMeta().getDisplayName()))
					e.setCancelled(true);
			}
		}
	}
	
	
	
	@EventHandler
	public void onClick(InventoryClickEvent e)
	{
		if (e.getCurrentItem() != null)
		{
			if (e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName())
			{
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aFast Travel")
				        || e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(book.getItemMeta().getDisplayName()))
				{
					e.setCancelled(true);
				}
			}
		}
	}
	
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		if (!e.getPlayer().getInventory().contains(book))
		{
			e.getPlayer().getInventory().addItem(book);
			if (!e.getPlayer().getInventory().contains(compass))
				e.getPlayer().getInventory().addItem(compass);
			e.getPlayer().updateInventory();
		}
	}
	
	
	
	@EventHandler
	public void onRespawn(final PlayerRespawnEvent e)
	{
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
		{
			
			
			@SuppressWarnings("deprecation")
			@Override
			public void run()
			{
				e.getPlayer().getInventory().addItem(compass);
				e.getPlayer().getInventory().addItem(book);
				e.getPlayer().updateInventory();
			}
		}, 20L);
	}
}





class EasyOption
{
	
	
	String name;
	String info;
	ItemStack item;
	String command;
	
	
	
	public EasyOption(String name, String info, ItemStack item, String command)
	{
		this.name = name.toString().replace("&", "§");
		this.info = info.toString().replace("&", "§");
		this.item = item;
		this.command = command.toLowerCase();
	}
	
	
	
	public String getName()
	{
		return name;
	}
	
	
	
	public String getInfo()
	{
		return info;
	}
	
	
	
	public ItemStack getItem()
	{
		return item;
	}
	
	
	
	public String getCommand()
	{
		return command;
	}
}
