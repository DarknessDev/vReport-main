package br.vootytools.teamdev.reports.libraries;

import com.google.common.base.Objects;
import org.bukkit.Bukkit;
import org.bukkit.Server;

public class MinecraftVersion {
   private int major;
   private int minor;
   private int build;
   private int compareId;
   private static MinecraftVersion currentVersion;

   public MinecraftVersion(Server server) {
      this(extractVersion(server));
   }

   public MinecraftVersion(String version) {
      int[] numbers = this.parseVersion(version);
      this.major = numbers[0];
      this.minor = numbers[1];
      this.build = numbers[2];
      this.compareId = Integer.parseInt(this.major + "" + this.minor + "" + this.build);
   }

   public MinecraftVersion(int major, int minor, int build) {
      this.major = major;
      this.minor = minor;
      this.build = build;
      this.compareId = Integer.parseInt(major + "" + minor + "" + build);
   }

   public boolean lowerThan(MinecraftVersion version) {
      return this.compareId <= version.getCompareId();
   }

   public boolean newerThan(MinecraftVersion version) {
      return this.compareId >= version.getCompareId();
   }

   public boolean inRange(MinecraftVersion latest, MinecraftVersion olded) {
      return this.compareId <= latest.getCompareId() && this.compareId >= olded.getCompareId();
   }

   public int getMajor() {
      return this.major;
   }

   public int getMinor() {
      return this.minor;
   }

   public int getBuild() {
      return this.build;
   }

   public int getCompareId() {
      return this.compareId;
   }

   private int[] parseVersion(String version) {
      String[] elements = version.split("\\.");
      int[] numbers = new int[3];
      if (elements.length > 1 && version.split("R").length >= 1) {
         for(int i = 0; i < 2; ++i) {
            numbers[i] = Integer.parseInt(elements[i]);
         }

         numbers[2] = Integer.parseInt(version.split("R")[1]);
         return numbers;
      } else {
         throw new IllegalStateException("Corrupt MC Server version: " + version);
      }
   }

   public String getVersion() {
      return String.format("v%s_%s_R%s", this.major, this.minor, this.build);
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof MinecraftVersion)) {
         return false;
      } else if (obj == this) {
         return true;
      } else {
         MinecraftVersion other = (MinecraftVersion)obj;
         return this.getMajor() == other.getMajor() && this.getMinor() == other.getMinor() && this.getBuild() == other.getBuild();
      }
   }

   public int hashCode() {
      return Objects.hashCode(new Object[]{this.getMajor(), this.getMinor(), this.getBuild()});
   }

   public String toString() {
      return String.format("%s", this.getVersion());
   }

   private static String extractVersion(Server server) {
      return extractVersion(server.getClass().getPackage().getName().split("\\.")[3]);
   }

   private static String extractVersion(String version) {
      return version.replace('_', '.').replace("v", "");
   }

   public static MinecraftVersion getCurrentVersion() {
      if (currentVersion == null) {
         currentVersion = new MinecraftVersion(Bukkit.getServer());
      }

      return currentVersion;
   }
}
