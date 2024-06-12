package com.egecanakincioglu.commands.auth;

import com.egecanakincioglu.handlers.builders.CommandBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class CancelAuth extends CommandBuilder {
    public CancelAuth() {
        super("cancelauth", "Cancels authentication");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("Authentication cancelled!").queue();
    }
}
