package com.egecanakincioglu.events;

import javax.annotation.Nonnull;

import com.egecanakincioglu.handlers.CommandHandler;
import com.egecanakincioglu.handlers.builders.CommandBuilder;
import com.egecanakincioglu.utils.system.Logger;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
        String commandName = event.getName();
        CommandBuilder command = CommandHandler.commands.get(commandName);

        if (command != null) {
            Logger.command("Executing command: " + commandName);
            command.execute(event);
        } else {
            Logger.warn("Unknown command: " + commandName);
        }
    }
}
