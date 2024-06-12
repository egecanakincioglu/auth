package com.egecanakincioglu.commands.moderation;

import com.egecanakincioglu.handlers.builders.CommandBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Ping extends CommandBuilder {
    public Ping() {
        super("ping", "Returns pong");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        try {
            event.reply("Pong!").queue();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
