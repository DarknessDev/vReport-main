package br.vootytools.teamdev.reports;

import java.io.InputStream;
import java.util.logging.Level;

import br.vootytools.teamdev.reports.bukkit.Main;
import net.md_5.bungee.api.chat.BaseComponent;
import br.vootytools.teamdev.reports.bukkit.compatibility.BukkitConsoleLogger;
import br.vootytools.teamdev.reports.bukkit.compatibility.BukkitCore;
import br.vootytools.teamdev.reports.bungee.compatibility.BungeeConsoleLogger;
import br.vootytools.teamdev.reports.bungee.compatibility.BungeeCore;
import br.vootytools.teamdev.reports.compatibility.ConsoleLogger;
import br.vootytools.teamdev.reports.utils.plugin.vLogger;

public abstract class Core {
   public static final boolean BUNGEE = hasBungeeClass();
   public static final vLogger LOGGER = new vLogger();
   private static final Core METHODS;

   public abstract void dL(vLogger var1, Level var2, String var3);

   public abstract void sM(String var1, String var2);

   public abstract void sM(String var1, BaseComponent... var2);

   public abstract String gS();

   static boolean hasBungeeClass() {
      try {
         Class.forName("net.md_5.bungee.api.ProxyServer");
         return true;
      } catch (ClassNotFoundException var1) {
         return false;
      }
   }

   public static void delayedLog(String message) {
      METHODS.dL(LOGGER, Level.INFO, message);
   }

   public static void delayedLog(vLogger logger, Level level, String message) {
      METHODS.dL(logger, level, message);
   }

   public static void sendMessage(String playerName, String message) {
      METHODS.sM(playerName, message);
   }

   public static void sendMessage(String playerName, BaseComponent... components) {
      METHODS.sM(playerName, components);
   }

   public static String getVersion() {
      return METHODS.gS();
   }

   public static InputStream getResource(String name) {
      return BUNGEE ? br.vootytools.teamdev.reports.bungee.Main.getInstance().getResourceAsStream(name) : Main.getInstance().getResource(name);
   }

   public static ConsoleLogger getConsoleLogger() {
      return (ConsoleLogger)(BUNGEE ? new BungeeConsoleLogger() : new BukkitConsoleLogger());
   }

   static {
      METHODS = (Core)(BUNGEE ? new BungeeCore() : new BukkitCore());
   }
}
