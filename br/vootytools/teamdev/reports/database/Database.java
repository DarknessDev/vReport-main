package br.vootytools.teamdev.reports.database;

import javax.sql.rowset.CachedRowSet;

import br.vootytools.teamdev.reports.Core;
import br.vootytools.teamdev.reports.utils.plugin.vConfig;
import br.vootytools.teamdev.reports.utils.plugin.vLogger;

public abstract class Database {
   private static Database instance;
   public static final vLogger LOGGER;

   public abstract void closeConnection();

   public abstract void update(String var1, Object... var2);

   public abstract void execute(String var1, Object... var2);

   public abstract CachedRowSet query(String var1, Object... var2);

   public static void setupDatabase() {
      if (Core.BUNGEE) {
         instance = new MySQLDatabase();
      } else if (vConfig.getConfig("dbconfig").getString("database.tipo").equalsIgnoreCase("mysql")) {
         instance = new MySQLDatabase();
      } else {
         instance = new SQLiteDatabase();
      }

   }

   public static Database getInstance() {
      return instance;
   }

   static {
      LOGGER = Core.LOGGER.getModule("DATABASE");
   }
}
