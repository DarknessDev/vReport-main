package br.vootytools.teamdev.reports.bungee.cmd.cmds;

import br.vootytools.teamdev.reports.Language;
import br.vootytools.teamdev.reports.comunication.BungeeComunication;
import br.vootytools.teamdev.reports.manager.ReportsManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import br.vootytools.teamdev.reports.bungee.cmd.Commands;

public class ReportsCommand extends Commands {
   public ReportsCommand() {
      super("reports");
   }

   public void perform(CommandSender sender, String[] args) {
      if (!(sender instanceof ProxiedPlayer)) {
         sender.sendMessage(TextComponent.fromLegacyText("§cEsse comando pode ser utilizado apenas por jogadores."));
      } else {
         ProxiedPlayer player = (ProxiedPlayer)sender;
         if (!player.hasPermission("vreports.cmd")) {
            player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$reports$not_perm));
         } else {
            if (args.length == 0) {
               player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$reports$help));
            } else if (args[0].equalsIgnoreCase("clear")) {
               int amount = ReportsManager.clear();
               if (amount != 0) {
                  String message = Language.messages$command$reports$cleaned;
                  player.sendMessage(TextComponent.fromLegacyText(message.replace("{amount}", String.valueOf(amount)).replace("{s}", amount == 1 ? "" : "s")));
               } else {
                  player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$reports$empty));
               }
            } else if (args[0].equalsIgnoreCase("list")) {
               if (!ReportsManager.isEmpty()) {
                  BungeeComunication.sendPluginMessage(player, "OpenReports");
               } else {
                  player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$reports$empty));
               }
            } else {
               player.sendMessage(TextComponent.fromLegacyText(Language.messages$command$reports$help));
            }

         }
      }
   }
}
