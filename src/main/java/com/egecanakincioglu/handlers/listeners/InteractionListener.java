package com.egecanakincioglu.handlers.listeners;

import com.egecanakincioglu.handlers.CommandHandler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class InteractionListener extends ListenerAdapter {

    private final CommandHandler commandHandler;

    public InteractionListener(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public void onInteractionCreate(SlashCommandInteractionEvent event) {
        if (!event.isFromGuild()) return;

        commandHandler.onSlashCommandInteraction(event);
    }
}
