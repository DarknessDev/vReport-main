package br.vootytools.teamdev.reports.bukkit.compatibility;

import br.vootytools.teamdev.reports.compatibility.ConsoleLogger;
import org.bukkit.Bukkit;

public class BukkitConsoleLogger implements ConsoleLogger {
   public void sendMessage(String message) {
      Bukkit.getConsoleSender().sendMessage(message);
   }
}
