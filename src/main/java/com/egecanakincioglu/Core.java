package com.egecanakincioglu;

import com.egecanakincioglu.utils.logger.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.internal.utils.JDALogger;

import javax.security.auth.login.LoginException;

public final class Core extends LoginException {
    public static void main(String[] arguments) {
        final String TOKEN = "";

        JDALogger.setFallbackLoggerEnabled(false);

        try {
            JDA auth = JDABuilder.createDefault(TOKEN)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                    .useSharding(0, 2)
                    .build();

            auth.awaitReady();
            Logger.info("Bot Hazır!");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
