package com.egecanakincioglu.events;

import javax.annotation.Nonnull;

import com.egecanakincioglu.utils.logger.LogFactory;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Ready extends ListenerAdapter {
    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        LogFactory.info("Bot is ready!");
    }
}
