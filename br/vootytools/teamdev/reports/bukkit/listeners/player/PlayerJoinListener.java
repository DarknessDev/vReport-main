package br.vootytools.teamdev.reports.bukkit.listeners.player;

import java.util.Iterator;

import br.vootytools.teamdev.reports.manager.ProfileManager;
import br.vootytools.teamdev.reports.utils.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import br.vootytools.teamdev.reports.bukkit.Main;

public class PlayerJoinListener implements Listener {
   @EventHandler
   public void onJoin(PlayerJoinEvent evt) {
      setupData(evt.getPlayer());
   }

   private static void setupData(Player player) {
      Iterator var1 = Main.getInstance().getConfig().getConfigurationSection("roles").getKeys(false).iterator();

      while(var1.hasNext()) {
         String group = (String)var1.next();
         String prefix = Main.getInstance().getConfig().getString("roles." + group + ".prefix").replace("&", "§");
         String permission = Main.getInstance().getConfig().getString("roles." + group + ".permission");
         String colored = StringUtils.getLastColor(prefix) + player.getName();
         String prefixed = prefix + player.getName();
         if (!permission.equals("")) {
            if (player.hasPermission(permission)) {
               ProfileManager.saveData(player.getUniqueId().toString(), player.getName(), colored, prefixed);
               return;
            }
         } else {
            ProfileManager.saveData(player.getUniqueId().toString(), player.getName(), colored, prefixed);
         }
      }

   }
}
