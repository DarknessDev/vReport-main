package br.vootytools.teamdev.reports.libraries.menu;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class Menu {
   private Inventory inventory;
   private List<Integer> slots;

   public Menu() {
      this("", 3);
   }

   public Menu(String title) {
      this(title, 3);
   }

   public Menu(String title, int rows) {
      this.slots = new ArrayList();
      this.inventory = Bukkit.createInventory((InventoryHolder)null, Math.min(Math.max(1, rows), 6) * 9, title);

      for(int i = 0; i < this.inventory.getSize(); ++i) {
         this.slots.add(i);
      }

   }

   public void setItem(int slot, ItemStack item) {
      this.inventory.setItem(slot, item);
   }

   public void setItems(List<ItemStack> items) {
      for(int i = 0; i < this.slots.size() && i < items.size(); ++i) {
         ItemStack item = (ItemStack)items.get(i);
         this.inventory.setItem((Integer)this.slots.get(i), item);
      }

   }

   public void remove(int slot) {
      this.inventory.setItem(slot, new ItemStack(Material.AIR));
   }

   public void removeAll() {
      this.slots.forEach((slot) -> {
         this.inventory.setItem(slot, new ItemStack(Material.AIR));
      });
   }

   public void clear() {
      this.inventory.clear();
   }

   public ItemStack getItem(int slot) {
      return this.inventory.getItem(slot);
   }

   public ItemStack[] getContents() {
      return this.inventory.getContents();
   }

   public Inventory getInventory() {
      return this.inventory;
   }

   public List<Integer> getSlots() {
      return this.slots;
   }
}
