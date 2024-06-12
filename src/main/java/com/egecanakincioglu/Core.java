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
            JDA auth = JDABuilder.createDefault(TOKEN)
                    .enableIntents(Arrays.asList(GatewayIntent.values()))
                    .useSharding(0, 2)
                    .build();

            CommandHandler commandHandler = new CommandHandler();
            EventHandler eventHandler = new EventHandler(commandHandler);
            eventHandler.loadEvents();

            commandHandler.registerCommands(auth);
            auth.awaitReady();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
