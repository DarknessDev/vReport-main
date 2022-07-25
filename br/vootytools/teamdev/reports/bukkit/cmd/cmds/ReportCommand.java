package br.vootytools.teamdev.reports.bukkit.cmd.cmds;

import java.util.Iterator;

import br.vootytools.teamdev.reports.Language;
import br.vootytools.teamdev.reports.bukkit.utils.SendUtils;
import br.vootytools.teamdev.reports.manager.ProfileManager;
import br.vootytools.teamdev.reports.manager.ReportsManager;
import br.vootytools.teamdev.reports.menus.ReportBook;
import br.vootytools.teamdev.reports.utils.StringUtils;
import br.vootytools.teamdev.reports.utils.Validator;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import br.vootytools.teamdev.reports.bukkit.cmd.Commands;

public class ReportCommand extends Commands {
   public ReportCommand() {
      super("report", "reporte", "reportar");
   }

   public void perform(CommandSender sender, String label, String[] args) {
      if (!(sender instanceof Player)) {
         sender.sendMessage("§cEsse comando pode ser utilizado apenas por jogadores.");
      } else {
         Player player = (Player)sender;
         String uuid;
         if (args.length == 0) {
            uuid = Language.messages$command$report$usage;
            player.sendMessage(uuid.replace("{label}", label));
         } else {
            if (!Validator.isValidUsername(args[0]) || args[0].length() > 16 || args[0].length() < 2) {
               player.sendMessage(Language.messages$command$report$invalid);
               return;
            }

            if (args[0].equalsIgnoreCase(player.getName())) {
               player.sendMessage(Language.messages$command$report$self);
               return;
            }

            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
               if (ProfileManager.getUUID(args[0]) == null) {
                  player.sendMessage(Language.messages$command$report$invalid);
                  return;
               }

               uuid = ProfileManager.getUUID(args[0]);
            } else {
               uuid = target.getUniqueId().toString();
            }

            if (ReportsManager.exists(args[0].toLowerCase())) {
               player.sendMessage(Language.messages$command$report$already);
               return;
            }

            if (args.length > 1) {
               String reason = StringUtils.join((Object[])args, " ").replace(args[0] + " ", "");
               ReportsManager.insert(uuid, args[0].toLowerCase(), player.getName(), reason);
               String message = Language.messages$command$report$success;
               player.sendMessage(message.replace("{target}", args[0]).replace("{colored_target}", ProfileManager.getColored(args[0])).replace("{prefixed_target}", ProfileManager.getPrefixed(args[0])).replace("{reason}", reason));
               Iterator var9 = Bukkit.getOnlinePlayers().iterator();

               while(var9.hasNext()) {
                  Player players = (Player)var9.next();
                  if (players.hasPermission("vreports.notify")) {
                     String notify = Language.messages$command$report$notify;
                     SendUtils.sendBukkitMessage(players, notify.replace("{author}", player.getName()).replace("{target}", args[0]).replace("{reason}", reason).replace("{target}", args[0]).replace("{colored_target}", ProfileManager.getColored(args[0])).replace("{prefixed_target}", ProfileManager.getPrefixed(args[0])).replace("{colored_author}", ProfileManager.getColored(player.getName())).replace("{prefixed_author}", ProfileManager.getPrefixed(player.getName())));
                  }
               }

               return;
            }

            ReportBook.open(player, ProfileManager.getName(args[0]));
         }

      }
   }
}
