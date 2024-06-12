package com.egecanakincioglu.handlers.builders;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public abstract class CommandBuilder {
    private final String name;
    private final String description;

    public CommandBuilder(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract void execute(SlashCommandInteractionEvent event);
}
