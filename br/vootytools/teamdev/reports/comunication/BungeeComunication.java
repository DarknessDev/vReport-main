package br.vootytools.teamdev.reports.comunication;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class BungeeComunication implements Listener {
   public static void sendPluginMessage(ProxiedPlayer player, String subChannel) {
      sendPluginMessage(player, subChannel, "");
   }

   public static void sendPluginMessage(ProxiedPlayer player, String subChannel, String utf) {
      ByteArrayDataOutput out = ByteStreams.newDataOutput();
      out.writeUTF(subChannel);
      out.writeUTF(player.getName());
      out.writeUTF(utf);
      player.getServer().sendData("vReports", out.toByteArray());
   }

   @EventHandler
   public void onPluginMessage(PluginMessageEvent evt) {
      if (evt.getTag().equals("vReports") && evt.getSender() instanceof Server && evt.getReceiver() instanceof ProxiedPlayer) {
         ProxiedPlayer player = (ProxiedPlayer)evt.getReceiver();
         ByteArrayDataInput in = ByteStreams.newDataInput(evt.getData());
         String subChannel = in.readUTF();
         if (subChannel.equals("ClearReports")) {
            ProxyServer.getInstance().getPluginManager().dispatchCommand(player, "reports clear");
         }
      }

   }
}
