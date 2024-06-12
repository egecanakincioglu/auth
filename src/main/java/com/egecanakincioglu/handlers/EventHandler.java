package com.egecanakincioglu.handlers;

import com.egecanakincioglu.handlers.builders.EventBuilder;
import com.egecanakincioglu.handlers.listeners.InteractionListener;
import com.egecanakincioglu.utils.logger.Logger;
import org.reflections.Reflections;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class EventHandler {

    private final CommandHandler commandHandler;

    public EventHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public void loadEvents() {
        Reflections reflections = new Reflections("com.egecanakincioglu.events");
        Set<Class<? extends EventBuilder>> eventClasses = reflections.getSubTypesOf(EventBuilder.class);

        for (Class<? extends EventBuilder> eventClass : eventClasses) {
            try {
                EventBuilder event = eventClass.getDeclaredConstructor().newInstance();
                event.execute(null);
                Logger.info("Loaded event: " + eventClass.getSimpleName());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                Logger.error("Failed to load event: " + eventClass.getSimpleName());
                e.printStackTrace();
            }
        }
        new InteractionListener(commandHandler);
    }
}
