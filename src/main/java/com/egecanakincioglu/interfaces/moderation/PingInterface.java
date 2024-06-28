package com.egecanakincioglu.interfaces.moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.SelfUser;

import java.awt.Color;
import java.time.Instant;

public class PingInterface {

    public EmbedBuilder createLatencyEmbed(long messageLatency, long gatewayPing, long restPing, SelfUser botUser, Member member) {
        String memberAvatarUrl = member.getEffectiveAvatarUrl();
        String botAvatarUrl = botUser.getAvatarUrl();

        return new EmbedBuilder()
                .setTitle("📊 Latency Metrics")
                .setColor(Color.BLUE)
                .addField("📩 Message Latency", String.format("`%d ms`", messageLatency), false)
                .addField("🌐 WebSocket Latency", String.format("`%d ms`", gatewayPing), false)
                .addField("⚙️ API Latency", String.format("`%d ms`", restPing), false)
                .setTimestamp(Instant.now())
                .setFooter("Requested by " + member.getEffectiveName(), memberAvatarUrl)
                .setThumbnail(botAvatarUrl);
    }

    public EmbedBuilder createErrorEmbed(String errorMessage, SelfUser botUser, Member member) {
        String memberAvatarUrl = member.getEffectiveAvatarUrl();
        String botAvatarUrl = botUser.getAvatarUrl();

        return new EmbedBuilder()
                .setTitle("❌ Error")
                .setColor(Color.RED)
                .setDescription("Failed to calculate API latency: " + errorMessage)
                .setTimestamp(Instant.now())
                .setFooter("Requested by " + member.getEffectiveName(), memberAvatarUrl)
                .setThumbnail(botAvatarUrl);
    }
}