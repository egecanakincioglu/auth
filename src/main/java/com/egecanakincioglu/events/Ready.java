package com.egecanakincioglu.events;

import javax.annotation.Nonnull;

import com.egecanakincioglu.utils.system.Logger;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ready extends ListenerAdapter {
    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        Logger.info("Bot is ready!");
    }
}
