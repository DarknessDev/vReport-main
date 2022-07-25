package br.vootytools.teamdev.reports.bungee.listeners.player;

import java.util.Iterator;

import br.vootytools.teamdev.reports.utils.StringUtils;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import br.vootytools.teamdev.reports.bungee.Main;
import br.vootytools.teamdev.reports.manager.ProfileManager;

public class ServerConnectedListener implements Listener {
   @EventHandler
   public void onServerConnect(ServerConnectedEvent evt) {
      setupData(evt.getPlayer());
   }

   private static void setupData(ProxiedPlayer player) {
      Iterator var1 = Main.getInstance().getConfig().getSection("roles").getKeys().iterator();

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
