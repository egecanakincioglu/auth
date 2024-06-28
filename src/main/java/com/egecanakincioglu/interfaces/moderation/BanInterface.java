package com.egecanakincioglu.interfaces.moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.awt.Color;
import java.time.Instant;

public class BanInterface {

    public EmbedBuilder createBanNotificationEmbed(Member member, String reason, String guildName) {
        return new EmbedBuilder()
                .setTitle("You have been banned")
                .setColor(Color.RED)
                .setDescription("You have been banned from " + guildName)
                .addField("Reason", reason, false)
                .setTimestamp(Instant.now());
    }

    public EmbedBuilder createBanSuccessEmbed(User user, String reason) {
        return new EmbedBuilder()
                .setTitle("Member Banned")
                .setColor(Color.GREEN)
                .setDescription(user.getAsTag() + " has been banned.")
                .addField("Reason", reason, false)
                .setTimestamp(Instant.now());
    }

    public EmbedBuilder createBanFailureEmbed() {
        return new EmbedBuilder()
                .setTitle("Ban Failed")
                .setColor(Color.RED)
                .setDescription("Failed to ban the member.")
                .setTimestamp(Instant.now());
    }
}