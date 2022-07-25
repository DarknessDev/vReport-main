package br.vootytools.teamdev.reports.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import br.vootytools.teamdev.reports.Core;
import br.vootytools.teamdev.reports.utils.plugin.vLogger;

public class FileUtils {
   private static final vLogger LOGGER;

   public static void deleteFile(File file) {
      if (file.exists()) {
         if (file.isDirectory()) {
            File[] var1 = file.listFiles();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
               File f = var1[var3];
               deleteFile(f);
            }
         }

         file.delete();
      }
   }

   public static void copyFiles(File in, File out) {
      if (in.isDirectory()) {
         if (!out.exists()) {
            out.mkdirs();
         }

         File[] var2 = in.listFiles();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            File file = var2[var4];
            if (!file.getName().equals("uid.dat")) {
               copyFiles(file, new File(out, file.getName()));
            }
         }
      } else {
         try {
            copyFile(new FileInputStream(in), out);
         } catch (IOException var6) {
            LOGGER.log(Level.WARNING, "Unexpected error ocurred copying file \"" + out.getName() + "\"!", var6);
         }
      }

   }

   public static void copyFile(InputStream input, File out) {
      FileOutputStream ou = null;

      try {
         ou = new FileOutputStream(out);
         byte[] buff = new byte[1024];

         int len;
         while((len = input.read(buff)) > 0) {
            ou.write(buff, 0, len);
         }
      } catch (IOException var13) {
         LOGGER.log(Level.WARNING, "Unexpected error ocurred copying file \"" + out.getName() + "\"!", var13);
      } finally {
         try {
            if (ou != null) {
               ou.close();
            }

            if (input != null) {
               input.close();
            }
         } catch (IOException var12) {
         }

      }

   }

   static {
      LOGGER = Core.LOGGER.getModule("FILE_UTILS");
   }
}
