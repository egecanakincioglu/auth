package com.egecanakincioglu.commands.auth;

import com.egecanakincioglu.handlers.builders.CommandBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class SetupAuth extends CommandBuilder {
    public SetupAuth() {
        super("setupauth", "Sets up authentication");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("Authentication setup complete!").queue();
    }
}
