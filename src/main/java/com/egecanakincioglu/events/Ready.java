package com.egecanakincioglu.events;

import com.egecanakincioglu.handlers.builders.EventBuilder;
import com.egecanakincioglu.utils.logger.Logger;

public class Ready extends EventBuilder {
    @Override
    public void execute(Object event) {
        Logger.info("Bot is ready!");
    }
}
