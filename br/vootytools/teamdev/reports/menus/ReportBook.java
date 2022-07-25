package br.vootytools.teamdev.reports.menus;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import br.vootytools.teamdev.reports.Language;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import br.vootytools.teamdev.reports.bukkit.utils.BukkitUtils;
import br.vootytools.teamdev.reports.bukkit.utils.SendUtils;
import br.vootytools.teamdev.reports.manager.ProfileManager;

public class ReportBook {
   public static void open(Player player, String target) {
      TextComponent COMPONENT = new TextComponent("");
      String header = Language.munus$report_book$header;
      COMPONENT.addExtra(SendUtils.getJsonFromString(header.replace("{target}", target).replace("{colored_target}", ProfileManager.getColored(target)).replace("{prefixed_target}", ProfileManager.getPrefixed(target))));
      Iterator var4 = Arrays.asList("Hack", "Discriminação", "Time ou Aliança", "Ofensa à jogador", "Flood", "Racismo").iterator();

      String footer;
      while(var4.hasNext()) {
         footer = (String)var4.next();
         String format = Language.munus$report_book$reason$format;
         COMPONENT.addExtra(SendUtils.getJsonFromString("\n" + format.replace("{target}", target).replace("{colored_target}", ProfileManager.getColored(target)).replace("{reason}", footer)));
      }

      String custom = Language.munus$report_book$reason$custom;
      COMPONENT.addExtra(SendUtils.getJsonFromString("\n \n" + custom.replace("{target}", target).replace("{colored_target}", ProfileManager.getColored(target)).replace("{prefixed_target}", ProfileManager.getPrefixed(target))));
      footer = Language.munus$report_book$footer;
      COMPONENT.addExtra(SendUtils.getJsonFromString(footer.replace("{target}", target).replace("{colored_target}", ProfileManager.getColored(target)).replace("{prefixed_target}", ProfileManager.getPrefixed(target))));
      ItemStack book = BukkitUtils.deserializeItemStack("WRITTEN_BOOK : 1 : autor>VootyTools : titulo>Escolher motivo");
      book = BukkitUtils.setNBTList(book, "pages", Collections.singletonList(ComponentSerializer.toString(COMPONENT)));
      BukkitUtils.openBook(player, book);
   }
}
