package br.vootytools.teamdev.reports.menus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import br.vootytools.teamdev.reports.Language;
import br.vootytools.teamdev.reports.bukkit.Main;
import br.vootytools.teamdev.reports.bukkit.utils.BukkitUtils;
import br.vootytools.teamdev.reports.comunication.BukkitComunication;
import br.vootytools.teamdev.reports.libraries.menu.PagedPlayerMenu;
import br.vootytools.teamdev.reports.manager.ProfileManager;
import br.vootytools.teamdev.reports.manager.ReportsManager;
import br.vootytools.teamdev.reports.utils.StringUtils;
import br.vootytools.teamdev.reports.utils.enums.EnumSound;

public class ReportsInventory extends PagedPlayerMenu {
   @EventHandler
   public void onInventoryClick(InventoryClickEvent evt) {
      if (evt.getInventory().equals(this.getCurrentInventory())) {
         evt.setCancelled(true);
         if (evt.getWhoClicked().equals(this.player) && evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getCurrentInventory())) {
            ItemStack item = evt.getCurrentItem();
            if (item != null && item.getType() != Material.AIR) {
               if (evt.getSlot() == this.previousPage) {
                  EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                  this.openPrevious();
               } else if (evt.getSlot() == this.nextPage) {
                  EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                  this.openNext();
               } else if (evt.getSlot() == this.clearSlot) {
                  this.player.closeInventory();
                  EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                  if (Main.getInstance().bungeecord) {
                     BukkitComunication.sendPluginMessage(this.player, "ClearReports");
                  } else {
                     this.player.chat("/reports clear");
                  }
               } else {
                  String report = getReport(item.getItemMeta().getDisplayName());
                  if (ReportsManager.exists(report.toLowerCase())) {
                     String execute;
                     if (evt.getClick() != ClickType.SHIFT_RIGHT && evt.getClick() != ClickType.SHIFT_LEFT) {
                        if (evt.getClick() != ClickType.RIGHT && evt.getClick() != ClickType.LEFT) {
                           return;
                        }

                        execute = Language.munus$reports_inventory$skulls$execute;
                        this.player.chat(execute.replace("{sender}", this.player.getName()).replace("{target}", report).replace("{colored_target}", ProfileManager.getColored(report)).replace("{prefixed_target}", ProfileManager.getPrefixed(report)));
                     } else {
                        ReportsManager.remove(report.toLowerCase());
                        EnumSound.BURP.play(this.player, 1.0F, 1.0F);
                        execute = Language.messages$command$reports$removed;
                        this.player.sendMessage(execute.replace("{target}", report).replace("{colored_target}", ProfileManager.getColored(report)).replace("{prefixed_target}", ProfileManager.getPrefixed(report)));
                     }

                     this.player.closeInventory();
                  }
               }
            }
         }
      }

   }

   private static String getReport(String str) {
      if (str.contains(" ")) {
         String[] splitter = str.split(" ");
         str = splitter[1];
      }

      return StringUtils.stripColors(str);
   }

   public ReportsInventory(Player player) {
      super(player, Language.munus$reports_inventory$title, Language.munus$reports_inventory$rows);
      this.nextPage = Language.munus$reports_inventory$next$slot;
      this.previousPage = Language.munus$reports_inventory$previous$slot;
      this.nextStack = Language.munus$reports_inventory$next$stack;
      this.previousStack = Language.munus$reports_inventory$previous$stack;
      this.clearSlot = Language.munus$reports_inventory$clear$slot;
      this.clearStack = Language.munus$reports_inventory$clear$stack;
      this.onlySlots(StringUtils.getIntegerList(Language.munus$reports_inventory$skulls$slots));
      List<ItemStack> items = new ArrayList();
      Iterator var3 = ReportsManager.get().iterator();

      while(var3.hasNext()) {
         String data = (String)var3.next();
         String lore = BukkitUtils.getLore(Language.munus$reports_inventory$skulls$stack) == null ? "" : BukkitUtils.getLore(Language.munus$reports_inventory$skulls$stack);
         String uuid = data.split(" : ")[0];
         String name = ProfileManager.getName(data.split(" : ")[1]);
         String author = data.split(" : ")[2];
         String reason = StringUtils.join((Object[])StringUtils.split(data.split(" : ")[3], 30), "\n" + (((String)Objects.requireNonNull(lore)).contains("{reason}") ? StringUtils.getLastColor(lore.split("reason}")[0]) : "§f"));
         String time = (new SimpleDateFormat("dd/MM/yyyy")).format(Long.parseLong(data.split(" : ")[4]));
         String icon = Language.munus$reports_inventory$skulls$stack;
         ItemStack stack = BukkitUtils.deserializeItemStack(icon.replace("{uuid}", uuid).replace("{accused}", (CharSequence)Objects.requireNonNull(name)).replace("{author}", author).replace("{reason}", reason).replace("{time}", time).replace("{colored_accused}", ProfileManager.getColored(name)).replace("{colored_author}", ProfileManager.getColored(author)).replace("{prefixed_accused}", ProfileManager.getPrefixed(name)).replace("{prefixed_author}", ProfileManager.getPrefixed(author)));
         items.add(stack);
      }

      this.setItems(items);
      items.clear();
      this.register(Main.getInstance());
      this.open();
   }

   public void cancel() {
      HandlerList.unregisterAll(this);
   }

   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent evt) {
      if (evt.getPlayer().equals(this.player)) {
         this.cancel();
      }

   }

   @EventHandler
   public void onInventoryClose(InventoryCloseEvent evt) {
      if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(this.getCurrentInventory())) {
         this.cancel();
      }

   }
}
