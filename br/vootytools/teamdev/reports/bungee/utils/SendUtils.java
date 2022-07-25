package br.vootytools.teamdev.reports.bungee.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SendUtils {
   public static void sendBungeeMessage(ProxiedPlayer proxiedPlayer, String message) {
      proxiedPlayer.sendMessage(getJsonFromString(message));
   }

   public static TextComponent getJsonFromString(String string) {
      TextComponent component = new TextComponent("");
      String[] rawtext;
      int var4;
      int var11;
      if (!string.contains("/-/")) {
         TextComponent action;
         if (string.contains(": ttp>")) {
            rawtext = string.split(" : ");
            action = new TextComponent(rawtext[0]);
            if (string.contains(": exe>")) {
               action.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
               action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, rawtext[2].replace("exe>", "")));
               component.addExtra(action);
            } else if (string.contains(": sgt>")) {
               action.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
               action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, rawtext[2].replace("sgt>", "")));
               component.addExtra(action);
            } else if (string.contains(": url>")) {
               action.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
               action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, rawtext[2].replace("url>", "")));
               component.addExtra(action);
            } else {
               action.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(rawtext[1].replace("ttp>", ""))));
               component.addExtra(action);
            }
         } else if (string.contains(": exe>")) {
            rawtext = string.split(" : ");
            action = new TextComponent(rawtext[0]);
            action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, rawtext[1].replace("exe>", "")));
            component.addExtra(action);
         } else if (string.contains(": sgt>")) {
            rawtext = string.split(" : ");
            action = new TextComponent(rawtext[0]);
            action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, rawtext[1].replace("sgt>", "")));
            component.addExtra(action);
         } else if (string.contains(": url>")) {
            rawtext = string.split(" : ");
            action = new TextComponent(rawtext[0]);
            action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, rawtext[1].replace("url>", "")));
            component.addExtra(action);
         } else {
            BaseComponent[] var10 = TextComponent.fromLegacyText(string);
            var11 = var10.length;

            for(var4 = 0; var4 < var11; ++var4) {
               BaseComponent components = var10[var4];
               component.addExtra(components);
            }
         }
      } else {
         rawtext = string.split("/-/");
         var11 = rawtext.length;

         for(var4 = 0; var4 < var11; ++var4) {
            String cluster = rawtext[var4];
            String[] split;
            TextComponent action;
            if (cluster.contains(": ttp>")) {
               split = cluster.split(" : ");
               action = new TextComponent(split[0]);
               if (cluster.contains(": exe>")) {
                  action.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(split[1].replace("ttp>", ""))));
                  action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, split[2].replace("exe>", "")));
                  component.addExtra(action);
               } else if (cluster.contains(": sgt>")) {
                  action.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(split[1].replace("ttp>", ""))));
                  action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, split[2].replace("sgt>", "")));
                  component.addExtra(action);
               } else if (cluster.contains(": url>")) {
                  action.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(split[1].replace("ttp>", ""))));
                  action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, split[2].replace("url>", "")));
                  component.addExtra(action);
               } else {
                  action.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText(split[1].replace("ttp>", ""))));
                  component.addExtra(action);
               }
            } else if (cluster.contains(": exe>")) {
               split = cluster.split(" : ");
               action = new TextComponent(split[0]);
               action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, split[1].replace("exe>", "")));
               component.addExtra(action);
            } else if (cluster.contains(": sgt>")) {
               split = cluster.split(" : ");
               action = new TextComponent(split[0]);
               action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, split[1].replace("sgt>", "")));
               component.addExtra(action);
            } else if (cluster.contains(": url>")) {
               split = cluster.split(" : ");
               action = new TextComponent(split[0]);
               action.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.OPEN_URL, split[1].replace("url>", "")));
               component.addExtra(action);
            } else {
               BaseComponent[] var6 = TextComponent.fromLegacyText(cluster);
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  BaseComponent components = var6[var8];
                  component.addExtra(components);
               }
            }
         }
      }

      return component;
   }
}
