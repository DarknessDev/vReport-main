package br.vootytools.teamdev.reports.bukkit.cmd.cmds;

import br.vootytools.teamdev.reports.Language;
import br.vootytools.teamdev.reports.manager.ReportsManager;
import br.vootytools.teamdev.reports.menus.ReportsInventory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import br.vootytools.teamdev.reports.bukkit.cmd.Commands;

public class ReportsCommand extends Commands {
   public ReportsCommand() {
      super("reports");
   }

   public void perform(CommandSender sender, String label, String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage("§cEsse comando pode ser utilizado apenas por jogadores.");
      } else {
         Player player = (Player)sender;
         if (!player.hasPermission("vreports.cmd")) {
            player.sendMessage(Language.messages$command$reports$not_perm);
         } else {
            if (args.length == 0) {
               player.sendMessage(Language.messages$command$reports$help);
            } else if (args[0].equalsIgnoreCase("clear")) {
               int amount = ReportsManager.clear();
               if (amount != 0) {
                  String message = Language.messages$command$reports$cleaned;
                  player.sendMessage(message.replace("{amount}", String.valueOf(amount)).replace("{s}", amount == 1 ? "" : "s"));
               } else {
                  player.sendMessage(Language.messages$command$reports$empty);
               }
            } else if (args[0].equalsIgnoreCase("list")) {
               if (!ReportsManager.isEmpty()) {
                  new ReportsInventory(player);
               } else {
                  player.sendMessage(Language.messages$command$reports$empty);
               }
            } else {
               player.sendMessage(Language.messages$command$reports$help);
            }

         }
      }
   }
}
