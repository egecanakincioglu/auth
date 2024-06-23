package com.egecanakincioglu.commands.moderation;

import com.egecanakincioglu.handlers.builders.CommandBuilder;
import net.dv8tion.jda.api.JDA;
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

            CompletableFuture<Long> restPingFuture = jda.getRestPing().submit();

            restPingFuture.thenAccept(restPing -> {
                long gatewayPing = jda.getGatewayPing();

                String response = String.format(
                        "Message Latency: `%d ms`\n" +
                                "WebSocket Latency: `%d ms`\n" +
                                "API Latency: `%d ms`",
                        messageLatency, gatewayPing, restPing
                );

                interactionHook.editOriginal(response).queue();
            }).exceptionally(ex -> {
                interactionHook.editOriginal("Failed to calculate API latency: " + ex.getMessage()).queue();
                return null;
            });
        });
    }
}
