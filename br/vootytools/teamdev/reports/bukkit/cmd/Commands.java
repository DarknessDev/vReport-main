package br.vootytools.teamdev.reports.bukkit.cmd;

import java.util.Arrays;
import java.util.logging.Level;

import br.vootytools.teamdev.reports.Core;
import br.vootytools.teamdev.reports.bukkit.cmd.cmds.ReportCommand;
import br.vootytools.teamdev.reports.bukkit.cmd.cmds.ReportsCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;

public abstract class Commands extends Command {
   public Commands(String name, String... aliases) {
      super(name);
      this.setAliases(Arrays.asList(aliases));

      try {
         SimpleCommandMap simpleCommandMap = (SimpleCommandMap)Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
         simpleCommandMap.register(this.getName(), "vreports", this);
      } catch (ReflectiveOperationException var4) {
         Core.LOGGER.log(Level.SEVERE, "Cannot register command: ", var4);
      }

   }

   public abstract void perform(CommandSender var1, String var2, String[] var3);

   public boolean execute(CommandSender sender, String commandLabel, String[] args) {
      this.perform(sender, commandLabel, args);
      return true;
   }

   public static void setupCommands() {
      new ReportCommand();
      new ReportsCommand();
   }
}
