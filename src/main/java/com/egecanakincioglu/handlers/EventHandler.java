package com.egecanakincioglu.handlers;

import com.egecanakincioglu.utils.system.Logger;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Set;

public class EventHandler {

    public void loadEvents(JDABuilder bot) {
        Reflections reflections = new Reflections("com.egecanakincioglu.events");
        Set<Class<? extends ListenerAdapter>> eventClasses = reflections.getSubTypesOf(ListenerAdapter.class);
        ArrayList<ListenerAdapter> events = new ArrayList<>();

        for (Class<? extends ListenerAdapter> eventClass : eventClasses) {
            try {
                ListenerAdapter event = eventClass.getDeclaredConstructor().newInstance();
                events.add(event);
                Logger.info("Loaded event: " + eventClass.getSimpleName());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException
                    | NoSuchMethodException e) {
                Logger.error("Failed to load event: " + eventClass.getSimpleName());
                e.printStackTrace();
            }
        }

        ListenerAdapter[] listenersArray = events.toArray(new ListenerAdapter[0]);
        bot.addEventListeners((Object[]) listenersArray);
    }
}
