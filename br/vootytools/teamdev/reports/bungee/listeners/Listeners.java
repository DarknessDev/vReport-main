package br.vootytools.teamdev.reports.bungee.listeners;

import br.vootytools.teamdev.reports.bungee.listeners.player.ChatListener;
import br.vootytools.teamdev.reports.bungee.listeners.player.ServerConnectedListener;
import br.vootytools.teamdev.reports.comunication.BungeeComunication;
import net.md_5.bungee.api.ProxyServer;
import br.vootytools.teamdev.reports.bungee.Main;

public class Listeners {
   public static void setupListeners() {
      ProxyServer.getInstance().getPluginManager().registerListener(Main.getInstance(), new BungeeComunication());
      ProxyServer.getInstance().getPluginManager().registerListener(Main.getInstance(), new ChatListener());
      ProxyServer.getInstance().getPluginManager().registerListener(Main.getInstance(), new ServerConnectedListener());
   }
}
