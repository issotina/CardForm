package com.craftman.cardform.time;

import java.util.Calendar;

public class Clock {
    private static Clock instance;
    protected Calendar calendarInstance;

    protected static Clock getInstance() {
        if (instance == null) {
            instance = new Clock();
        }
        return instance;
    }

    public static Calendar getCalendarInstance() {
        return getInstance()._calendarInstance();
    }

    private Calendar _calendarInstance() {
        return calendarInstance != null ? (Calendar) calendarInstance.clone() : Calendar.getInstance();
    }
}
