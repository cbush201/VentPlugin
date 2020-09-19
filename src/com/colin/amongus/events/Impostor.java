package com.colin.amongus.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Impostor implements Listener
{

    private int ventNum = 0;

    @EventHandler
    public static void onPlayerKill(PlayerInteractEntityEvent event)
    {

    }

    public static Material getBlockUnder(Player player)
    {
        int x = player.getLocation().getBlockX();
        int y = player.getLocation().getBlockY();
        int z = player.getLocation().getBlockZ();

        return player.getWorld().getBlockAt(x, y - 1, z).getType();

    }

    @EventHandler
    public static void playerVent(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        PotionEffect invisible = new PotionEffect(PotionEffectType.INVISIBILITY, 100000, 0, true);
        PotionEffect frozen = new PotionEffect(PotionEffectType.SLOW, 1000000, 129, true);

        Block block = event.getClickedBlock();

        int x = player.getLocation().getBlockX();
        int y = player.getLocation().getBlockY();
        int z = player.getLocation().getBlockZ();

        Material blockUnder = player.getWorld().getBlockAt(x, y - 1, z).getType();

        assert block != null;
        if (block.getType() == Material.IRON_TRAPDOOR && !(player.hasPotionEffect(PotionEffectType.INVISIBILITY)) && getBlockUnder(player) == Material.IRON_TRAPDOOR)
        {
            player.addPotionEffect(invisible);
            player.addPotionEffect(frozen);
        }
        else if (block.getType() == Material.IRON_TRAPDOOR && player.hasPotionEffect(PotionEffectType.INVISIBILITY) && getBlockUnder(player) == Material.IRON_TRAPDOOR)
        {
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            player.removePotionEffect(PotionEffectType.SLOW);
        }


    }

    public static Location[] getPlayerVentLocation(Player p)
    {
        World w = p.getWorld();

        double x = (int) p.getLocation().getX() + .5;
        double y = (int) p.getLocation().getY();
        double z = (int) p.getLocation().getZ() + .5;

        Location playerLoc = new Location(w, x,y,z);
        System.out.println(playerLoc);

//        Location[] locations = new Location[14];
//        locations[0] = new Location(w, 159.5, 34, 371.5);
//        locations[1] = (new Location(w, 149.5, 34, 358.5));
//        locations[2] = (new Location(w, 162.5, 34, 381.5));
//        locations[3] = (new Location(w, 149.5, 34, 392.5));
//        locations[4] = (new Location(w, 141.5, 34, 372.5));
//        locations[5] = (new Location(w, 135.5, 34, 370.5));
//        locations[6] = (new Location(w, 137.5, 34, 376.5));
//        locations[7] = (new Location(w, 109.5, 34, 384.5));
//        locations[8] = (new Location(w, 100.5, 34, 374.5));
//        locations[9] = (new Location(w, 112.5, 34, 365.5));
//        locations[10] = (new Location(w, 88.5, 34, 380.5));
//        locations[11] = (new Location(w, 101.5, 34, 391.5));
//        locations[12] = (new Location(w, 88.5, 34, 374.5));
//        locations[13] =(new Location(w, 99.5, 34, 356.5));

        List<Location[]> locations = new ArrayList<Location[]>();

        locations.add(new Location[] { new Location(w, 159.5, 34, 371.5) , new Location(w, 149.5, 34, 358.5) } ); // set 1
        locations.add(new Location[] { new Location(w, 162.5, 34, 381.5), new Location(w, 149.5, 34, 392.5) } ); // set 2
        locations.add(new Location[] { new Location(w, 141.5, 34, 372.5) , new Location(w, 135.5, 34, 370.5) , new Location(w, 137.5, 34, 376.5) } ); // set 3
        locations.add(new Location[] { new Location(w, 109.5, 34, 384.5) , new Location(w, 100.5, 34, 374.5) , new Location(w, 112.5, 34, 365.5) } ); // set 4
        locations.add(new Location[] { new Location(w, 88.5, 34, 380.5) , new Location(w, 101.5, 34, 391.5) } ); // set 5
        locations.add(new Location[] { new Location(w, 88.5, 34, 374.5) , new Location(w, 99.5, 34, 356.5) } ); // set 6


        for (Location[] l : locations)
        {
            for (int i = 0; i < l.length; i++)
            {
                if (l[i].equals(playerLoc))
                {
                    return l;
                }
            }
        }
        return locations.get(0);
    }


    @EventHandler
    public void playerSwitchVent(PlayerToggleSneakEvent event)
    {
        Player p = event.getPlayer();

        if (p.isSneaking())
        {
            if (getBlockUnder(p) == Material.IRON_TRAPDOOR && p.hasPotionEffect(PotionEffectType.INVISIBILITY))
            {
                Location[] ventSystem = getPlayerVentLocation(p);

                if (ventNum < ventSystem.length)
                {
                    p.sendMessage("Currently on Vent #" + ventNum);
                    ventNum++;
                    p.teleport(ventSystem[ventNum]);
                    p.sendMessage("Now on Vent #" + ventNum);
                }
                else
                {
                    ventNum = 0;
                    p.teleport(ventSystem[ventNum]);
                    p.sendMessage("Vent Should be 0 Now. Vent #" + ventNum);

                }

            }
        }

    }
}



