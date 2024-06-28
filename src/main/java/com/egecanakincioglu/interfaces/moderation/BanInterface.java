package com.egecanakincioglu.interfaces.moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Guild;

import java.awt.Color;
import java.time.Instant;

public class BanInterface {

    public EmbedBuilder createBanNotificationEmbed(Member member, String reason, Guild guild) {
        return new EmbedBuilder()
                .setTitle("🚫 You have been banned")
                .setColor(Color.RED)
                .setDescription("You have been banned from " + guild.getName())
                .addField("Reason", reason, false)
                .addField("Banned By", member.getUser().getAsTag() + " (" + member.getId() + ")", false)
                .setThumbnail(guild.getIconUrl())
                .setTimestamp(Instant.now())
                .setFooter("Banned by " + member.getUser().getAsTag(), member.getUser().getAvatarUrl());
    }

    public EmbedBuilder createBanSuccessEmbed(User bannedUser, User banningUser, String reason) {
        return new EmbedBuilder()
                .setTitle("✅ Member Banned")
                .setColor(Color.GREEN)
                .setDescription(bannedUser.getAsTag() + " has been banned.")
                .addField("Banned User", bannedUser.getAsTag() + " (" + bannedUser.getId() + ")", false)
                .addField("Reason", reason, false)
                .addField("Banned By", banningUser.getAsTag() + " (" + banningUser.getId() + ")", false)
                .setThumbnail(bannedUser.getAvatarUrl())
                .setTimestamp(Instant.now())
                .setFooter("Command executed by " + banningUser.getAsTag(), banningUser.getAvatarUrl());
    }

    public EmbedBuilder createBanFailureEmbed() {
        return new EmbedBuilder()
                .setTitle("❌ Ban Failed")
                .setColor(Color.RED)
                .setDescription("Failed to ban the member.")
                .setTimestamp(Instant.now());
    }
}