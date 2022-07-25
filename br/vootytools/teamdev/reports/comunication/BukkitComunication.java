package br.vootytools.teamdev.reports.comunication;

import br.vootytools.teamdev.reports.menus.ReportBook;
import br.vootytools.teamdev.reports.menus.ReportsInventory;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.messaging.PluginMessageListener;
import br.vootytools.teamdev.reports.bukkit.Main;

public class BukkitComunication implements Listener, PluginMessageListener {
   public static void sendPluginMessage(Player player, String subChannel) {
      sendPluginMessage(player, subChannel, "");
   }

   public static void sendPluginMessage(Player player, String subChannel, String utf) {
      ByteArrayDataOutput out = ByteStreams.newDataOutput();
      out.writeUTF(subChannel);
      out.writeUTF(utf);
      player.sendPluginMessage(Main.getInstance(), "vReports", out.toByteArray());
   }

   public void onPluginMessageReceived(String channel, Player arg1, byte[] msg) {
      if (channel.equals("vReports")) {
         ByteArrayDataInput in = ByteStreams.newDataInput(msg);
         String subChannel = in.readUTF();
         Player player;
         if (subChannel.equalsIgnoreCase("console")) {
            player = Bukkit.getPlayerExact(in.readUTF());
            if (player != null) {
               Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), in.readUTF());
            }
         }

         if (subChannel.equalsIgnoreCase("OpenReport")) {
            player = Bukkit.getPlayerExact(in.readUTF());
            String target = in.readUTF();
            if (player != null) {
               ReportBook.open(player, target);
            }
         }

         if (subChannel.equalsIgnoreCase("OpenReports")) {
            player = Bukkit.getPlayerExact(in.readUTF());
            if (player != null) {
               new ReportsInventory(player);
            }
         }
      }

   }
}
