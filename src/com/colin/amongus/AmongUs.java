package com.colin.amongus;

import com.colin.amongus.events.Impostor;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class AmongUs extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(new Impostor(), this);
        System.out.println(ChatColor.GREEN + "[AmongUs] is enabled!");
    }

    @Override
    public void onDisable()
    {
        System.out.println(ChatColor.RED + "[AmongUs] is disabled!");
    }
}
