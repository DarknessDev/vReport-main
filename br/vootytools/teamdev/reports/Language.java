package br.vootytools.teamdev.reports;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import br.vootytools.teamdev.reports.utils.StringUtils;
import br.vootytools.teamdev.reports.utils.plugin.vConfig;
import br.vootytools.teamdev.reports.utils.plugin.vLogger;
import br.vootytools.teamdev.reports.utils.plugin.vWriter;

public class Language {
   public static String messages$command$report$usage = "§cPara reportar utilize /report [jogador]";
   public static String messages$command$report$invalid = "§cEste jogador não existe!";
   public static String messages$command$report$self = "§cVocê não pode reportar a si mesmo!";
   public static String messages$command$report$already = "§cEste jogador já foi reportado antes!";
   public static String messages$command$report$success = "§aVocê reportou o jogador {colored_target} §apor §f{reason}§a.";
   public static String messages$command$report$event = " \n§aDigite o motivo do report no chat\n§7Ou clique /-/§7§lAQUI : ttp>§7Clique para cancelar! : exe>event.cancel_custom_report/-/ §7para cancelar essa operação\n ";
   public static String messages$command$report$cancelled = "§cO report foi cancelado com sucesso!";
   public static String messages$command$report$notify;
   public static String messages$command$reports$not_perm;
   public static String messages$command$reports$help;
   public static String messages$command$reports$empty;
   public static String messages$command$reports$removed;
   public static String messages$command$reports$cleaned;
   public static String munus$report_book$header;
   public static String munus$report_book$reason$format;
   public static String munus$report_book$reason$custom;
   public static String munus$report_book$footer;
   public static String munus$reports_inventory$title;
   public static Integer munus$reports_inventory$rows;
   public static String munus$reports_inventory$skulls$stack;
   public static String munus$reports_inventory$skulls$slots;
   @vWriter.YamlEntryInfo(
      annotation = "placeholders: {prefixed_target}, {colored_target}, {target} and {sender}"
   )
   public static String munus$reports_inventory$skulls$execute;
   public static String munus$reports_inventory$next$stack;
   public static Integer munus$reports_inventory$next$slot;
   public static String munus$reports_inventory$previous$stack;
   public static Integer munus$reports_inventory$previous$slot;
   public static String munus$reports_inventory$clear$stack;
   public static Integer munus$reports_inventory$clear$slot;
   public static final vLogger LOGGER;
   private static final vConfig CONFIG;

   public static void setupSettings() {
      boolean save = false;
      vWriter writer = new vWriter(CONFIG.getFile(), "vReports - Criado por VootyTeam\nVersão da configuração: " + Core.getVersion());
      Field[] var2 = Language.class.getDeclaredFields();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Field field = var2[var4];
         if (field.getName().contains("$") && !Modifier.isFinal(field.getModifiers())) {
            String nativeName = field.getName().replace("$", ".").replace("_", "-");

            try {
               vWriter.YamlEntryInfo entryInfo = (vWriter.YamlEntryInfo)field.getAnnotation(vWriter.YamlEntryInfo.class);
               Object value;
               List l;
               ArrayList list;
               Iterator var11;
               Object v;
               if (CONFIG.contains(nativeName)) {
                  value = CONFIG.get(nativeName);
                  if (value instanceof String) {
                     value = StringUtils.formatColors((String)value).replace("\\n", "\n");
                  } else if (value instanceof List) {
                     l = (List)value;
                     list = new ArrayList(l.size());
                     var11 = l.iterator();

                     while(var11.hasNext()) {
                        v = var11.next();
                        if (v instanceof String) {
                           list.add(StringUtils.formatColors((String)v).replace("\\n", "\n"));
                        } else {
                           list.add(v);
                        }
                     }

                     value = list;
                  }

                  field.set((Object)null, value);
                  writer.set(nativeName, new vWriter.YamlEntry(new Object[]{entryInfo == null ? "" : entryInfo.annotation(), CONFIG.get(nativeName)}));
               } else {
                  value = field.get((Object)null);
                  if (value instanceof String) {
                     value = StringUtils.deformatColors((String)value).replace("\n", "\\n");
                  } else if (value instanceof List) {
                     l = (List)value;
                     list = new ArrayList(l.size());
                     var11 = l.iterator();

                     while(var11.hasNext()) {
                        v = var11.next();
                        if (v instanceof String) {
                           list.add(StringUtils.deformatColors((String)v).replace("\n", "\\n"));
                        } else {
                           list.add(v);
                        }
                     }

                     value = list;
                  }

                  save = true;
                  writer.set(nativeName, new vWriter.YamlEntry(new Object[]{entryInfo == null ? "" : entryInfo.annotation(), value}));
               }
            } catch (ReflectiveOperationException var13) {
               LOGGER.log(Level.WARNING, "Unexpected error on settings file: ", var13);
            }
         }
      }

      if (save) {
         writer.write();
         CONFIG.reload();
         Core.delayedLog("A config §6language.yml §afoi modificada ou criada.");
      }

   }

   static {
      messages$command$report$notify = Core.BUNGEE ? " \n §c§lNOVO REPORT!\n \n§7 autor: §f{author}\n§7 acusado: §f{target}\n§7 servidor: §f/-/{server} : ttp>§7Clique para conectar ao servidor §f'{server}'§7! : exe>/server {server}/-/\n§7 motivo: §f{reason}\n \n §aClique /-/§6§lAQUI : ttp>§eClique para ver a lista de reports\n§fComando: §7/reports list : exe>/reports list/-/§a para ver a lista de reports!\n " : " \n §a§lNOVO REPORT!\n \n§7 autor: §f{author}\n§7 acusado: §f{target}\n§7 motivo: §f{reason}\n \n §aClique /-/§6§lAQUI : ttp>§eClique para ver a lista de reports\n§fComando: §7/reports list : exe>/reports list/-/§a para ver a lista de reports!\n ";
      messages$command$reports$not_perm = "§cVocê não possui permissão para utilizar este comando.";
      messages$command$reports$help = " \n§eAjuda - Reports\n \n§3/reports list §f- §7Listar todos reports.\n§3/reports clear §f- §7Limpar lista de reports.\n ";
      messages$command$reports$empty = "§cA lista de reports está vazia!";
      messages$command$reports$removed = "§aVocê removeu o report sobre {colored_target}§a!";
      messages$command$reports$cleaned = "§aVocê limpou {amount} report{s}!";
      munus$report_book$header = "       §5§lREPORTAR\n \n§0Acusado: {colored_target}\n§0Escolha um motivo:\n ";
      munus$report_book$reason$format = " §8▪ {reason} : ttp>§7Clique para selecionar o motivo §f'{reason}'§7! : exe>/report {target} {reason}";
      munus$report_book$reason$custom = " §3▪ Outro : ttp>§7Clique para escolher um motivo! : exe>event.custom_report_{target}";
      munus$report_book$footer = "\n \n§c§lCANCELAR DENÚNCIA : ttp>§cClique para cancelar o report de {target}! : exe>event.cancel_report";
      munus$reports_inventory$title = "Denúncias";
      munus$reports_inventory$rows = 6;
      munus$reports_inventory$skulls$stack = "SKULL_ITEM:3 : 1 : dono>{accused} : nome>&a{colored_accused} : desc>§8{uuid}\n \n§7Autor: §f{colored_author}\n§7Motivo: §f{reason}\n§7Reportado em: §f{time}\n \n§eClique segurando shift para remover!\n§aClique aqui para assistir {accused}!";
      munus$reports_inventory$skulls$slots = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43).toString();
      munus$reports_inventory$skulls$execute = "/assistir {target}";
      munus$reports_inventory$next$stack = "INK_SACK:10 : 1 : nome>&aPágina {page}";
      munus$reports_inventory$next$slot = 44;
      munus$reports_inventory$previous$stack = "INK_SACK:8 : 1 : nome>&aPágina {page}";
      munus$reports_inventory$previous$slot = 36;
      munus$reports_inventory$clear$stack = "BARRIER : 1 : nome>&aLimpar : desc>&7Você também pode limpar\n§7esta lista de reportes utilizando\n§7o comando §f\"/reports clear\"§7!\n \n&aClique para limpar rápidamente!";
      munus$reports_inventory$clear$slot = 49;
      LOGGER = Core.LOGGER.getModule("LANGUAGE");
      CONFIG = vConfig.getConfig("language");
   }
}
