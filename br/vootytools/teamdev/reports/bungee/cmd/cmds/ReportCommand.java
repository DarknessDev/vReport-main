package br.vootytools.teamdev.reports.bungee.cmd.cmds;

import java.util.Iterator;

import br.vootytools.teamdev.reports.Language;
import br.vootytools.teamdev.reports.bungee.cmd.cmds.ReportAliases.ReportarAlias;
import br.vootytools.teamdev.reports.comunication.BungeeComunication;
import br.vootytools.teamdev.reports.manager.ProfileManager;
import br.vootytools.teamdev.reports.manager.ReportsManager;
import br.vootytools.teamdev.reports.utils.StringUtils;
import br.vootytools.teamdev.reports.utils.Validator;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import br.vootytools.teamdev.reports.bungee.cmd.cmds.ReportAliases.ReportAlias;
import br.vootytools.teamdev.reports.bungee.cmd.cmds.ReportAliases.ReporteAlias;
import br.vootytools.teamdev.reports.bungee.utils.SendUtils;

public class ReportCommand {
   public static void setupAliases() {
      new ReportAlias();
      new ReporteAlias();
      new ReportarAlias();
   }

   public static void execute(ProxiedPlayer player, String[] args) {
      if (Validator.isValidUsername(args[0]) && args[0].length() <= 16 && args[0].length() >= 2) {
         if (args[0].equalsIgnoreCase(player.getName())) {
            player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$report$self));
         } else {
            ProxiedPlayer target = BungeeCord.getInstance().getPlayer(args[0]);
            String uuid;
            if (target == null) {
               if (ProfileManager.getUUID(args[0]) == null) {
                  player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$report$invalid));
                  return;
               }

               uuid = ProfileManager.getUUID(args[0]);
            } else {
               uuid = target.getUniqueId().toString();
            }

            if (ReportsManager.exists(args[0].toLowerCase())) {
               player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$report$already));
            } else {
               String server = target == null ? player.getServer().getInfo().getName() : target.getServer().getInfo().getName();
               if (args.length > 1) {
                  String reason = StringUtils.join((Object[])args, " ").replace(args[0] + " ", "");
                  ReportsManager.insert(uuid, args[0].toLowerCase(), player.getName(), reason);
                  String message = Language.messages$command$report$success;
                  player.sendMessage(TextComponent.fromLegacyText(message.replace("{target}", args[0]).replace("{colored_target}", ProfileManager.getColored(args[0])).replace("{prefixed_target}", ProfileManager.getPrefixed(args[0])).replace("{reason}", reason)));
                  Iterator var7 = BungeeCord.getInstance().getPlayers().iterator();

                  while(var7.hasNext()) {
                     ProxiedPlayer players = (ProxiedPlayer)var7.next();
                     if (players.hasPermission("vreports.notify")) {
                        String notify = Language.messages$command$report$notify;
                        SendUtils.sendBungeeMessage(players, notify.replace("{author}", player.getName()).replace("{target}", args[0]).replace("{server}", server).replace("{reason}", reason).replace("{target}", args[0]).replace("{colored_target}", ProfileManager.getColored(args[0])).replace("{prefixed_target}", ProfileManager.getPrefixed(args[0])).replace("{colored_author}", ProfileManager.getColored(player.getName())).replace("{prefixed_author}", ProfileManager.getPrefixed(player.getName())));
                     }
                  }

               } else {
                  BungeeComunication.sendPluginMessage(player, "OpenReport", ProfileManager.getName(args[0]));
               }
            }
         }
      } else {
         player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$report$invalid));
      }
   }
}
