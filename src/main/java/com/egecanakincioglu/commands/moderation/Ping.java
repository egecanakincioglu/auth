package com.egecanakincioglu.commands.moderation;

import com.egecanakincioglu.handlers.builders.CommandBuilder;
import com.egecanakincioglu.interfaces.moderation.PingInterface;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.concurrent.CompletableFuture;

public class Ping extends CommandBuilder {
    public Ping() {
        super("ping", "Returns various latency metrics");
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        long start = System.currentTimeMillis();
        event.reply("Calculating latency...").queue(interactionHook -> {
            long end = System.currentTimeMillis();
            long messageLatency = end - start;

            JDA jda = event.getJDA();
            SelfUser botUser = jda.getSelfUser();
            Member member = event.getMember(); // Kullanıcıyı sunucudaki üye olarak alıyoruz

            if (member == null) {
                interactionHook.editOriginal("Failed to retrieve member information.").queue();
                return;
            }

            CompletableFuture<Long> restPingFuture = jda.getRestPing().submit();

            restPingFuture.thenAccept(restPing -> {
                long gatewayPing = jda.getGatewayPing();

                PingInterface pingInterface = new PingInterface();
                interactionHook.editOriginal("Latency metrics calculated")
                        .setEmbeds(pingInterface.createLatencyEmbed(messageLatency, gatewayPing, restPing, botUser, member).build())
                        .queue();
            }).exceptionally(ex -> {
                PingInterface pingInterface = new PingInterface();
                interactionHook.editOriginal("Latency metrics calculated")
                        .setEmbeds(pingInterface.createErrorEmbed(ex.getMessage(), botUser, member).build())
                        .queue();
                return null;
            });
        });
    }
}