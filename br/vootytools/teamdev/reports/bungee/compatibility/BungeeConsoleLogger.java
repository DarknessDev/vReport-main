package br.vootytools.teamdev.reports.bungee.compatibility;

import br.vootytools.teamdev.reports.compatibility.ConsoleLogger;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

public class BungeeConsoleLogger implements ConsoleLogger {
   public void sendMessage(String message) {
      ProxyServer.getInstance().getConsole().sendMessage(TextComponent.fromLegacyText(message));
   }
}
