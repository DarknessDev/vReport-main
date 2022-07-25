package br.vootytools.teamdev.reports.bungee.cmd.cmds.ReportAliases;

import br.vootytools.teamdev.reports.Language;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import br.vootytools.teamdev.reports.bungee.cmd.Commands;
import br.vootytools.teamdev.reports.bungee.cmd.cmds.ReportCommand;

public class ReportarAlias extends Commands {
   public ReportarAlias() {
      super("reportar");
   }

   public void perform(CommandSender sender, String[] args) {
      if (!(sender instanceof ProxiedPlayer)) {
         sender.sendMessage(TextComponent.fromLegacyText("§cEsse comando pode ser utilizado apenas por jogadores."));
      } else {
         ProxiedPlayer player = (ProxiedPlayer)sender;
         if (args.length == 0) {
            String usage = Language.messages$command$report$usage;
            player.sendMessage(TextComponent.fromLegacyText(usage.replace("{label}", "reportar")));
         } else {
            ReportCommand.execute(player, args);
         }

      }
   }
}
