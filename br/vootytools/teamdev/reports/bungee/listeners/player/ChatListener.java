package br.vootytools.teamdev.reports.bungee.listeners.player;

import java.util.HashMap;
import java.util.Map;

import br.vootytools.teamdev.reports.Language;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import br.vootytools.teamdev.reports.bungee.utils.SendUtils;

public class ChatListener implements Listener {
   private static final Map<ProxiedPlayer, String> reporting = new HashMap();

   public static Boolean isReporting(ProxiedPlayer player) {
      return reporting.containsKey(player);
   }

   public static void setReporting(ProxiedPlayer player, String target) {
      reporting.put(player, target);
   }

   @EventHandler(
      priority = 64
   )
   public void onPlayerChat(ChatEvent evt) {
      if (evt.getSender() instanceof ProxiedPlayer) {
         ProxiedPlayer player = (ProxiedPlayer)evt.getSender();
         String custom = "event.custom_report_";
         String cancel_report = "event.cancel_report";
         String cancel_custom = "event.cancel_custom_report";
         if (isReporting(player)) {
            evt.setCancelled(true);
            if (evt.isCommand()) {
               SendUtils.sendBungeeMessage(player, Language.messages$command$report$event);
               return;
            }

            String reason = evt.getMessage();
            String target = (String)reporting.get(player);
            reporting.remove(player);
            if (evt.getMessage().equals(cancel_custom)) {
               player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$report$cancelled));
            } else {
               ProxyServer.getInstance().getPluginManager().dispatchCommand(player, "report " + target + " " + reason);
            }
         } else if (!evt.getMessage().equals(cancel_custom) && !evt.getMessage().equals(cancel_report)) {
            if (evt.getMessage().contains(custom)) {
               evt.setCancelled(true);
               setReporting(player, evt.getMessage().replace(custom, ""));
               SendUtils.sendBungeeMessage(player, Language.messages$command$report$event);
            }
         } else {
            evt.setCancelled(true);
         }
      }

   }

   @EventHandler(
      priority = 64
   )
   public void onPlayerDisconnect(PlayerDisconnectEvent evt) {
      if (isReporting(evt.getPlayer())) {
         reporting.remove(evt.getPlayer());
      }

   }
}
