package br.vootytools.teamdev.reports.utils;

import java.util.Calendar;

public class TimeUtils {
   public static boolean isNewYear() {
      Calendar cl = Calendar.getInstance();
      return cl.get(2) == 11 && cl.get(5) == 31 || cl.get(2) == 0 && cl.get(5) == 1;
   }

   public static boolean isChristmas() {
      Calendar cl = Calendar.getInstance();
      return cl.get(2) == 11 && (cl.get(5) == 24 || cl.get(5) == 25);
   }

   public static int getLastDayOfMonth(int month) {
      Calendar cl = Calendar.getInstance();
      cl.set(2, month - 1);
      return cl.getActualMaximum(5);
   }

   public static int getLastDayOfMonth() {
      return Calendar.getInstance().getActualMaximum(5);
   }

   public static long getExpireIn(int days) {
      Calendar cooldown = Calendar.getInstance();
      cooldown.set(10, 24);

      for(int day = 0; day < days - 1; ++day) {
         cooldown.add(10, 24);
      }

      cooldown.set(12, 0);
      cooldown.set(13, 0);
      return cooldown.getTimeInMillis();
   }

   public static String getTimeUntil(long epoch) {
      return getTimeUntil(epoch, true);
   }

   public static String getTimeUntil(long epoch, boolean seconds) {
      epoch -= System.currentTimeMillis();
      return getTime(epoch, seconds);
   }

   public static String getTime(long time) {
      return getTime(time, true);
   }

   public static String getTime(long time, boolean seconds) {
      long ms = time / 1000L;
      if (ms <= 0L) {
         return "";
      } else {
         StringBuilder result = new StringBuilder();
         long days = ms / 86400L;
         if (days > 0L) {
            result.append(days).append("d");
            ms -= days * 86400L;
            if (ms / 3600L > 0L) {
               result.append(" ");
            }
         }

         long hours = ms / 3600L;
         if (hours > 0L) {
            result.append(hours).append("h");
            ms -= hours * 3600L;
            if (ms / 60L > 0L) {
               result.append(" ");
            }
         }

         long minutes = ms / 60L;
         if (minutes > 0L) {
            result.append(minutes).append("m");
            ms -= minutes * 60L;
            if (ms > 0L && seconds) {
               result.append(" ");
            }
         }

         if (seconds && ms > 0L) {
            result.append(ms).append("s");
         }

         return result.toString();
      }
   }
}
