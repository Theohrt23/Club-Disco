package com.fr.clubdisco.service;

import com.fr.clubdisco.model.Widget;

import java.util.List;

public interface WidgetService {

    Widget addWidgetToUsers(Long userId, Long widgetId);

    void removeWidgetToUser(Long userId, Long widgetId);

    List<Widget> getAllWidgets();

    List<Widget> getAllWidgetOfUser(Long userId);

    boolean userHaveSpotifyWidget(Long userId);

    boolean userHaveWidgetId(Long userId, Long widgetId);

}
