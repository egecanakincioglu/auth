package com.egecanakincioglu.handlers.builders;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandBuilder {
    private final String name;
    private final String description;
    private final List<OptionData> options = new ArrayList<>();

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

    protected void addOption(OptionType type, String name, String description, boolean required) {
        this.options.add(new OptionData(type, name, description).setRequired(required));
    }

    public List<OptionData> getOptions() {
        return options;
    }

    public abstract void execute(SlashCommandInteractionEvent event);

    public SlashCommandData getSlashCommandData() {
        SlashCommandData commandData = Commands.slash(name, description);
        commandData.addOptions(options);
        return commandData;
    }
}