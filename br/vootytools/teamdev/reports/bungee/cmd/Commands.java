package br.vootytools.teamdev.reports.bungee.cmd;

import br.vootytools.teamdev.reports.bungee.Main;
import br.vootytools.teamdev.reports.bungee.cmd.cmds.ReportCommand;
import br.vootytools.teamdev.reports.bungee.cmd.cmds.ReportsCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

public abstract class Commands extends Command {
   public Commands(String name, String... aliases) {
      super(name, (String)null, aliases);
      ProxyServer.getInstance().getPluginManager().registerCommand(Main.getInstance(), this);
   }

   public abstract void perform(CommandSender var1, String[] var2);

   public void execute(CommandSender sender, String[] args) {
      this.perform(sender, args);
   }

   public static void setupCommands() {
      new ReportsCommand();
      ReportCommand.setupAliases();
   }
}
