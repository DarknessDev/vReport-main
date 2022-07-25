package br.vootytools.teamdev.reports.manager;

import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;

import br.vootytools.teamdev.reports.database.Database;

public class ProfileManager {
   public static void saveData(String uuid, String name, String colored, String prefixed) {
      CachedRowSet rs = Database.getInstance().query("SELECT * FROM `vreports_profile` WHERE uuid = ?", uuid);
      if (rs != null) {
         Database.getInstance().execute("DELETE FROM `vreports_profile` WHERE uuid = ?", uuid);
      }

      Database.getInstance().execute("INSERT INTO `vreports_profile` (uuid, name, nameId, colored, prefixed) VALUES (?, ?, ?, ?, ?)", uuid, name, name.toLowerCase(), colored, prefixed);
   }

   public static String getUUID(String nameId) {
      CachedRowSet rs = Database.getInstance().query("SELECT * FROM `vreports_profile` WHERE nameId = ?", nameId.toLowerCase());
      if (rs != null) {
         try {
            return rs.getString("uuid");
         } catch (SQLException var3) {
            return null;
         }
      } else {
         return null;
      }
   }

   public static String getName(String nameId) {
      CachedRowSet rs = Database.getInstance().query("SELECT * FROM `vreports_profile` WHERE nameId = ?", nameId.toLowerCase());
      if (rs != null) {
         try {
            return rs.getString("name");
         } catch (SQLException var3) {
            return null;
         }
      } else {
         return null;
      }
   }

   public static String getColored(String player) {
      CachedRowSet rs = Database.getInstance().query("SELECT * FROM `vreports_profile` WHERE nameId = ?", player.toLowerCase());
      if (rs != null) {
         try {
            return rs.getString("colored");
         } catch (SQLException var3) {
         }
      }

      return "§7" + player;
   }

   public static String getPrefixed(String player) {
      CachedRowSet rs = Database.getInstance().query("SELECT * FROM `vreports_profile` WHERE nameId = ?", player.toLowerCase());
      if (rs != null) {
         try {
            return rs.getString("prefixed");
         } catch (SQLException var3) {
         }
      }

      return "§7" + player;
   }
}
