package com.egecanakincioglu.handlers;

import com.egecanakincioglu.handlers.builders.CommandBuilder;
import com.egecanakincioglu.utils.logger.LogFactory;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.reflections.Reflections;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandHandler extends ListenerAdapter {
    public static final Map<String, CommandBuilder> commands = new HashMap<>();

    public CommandHandler() {
        loadCommands();
    }

    private void loadCommands() {
        Reflections reflections = new Reflections("com.egecanakincioglu.commands");
        Set<Class<? extends CommandBuilder>> commandClasses = reflections.getSubTypesOf(CommandBuilder.class);

        for (Class<? extends CommandBuilder> commandClass : commandClasses) {
            try {
                CommandBuilder command = commandClass.getDeclaredConstructor().newInstance();
                commands.put(command.getName(), command);
                LogFactory.command("Loaded command: " + command.getName());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                    | NoSuchMethodException e) {
                LogFactory.error("Failed to load command: " + commandClass.getSimpleName());
                e.printStackTrace();
            }
        }
    }

    public void registerCommands(JDA jda) {
        jda.updateCommands().addCommands(
                commands.values().stream()
                        .map(cmd -> Commands.slash(cmd.getName(), cmd.getDescription()))
                        .toArray(net.dv8tion.jda.api.interactions.commands.build.CommandData[]::new))
                .queue();
    }
}
