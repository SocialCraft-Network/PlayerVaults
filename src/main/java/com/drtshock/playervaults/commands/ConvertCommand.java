package com.drtshock.playervaults.commands;

import com.drtshock.playervaults.converters.BackpackConverter;
import com.drtshock.playervaults.converters.Converter;
import com.drtshock.playervaults.util.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ConvertCommand implements CommandExecutor {

    private List<Converter> converters = new ArrayList<>();

    public ConvertCommand() {
        converters.add(new BackpackConverter());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("playervaults.convert")) {
            sender.sendMessage(Lang.TITLE.toString() + Lang.NO_PERMS);
        } else {
            if (args.length == 0) {
                sender.sendMessage(Lang.TITLE + "/pvconvert <all | plugin name>");
            } else {
                String name = args[0];
                List<Converter> applicableConverters = new ArrayList<>();
                if (name.equalsIgnoreCase("all")) {
                    applicableConverters.addAll(converters);
                } else {
                    for (Converter converter : converters) {
                        if (converter.getName().equalsIgnoreCase(name)) {
                            applicableConverters.add(converter);
                        }
                    }
                }

                if (applicableConverters.size() <= 0) {
                    sender.sendMessage(Lang.TITLE.toString() + Lang.CONVERT_PLUGIN_NOT_FOUND);
                } else {
                    int converted = 0;
                    for (Converter converter : applicableConverters) {
                        if (converter.canConvert()) {
                            converted += converter.doConvert();
                        }
                    }
                    sender.sendMessage(Lang.TITLE + Lang.CONVERT_COMPLETE.toString().replace("%converted", converted + ""));
                }
            }
        }

        return true;
    }
}