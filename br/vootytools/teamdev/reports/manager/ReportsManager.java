package br.vootytools.teamdev.reports.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

import br.vootytools.teamdev.reports.database.Database;

public class ReportsManager {
   public static Boolean isEmpty() {
      CachedRowSet rs = Database.getInstance().query("SELECT * FROM `vreports_reports`");
      return rs == null;
   }

   public static List<String> get() {
      List<String> list = new ArrayList();
      CachedRowSet rs = Database.getInstance().query("SELECT * FROM `vreports_reports`");
      if (rs != null) {
         try {
            rs.beforeFirst();

            while(rs.next()) {
               String uuid = rs.getString("uuid");
               String name = rs.getString("name");
               String author = rs.getString("author");
               String reason = rs.getString("reason");
               String time = String.valueOf(rs.getLong("time"));
               list.add(uuid + " : " + name + " : " + author + " : " + reason + " : " + time);
            }
         } catch (SQLException var7) {
         }
      }

      return list;
   }

   public static int clear() {
      int amount = 0;
      CachedRowSet rs = Database.getInstance().query("SELECT * FROM `vreports_reports`");
      if (rs != null) {
         try {
            rs.beforeFirst();

            while(rs.next()) {
               String name = rs.getString("name");
               if (remove(name)) {
                  ++amount;
               }
            }
         } catch (SQLException var3) {
            var3.printStackTrace();
         }
      }

      return amount;
   }

   public static Boolean remove(String name) {
      CachedRowSet rs = Database.getInstance().query("SELECT * FROM `vreports_reports` WHERE name = ?", name);
      if (rs != null) {
         Database.getInstance().execute("DELETE FROM `vreports_reports` WHERE name = ?", name);
         return true;
      } else {
         return false;
      }
   }

   public static void insert(String uuid, String name, String author, String reason) {
      long time = System.currentTimeMillis();
      Database.getInstance().execute("INSERT INTO `vreports_reports` (uuid, name, author, reason, time) VALUES (?, ?, ?, ?, ?)", uuid, name, author, reason, time);
   }

   public static boolean exists(String name) {
      CachedRowSet rs = Database.getInstance().query("SELECT * FROM `vreports_reports` WHERE name = ?", name);
      return rs != null;
   }
}
