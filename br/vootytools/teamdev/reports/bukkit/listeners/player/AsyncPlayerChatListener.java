package br.vootytools.teamdev.reports.bukkit.listeners.player;

import java.util.HashMap;
import java.util.Map;

import br.vootytools.teamdev.reports.Language;
import br.vootytools.teamdev.reports.bukkit.utils.SendUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AsyncPlayerChatListener implements Listener {
   private static final Map<Player, String> reporting = new HashMap();

   public static Boolean isReporting(Player player) {
      return reporting.containsKey(player);
   }

   public static void setReporting(Player player, String target) {
      reporting.put(player, target);
   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void AsyncPlayerChat(AsyncPlayerChatEvent evt) {
      Player player = evt.getPlayer();
      String custom = "event.custom_report_";
      String cancel_report = "event.cancel_report";
      String cancel_custom = "event.cancel_custom_report";
      if (isReporting(player)) {
         evt.setCancelled(true);
         String target = (String)reporting.get(player);
         reporting.remove(player);
         if (evt.getMessage().equals(cancel_custom)) {
            player.sendMessage(Language.messages$command$report$cancelled);
         } else {
            player.chat("/report " + target + " " + evt.getMessage());
         }
      } else if (!evt.getMessage().equals(cancel_custom) && !evt.getMessage().equals(cancel_report)) {
         if (evt.getMessage().contains(custom)) {
            evt.setCancelled(true);
            setReporting(player, evt.getMessage().replace(custom, ""));
            SendUtils.sendBukkitMessage(player, Language.messages$command$report$event);
         }
      } else {
         evt.setCancelled(true);
      }

   }

   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent evt) {
      if (isReporting(evt.getPlayer())) {
         reporting.remove(evt.getPlayer());
      }

   }

   @EventHandler(
      priority = EventPriority.MONITOR
   )
   public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent evt) {
      Player player = evt.getPlayer();
      if (isReporting(evt.getPlayer())) {
         evt.setCancelled(true);
         SendUtils.sendBukkitMessage(player, Language.messages$command$report$event);
      }

   }
}
