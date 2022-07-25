package br.vootytools.teamdev.reports.bukkit;

import java.util.logging.Level;

import br.vootytools.teamdev.reports.Core;
import br.vootytools.teamdev.reports.Language;
import br.vootytools.teamdev.reports.bukkit.listeners.Listeners;
import br.vootytools.teamdev.reports.comunication.BukkitComunication;
import br.vootytools.teamdev.reports.database.Database;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import br.vootytools.teamdev.reports.bukkit.cmd.Commands;

public class Main extends JavaPlugin {
   private static Main instance;
   public boolean bungeecord;

   public void onEnable() {
      instance = this;
      this.saveDefaultConfig();
      Database.setupDatabase();
      Language.setupSettings();
      this.bungeecord = this.getConfig().getBoolean("bungeecord");
      if (this.bungeecord) {
         BukkitComunication bukkitComunication = new BukkitComunication();
         Bukkit.getPluginManager().registerEvents(bukkitComunication, this);
         Bukkit.getMessenger().registerOutgoingPluginChannel(this, "vReports");
         Bukkit.getMessenger().registerIncomingPluginChannel(this, "vReports", bukkitComunication);
      } else {
         Commands.setupCommands();
         Listeners.setupListeners();
      }

      Core.LOGGER.log(Level.INFO, "O plugin foi ativado.");
   }

   public static Main getInstance() {
      return instance;
   }
}
