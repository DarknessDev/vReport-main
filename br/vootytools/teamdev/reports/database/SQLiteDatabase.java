package br.vootytools.teamdev.reports.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import br.vootytools.teamdev.reports.utils.plugin.vConfig;

public class SQLiteDatabase extends Database {
   public vConfig config = vConfig.getConfig("dbconfig");
   private final File file = new File(config.getString("database.sqlite.arquivo"));
   private Connection connection;
   private final ExecutorService executor;

   public SQLiteDatabase() {
      if (!this.file.exists()) {
         this.file.getParentFile().mkdirs();

         try {
            this.file.createNewFile();
         } catch (IOException var2) {
            LOGGER.log(Level.WARNING, "Ocorreu um erro ao criar o arquivo SQLite: ", var2);
         }
      }

      this.executor = Executors.newCachedThreadPool();
      this.openConnection();
      this.update("CREATE TABLE IF NOT EXISTS `vreports_profile` (id INT AUTO_INCREMENT,uuid VARCHAR(50),name VARCHAR(50),nameId VARCHAR(50),colored VARCHAR(100),prefixed VARCHAR(100), PRIMARY KEY(id));");
      this.update("CREATE TABLE IF NOT EXISTS `vreports_reports` (id INT AUTO_INCREMENT,uuid VARCHAR(50),name VARCHAR(50),author VARCHAR(50),reason TEXT,time LONG, PRIMARY KEY(id));");
   }

   public void closeConnection() {
      if (this.isConnected()) {
         try {
            this.connection.close();
         } catch (SQLException var2) {
            LOGGER.log(Level.WARNING, "Erro ao fechar a SQLite connection: ", var2);
         }
      }

   }

   public boolean isConnected() {
      try {
         return this.connection != null && !this.connection.isClosed();
      } catch (SQLException var2) {
         LOGGER.log(Level.WARNING, "Occorreu um erro na SQLite: ", var2);
         return false;
      }
   }

   public Connection getConnection() {
      if (!this.isConnected()) {
         this.openConnection();
      }

      return this.connection;
   }

   public void openConnection() {
      if (!this.isConnected()) {
         try {
            Class.forName("org.sqlite.JDBC");
            boolean bol = this.connection == null;
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.file);
            if (bol) {
               LOGGER.info("Conectado ao SQLite!");
               return;
            }

            LOGGER.info("Reconectado ao SQLite!");
         } catch (SQLException var2) {
            LOGGER.log(Level.WARNING, "Erro ao abrir a MySQL connection: ", var2);
         } catch (ClassNotFoundException var3) {
            LOGGER.log(Level.SEVERE, "Erro ao procurar o Driver da SQLite!");
         }
      }

   }

   public void update(String query, Object... vars) {
      this.execute(query, vars);
   }

   public void execute(String query, Object... vars) {
      this.executor.execute(() -> {
         try {
            PreparedStatement ps = this.prepareStatement(query, vars);
            ps.executeUpdate();
            ps.close();
         } catch (SQLException var4) {
            LOGGER.log(Level.WARNING, "Erro ao executar uma SQL: ", var4);
         }

      });
   }

   public PreparedStatement prepareStatement(String query, Object... vars) {
      try {
         PreparedStatement ps = this.getConnection().prepareStatement(query);

         for(int i = 0; i < vars.length; ++i) {
            ps.setObject(i + 1, vars[i]);
         }

         return ps;
      } catch (SQLException var5) {
         LOGGER.log(Level.WARNING, "Erro ao preparar um SQL: ", var5);
         return null;
      }
   }

   public CachedRowSet query(String query, Object... vars) {
      CachedRowSet rowSet = null;

      try {
         Future<CachedRowSet> future = this.executor.submit(() -> {
            try {
               PreparedStatement ps = this.prepareStatement(query, vars);
               ResultSet rs = ps.executeQuery();
               CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
               crs.populate(rs);
               rs.close();
               ps.close();
               if (crs.next()) {
                  return crs;
               }
            } catch (Exception var6) {
               LOGGER.log(Level.WARNING, "Erro ao executar uma Query: ", var6);
            }

            return null;
         });
         if (future.get() != null) {
            rowSet = (CachedRowSet)future.get();
         }
      } catch (Exception var5) {
         LOGGER.log(Level.WARNING, "Erro ao chamar uma Task Futura: ", var5);
      }

      return rowSet;
   }
}
