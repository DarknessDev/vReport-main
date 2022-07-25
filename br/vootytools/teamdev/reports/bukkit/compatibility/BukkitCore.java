package br.vootytools.teamdev.reports.bukkit.compatibility;

import java.util.logging.Level;

import br.vootytools.teamdev.reports.Core;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import br.vootytools.teamdev.reports.bukkit.Main;
import br.vootytools.teamdev.reports.utils.plugin.vLogger;

public class BukkitCore extends Core {
   public void dL(vLogger logger, Level level, String message) {
      logger.log(level, message);
   }

   public void sM(String playerName, String message) {
      Player player = Bukkit.getPlayerExact(playerName);
      if (player != null) {
         player.sendMessage(message);
      }

   }

   public void sM(String playerName, BaseComponent... components) {
      Player player = Bukkit.getPlayerExact(playerName);
      if (player != null) {
         player.spigot().sendMessage(components);
      }

   }

   public String gS() {
      return Main.getInstance().getDescription().getVersion();
   }
}
