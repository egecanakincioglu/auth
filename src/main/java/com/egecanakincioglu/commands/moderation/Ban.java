package com.egecanakincioglu.commands.moderation;

import com.egecanakincioglu.handlers.builders.CommandBuilder;
import com.egecanakincioglu.interfaces.moderation.BanInterface;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Ban extends CommandBuilder {
    public Ban() {
        super("ban", "Bans a member from the server with a reason.");
        this.addOption(OptionType.USER, "user", "The user to ban", true);
        this.addOption(OptionType.STRING, "reason", "The reason for the ban", false);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Member member = event.getMember();

        if (member == null || !member.hasPermission(Permission.BAN_MEMBERS)) {
            event.reply("You don't have permission to ban members!").setEphemeral(true).queue();
            return;
        }

        User targetUser = Objects.requireNonNull(event.getOption("user")).getAsUser();
        String reason = event.getOption("reason") != null ? event.getOption("reason").getAsString() : "No reason provided";

        event.deferReply().queue();
        Guild guild = Objects.requireNonNull(event.getGuild());
        Member targetMember = guild.retrieveMember(targetUser).complete();

        if (targetMember == null) {
            event.getHook().sendMessage("User not found in this server.").setEphemeral(true).queue();
            return;
        }

        if (!member.canInteract(targetMember)) {
            event.getHook().sendMessage("You cannot ban this member due to role hierarchy.").setEphemeral(true).queue();
            return;
        }

        BanInterface banInterface = new BanInterface();
        targetUser.openPrivateChannel().queue(privateChannel -> {
            privateChannel.sendMessageEmbeds(banInterface.createBanNotificationEmbed(targetMember, reason, guild.getName()).build()).queue(
                    message -> {
                        guild.ban(UserSnowflake.fromId(targetUser.getId()), 7, TimeUnit.DAYS).reason(reason).queue(
                                success -> event.getHook().sendMessageEmbeds(banInterface.createBanSuccessEmbed(targetUser, reason).build()).queue(),
                                error -> event.getHook().sendMessageEmbeds(banInterface.createBanFailureEmbed().build()).queue()
                        );
                    },
                    error -> {
                        guild.ban(UserSnowflake.fromId(targetUser.getId()), 7, TimeUnit.DAYS).reason(reason).queue(
                                success -> event.getHook().sendMessageEmbeds(banInterface.createBanSuccessEmbed(targetUser, reason).build()).queue(),
                                error2 -> event.getHook().sendMessageEmbeds(banInterface.createBanFailureEmbed().build()).queue()
                        );
                    }
            );
        }, error -> {
            event.getGuild().ban(UserSnowflake.fromId(targetUser.getId()), 7, TimeUnit.DAYS).reason(reason).queue(
                    success -> event.getHook().sendMessageEmbeds(banInterface.createBanSuccessEmbed(targetUser, reason).build()).queue(),
                    error2 -> event.getHook().sendMessageEmbeds(banInterface.createBanFailureEmbed().build()).queue()
            );
        });
    }
}