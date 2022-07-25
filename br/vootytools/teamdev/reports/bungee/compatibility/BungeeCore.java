package br.vootytools.teamdev.reports.bungee.compatibility;

import java.util.logging.Level;

import br.vootytools.teamdev.reports.Core;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import br.vootytools.teamdev.reports.bungee.Main;
import br.vootytools.teamdev.reports.utils.plugin.vLogger;

public class BungeeCore extends Core {
   public void dL(vLogger logger, Level level, String message) {
      logger.log(level, message);
   }

   public void sM(String playerName, String message) {
      ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playerName);
      if (player != null) {
         player.sendMessage(TextComponent.fromLegacyText(message));
      }

   }

   public void sM(String playerName, BaseComponent... components) {
      ProxiedPlayer player = ProxyServer.getInstance().getPlayer(playerName);
      if (player != null) {
         player.sendMessage(components);
      }

   }

   public String gS() {
      return Main.getInstance().getDescription().getVersion();
   }
}
