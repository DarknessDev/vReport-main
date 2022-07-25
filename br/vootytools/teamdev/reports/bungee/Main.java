package br.vootytools.teamdev.reports.bungee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

import br.vootytools.teamdev.reports.Core;
import br.vootytools.teamdev.reports.Language;
import br.vootytools.teamdev.reports.bungee.listeners.Listeners;
import br.vootytools.teamdev.reports.database.Database;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.YamlConfiguration;
import br.vootytools.teamdev.reports.bungee.cmd.Commands;
import br.vootytools.teamdev.reports.utils.FileUtils;

public class Main extends Plugin {
   private static Main instance;
   private Configuration config;

   public void onEnable() {
      instance = this;
      this.saveDefaultConfig();
      Database.setupDatabase();
      Commands.setupCommands();
      Language.setupSettings();
      Listeners.setupListeners();
      ProxyServer.getInstance().registerChannel("vReports");
      Core.LOGGER.log(Level.INFO, "O plugin foi ativado.");
   }

   public void saveDefaultConfig() {
      File file = new File("plugins/vReports/config.yml");
      if (!file.exists()) {
         file.getParentFile().mkdirs();
         FileUtils.copyFile(this.getResourceAsStream("config.yml"), file);
      }

      try {
         this.config = YamlConfiguration.getProvider(YamlConfiguration.class).load(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
      } catch (FileNotFoundException var3) {
         var3.printStackTrace();
      }

   }

   public Configuration getConfig() {
      return this.config;
   }

   public static Main getInstance() {
      return instance;
   }
}
