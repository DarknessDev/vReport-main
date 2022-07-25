package br.vootytools.teamdev.reports.bukkit.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import br.vootytools.teamdev.reports.bukkit.Main;
import br.vootytools.teamdev.reports.bukkit.listeners.player.AsyncPlayerChatListener;
import br.vootytools.teamdev.reports.bukkit.listeners.player.PlayerJoinListener;

public class Listeners {
   public static void setupListeners() {
      try {
         PluginManager pm = Bukkit.getPluginManager();
         pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, new AsyncPlayerChatListener(), Main.getInstance());
         pm.getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class).invoke(pm, new PlayerJoinListener(), Main.getInstance());
      } catch (Exception var1) {
         var1.printStackTrace();
      }

   }
}
