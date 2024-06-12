package com.egecanakincioglu.handlers;

import com.egecanakincioglu.handlers.builders.CommandBuilder;
import com.egecanakincioglu.utils.logger.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.reflections.Reflections;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandHandler extends ListenerAdapter {
    private final Map<String, CommandBuilder> commands = new HashMap<>();

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
                Logger.command("Loaded command: " + command.getName());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                Logger.error("Failed to load command: " + commandClass.getSimpleName());
                e.printStackTrace();
            }
        }
    }

    public void registerCommands(JDA jda) {
        jda.updateCommands().addCommands(
                commands.values().stream()
                        .map(cmd -> Commands.slash(cmd.getName(), cmd.getDescription()))
                        .toArray(net.dv8tion.jda.api.interactions.commands.build.CommandData[]::new)
        ).queue();
    }

    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        CommandBuilder command = commands.get(commandName);
        if (command != null) {
            Logger.command("Executing command: " + commandName);
            command.execute(event);
        } else {
            Logger.warn("Unknown command: " + commandName);
        }
    }
}
