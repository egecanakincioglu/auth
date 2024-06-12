package com.egecanakincioglu;

import com.egecanakincioglu.handlers.CommandHandler;
import com.egecanakincioglu.handlers.EventHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.internal.utils.JDALogger;

import javax.security.auth.login.LoginException;
import java.util.Arrays;

public final class Core extends LoginException {
    public static void main(String[] arguments) {
        final String TOKEN = "";

        JDALogger.setFallbackLoggerEnabled(false);

        try {
            JDABuilder auth = JDABuilder.createDefault(TOKEN)
                    .enableIntents(Arrays.asList(GatewayIntent.values()));

            CommandHandler commandHandler = new CommandHandler();
            EventHandler eventHandler = new EventHandler();
            eventHandler.loadEvents(auth);

            JDA bot = auth.build();

            commandHandler.registerCommands(bot);
            bot.awaitReady();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
