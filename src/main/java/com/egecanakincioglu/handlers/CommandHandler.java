package com.egecanakincioglu.handlers;

import com.egecanakincioglu.handlers.builders.CommandBuilder;
import com.egecanakincioglu.services.language.StringManager;
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
        final String COMMAND_HANDLER_PACKAGE = StringManager.getCommandHandlerPackage();
        if (COMMAND_HANDLER_PACKAGE == null) {
            throw new IllegalArgumentException("Command handler package cannot be null");
        }

        final String COMMAND_LOADED = StringManager.getCommandLoaded();
        final String COMMAND_FAILED_TO_LOAD = StringManager.getCommandFailedToLoad();

        Reflections reflections = new Reflections(COMMAND_HANDLER_PACKAGE);
        Set<Class<? extends CommandBuilder>> commandClasses = reflections.getSubTypesOf(CommandBuilder.class);

        for (Class<? extends CommandBuilder> commandClass : commandClasses) {
            try {
                CommandBuilder command = commandClass.getDeclaredConstructor().newInstance();
                commands.put(command.getName(), command);
                LogFactory.command(COMMAND_LOADED + command.getName());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                    | NoSuchMethodException e) {
                LogFactory.error(COMMAND_FAILED_TO_LOAD + commandClass.getSimpleName());
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
