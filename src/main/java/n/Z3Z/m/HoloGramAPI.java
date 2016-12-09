package n.Z3Z.m;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

public class HoloGramAPI implements Listener{
	
	private static a y;
	public HoloGramAPI(a hub) {
		HoloGramAPI.y = hub;
	}
	static String b = "&c&lERROR: &eHoloGram is already made!";
	public static HashMap<Integer, Entity> g = new HashMap<Integer, Entity>();
	public static void createHoloGram(Location loc, String Name, Integer ID, boolean glowing, boolean small, boolean firework, Color color1, Color color2, Color color3){
		if(!g.containsKey(ID)){
		final ArmorStand s = (ArmorStand) loc.getWorld().spawn(loc.add(0.5, 0, 0.5), ArmorStand.class);
		s.setVisible(false);
		s.setCustomName(z.f(Name));
		s.setCustomNameVisible(true);
		s.setInvulnerable(true);
		s.setGravity(false);
		s.setCollidable(false);
		s.setCanPickupItems(false);
		s.setGlowing(glowing);
		s.setSmall(small);
		s.setGliding(false);
		g.put(ID, s);
		if(firework == true){
			FireworkEffect u = FireworkEffect.builder().trail(true).flicker(true).withColor(new Color[] { color1,color2,color3 }).with(FireworkEffect.Type.BURST).build();
			z.p(s.getLocation().add(0.5, 0, 0.5), u, 1);
		}else{
			return;
		}
		}else{
			ConsoleCommandSender v = Bukkit.getConsoleSender();
			v.sendMessage(z.f(b));
		}
	}
	public static Integer Id;
	public static void createTempHoloGram(Location loc, String name, final Integer ID, Integer timeInSecondsBeforeDespawn,boolean glowing, boolean small, boolean firework, Color color1, Color color2, Color color3){
		Id = 0;
		if(!g.containsKey(ID)){
			createHoloGram(loc, name, ID, glowing, small, firework, color1, color2, color3);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(y, new Runnable(){
	    		public void run() {
	    			removeHoloGram(ID);
	    		}
	    	}, timeInSecondsBeforeDespawn * 20);
		}else{
			Id = ID + z.randomNum(1, 1000);
			createHoloGram(loc, name, Id, glowing, small, firework, color1, color2, color3);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(y, new Runnable(){
	    		public void run() {
	    			removeHoloGram(Id);
	    		}
	    	}, timeInSecondsBeforeDespawn * 20);
		}
	}
	
	public static void removeHoloGram(Integer ID){
		g.get(ID).remove();
		g.remove(ID);
	}
	
	public static void removeAllHoloGramsInWorld(String world){
		List<Entity> e1 = Bukkit.getWorld(world).getEntities();
		for ( Entity e : e1){
			if(e.equals(EntityType.ARMOR_STAND)){
				e.remove();
			}
		}
	}
	
	public static void teleportHoloGram(Integer ID, Location loc){
		g.get(ID).teleport(loc.add(0.5, 0, 0.5));
	}
	
	public static String getHoloGramName(Integer ID){
		return g.get(ID).getCustomName();
	}
	
	public static Location getHoloGramLoc(Integer ID){
		return g.get(ID).getLocation();
	}
	
	public static void renameHoloGram(String Name, Integer ID){
		g.get(ID).setCustomName(z.f(Name));
		g.get(ID).setCustomNameVisible(true);
	}
	
	@EventHandler
	public void q(EntityCombustEvent e){
		if(e.getEntityType() == EntityType.ARMOR_STAND){
			e.setCancelled(true);
		}
	}

}
