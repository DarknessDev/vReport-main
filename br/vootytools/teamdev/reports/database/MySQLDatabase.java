package br.vootytools.teamdev.reports.database;

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

public class MySQLDatabase extends Database {
   private Connection connection;
   private final ExecutorService executor;
   private final String host;
   private final String port;
   private final String database;
   private final String username;
   private final String password;

   public MySQLDatabase() {
      vConfig config = vConfig.getConfig("dbconfig");
      this.host = config.getString("database.mysql.host");
      this.port = config.getString("database.mysql.porta");
      this.database = config.getString("database.mysql.nome");
      this.username = config.getString("database.mysql.usuario");
      this.password = config.getString("database.mysql.senha");
      this.executor = Executors.newCachedThreadPool();
      this.openConnection();
      this.update("CREATE TABLE IF NOT EXISTS `vreports_profile` (id INT AUTO_INCREMENT,uuid VARCHAR(50),name VARCHAR(50),nameId VARCHAR(50),colored VARCHAR(100),prefixed VARCHAR(100), PRIMARY KEY(id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;");
      this.update("CREATE TABLE IF NOT EXISTS `vreports_reports` (id INT AUTO_INCREMENT,uuid VARCHAR(50),name VARCHAR(50),author VARCHAR(50),reason TEXT,time LONG, PRIMARY KEY(id)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;");
   }

   public Connection getConnection() {
      if (!this.isConnected()) {
         this.openConnection();
      }

      return this.connection;
   }

   public void closeConnection() {
      if (this.isConnected()) {
         try {
            this.connection.close();
         } catch (SQLException var2) {
            LOGGER.log(Level.SEVERE, "Erro ao fechar a MySQL connection: ", var2);
         }
      }

   }

   public boolean isConnected() {
      try {
         return this.connection != null && !this.connection.isClosed() && this.connection.isValid(5);
      } catch (SQLException var2) {
         LOGGER.log(Level.SEVERE, "Occorreu um erro na MySQL: ", var2);
         return false;
      }
   }

   public void openConnection() {
      if (!this.isConnected()) {
         try {
            boolean bol = this.connection == null;
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?verifyServerCertificate=false&useSSL=false&useUnicode=yes&characterEncoding=UTF-8", this.username, this.password);
            if (bol) {
               LOGGER.info("Conectado ao MySQL!");
               return;
            }

            LOGGER.info("Reconectado ao MySQL!");
         } catch (SQLException var2) {
            LOGGER.log(Level.SEVERE, "Erro ao abrir a MySQL connection: ", var2);
         }
      }

   }

   public void update(String sql, Object... vars) {
      try {
         PreparedStatement ps = this.prepareStatement(sql, vars);
         ps.execute();
         ps.close();
      } catch (SQLException var4) {
         LOGGER.log(Level.WARNING, "Erro ao executar uma SQL: ", var4);
      }

   }

   public void execute(String sql, Object... vars) {
      this.executor.execute(() -> {
         this.update(sql, vars);
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
