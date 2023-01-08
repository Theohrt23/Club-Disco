package com.fr.clubdisco.model;

public enum WidgetsName {

    SPOTIFY("SPOTIFY"), ZIPPOPOTAM("ZIPPOPOTAM"), NASA("NASA"), WEATHER("WEATHER");

    private String widgetName;

    public String getWidgetName() {
        return widgetName;
    }

    WidgetsName(final String widgetName) {
        this.widgetName = widgetName;
    }
}
