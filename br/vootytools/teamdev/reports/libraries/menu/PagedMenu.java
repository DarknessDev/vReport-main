package br.vootytools.teamdev.reports.libraries.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import br.vootytools.teamdev.reports.bukkit.utils.BukkitUtils;

public class PagedMenu {
   protected int rows;
   protected String name;
   protected int currentPage;
   protected List<Menu> menus;
   protected Map<Menu, Integer> id;
   protected Map<Integer, ItemStack> slots;
   public int previousPage;
   public int nextPage;
   public int clearSlot;
   public String previousStack;
   public String nextStack;
   public String clearStack;
   protected int lastListSize;

   public PagedMenu(String name) {
      this(name, 3);
   }

   public PagedMenu(String name, int rows) {
      this.currentPage = 1;
      this.menus = new ArrayList();
      this.id = new HashMap();
      this.slots = new HashMap();
      this.previousPage = 45;
      this.nextPage = 53;
      this.clearSlot = 49;
      this.previousStack = "INK_SACK:8 : 1 : nome>&aPágina {page}";
      this.nextStack = "INK_SACK:10 : 1 : nome>&aPágina {page}";
      this.clearStack = "AIR : 1";
      this.lastListSize = -1;
      this.rows = rows > 6 ? 6 : Math.max(rows, 1);
      this.name = name;
   }

   public void open(Player player) {
      player.openInventory(((Menu)this.menus.get(0)).getInventory());
   }

   public void openPrevious(Player player, Inventory inv) {
      int currentPage = (Integer)this.id.get(this.getCurrent(inv));
      if (currentPage != 1) {
         player.openInventory(((Menu)this.menus.get(currentPage - 2)).getInventory());
      }
   }

   public void openNext(Player player, Inventory inv) {
      int currentPage = (Integer)this.id.get(this.getCurrent(inv));
      if (currentPage + 1 <= this.menus.size()) {
         player.openInventory(((Menu)this.menus.get(currentPage)).getInventory());
      }
   }

   public void onlySlots(Integer... slots) {
      this.onlySlots(Arrays.asList(slots));
   }

   public void onlySlots(List<Integer> slots) {
      for(int slot = 0; slot < this.rows * 9; ++slot) {
         if (!slots.contains(slot)) {
            this.slots.put(slot, (ItemStack) null);
         }
      }

   }

   public void removeSlots(int... slots) {
      this.removeSlotsWith((ItemStack)null, slots);
   }

   public void removeSlotsWith(ItemStack item, int... slots) {
      int[] var3 = slots;
      int var4 = slots.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         int slot = var3[var5];
         this.slots.put(slot, item);
      }

   }

   public void setItems(List<ItemStack> items) {
      if (items.size() == this.lastListSize) {
         this.updateItems(items);
      } else {
         this.menus.forEach((menux) -> {
            menux.getInventory().getViewers().forEach(HumanEntity::closeInventory);
         });
         this.menus.clear();
         this.lastListSize = items.size();
         List<List<ItemStack>> splitted = this.split(items);
         if (splitted.isEmpty()) {
            splitted.add(new ArrayList());
         }

         for(int i = 0; i < splitted.size(); ++i) {
            List<ItemStack> list = (List)splitted.get(i);
            Menu menu = new Menu(this.name, this.rows);
            this.slots.forEach((key, value) -> {
               menu.getSlots().remove(key);
               if (value != null) {
                  menu.setItem(key, value);
               }

            });
            menu.setItems(list);
            if (!this.clearStack.equals("AIR : 1")) {
               menu.setItem(this.clearSlot, BukkitUtils.deserializeItemStack(this.clearStack));
            }

            if (splitted.size() > 1) {
               if (i > 0 && this.previousPage != -1) {
                  menu.setItem(this.previousPage, BukkitUtils.deserializeItemStack(this.previousStack.replace("{page}", String.valueOf(i))));
               }

               if (i + 1 != splitted.size() && this.nextPage != -1) {
                  menu.setItem(this.nextPage, BukkitUtils.deserializeItemStack(this.nextStack.replace("{page}", String.valueOf(i + 2))));
               }
            }

            this.menus.add(menu);
            this.id.put(menu, i + 1);
         }

      }
   }

   public void updateItems(List<ItemStack> items) {
      List<List<ItemStack>> splitted = this.split(items);
      if (splitted.isEmpty()) {
         splitted.add(new ArrayList());
      }

      for(int i = 0; i < splitted.size(); ++i) {
         Menu menu = (Menu)this.menus.get(i);
         this.slots.forEach((key, value) -> {
            if (value != null) {
               menu.setItem(key, value);
            }

         });
         menu.setItems((List)splitted.get(i));
      }

   }

   public Menu getCurrent(Inventory inv) {
      Iterator var2 = this.menus.iterator();

      Menu menu;
      do {
         if (!var2.hasNext()) {
            return (Menu)this.menus.get(0);
         }

         menu = (Menu)var2.next();
      } while(!menu.getInventory().equals(inv));

      return menu;
   }

   public Inventory getCurrentInventory() {
      return ((Menu)this.menus.get(this.currentPage - 1)).getInventory();
   }

   private List<List<ItemStack>> split(List<ItemStack> items) {
      List<List<ItemStack>> list = new ArrayList();
      List<ItemStack> toadd = new ArrayList();

      for(int size = 1; size - 1 < items.size(); ++size) {
         toadd.add(items.get(size - 1));
         if (size % (this.rows * 9 - this.slots.size()) == 0) {
            list.add(toadd);
            toadd = new ArrayList();
         }

         if (size == items.size()) {
            if (!toadd.isEmpty()) {
               list.add(toadd);
            }
            break;
         }
      }

      return list;
   }
}
