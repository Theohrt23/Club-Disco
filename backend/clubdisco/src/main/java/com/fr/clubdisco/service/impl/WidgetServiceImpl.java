package com.fr.clubdisco.service.impl;

import com.fr.clubdisco.model.User;
import com.fr.clubdisco.model.Widget;
import com.fr.clubdisco.model.WidgetsName;
import com.fr.clubdisco.repo.UserRepository;
import com.fr.clubdisco.repo.WidgetRepository;
import com.fr.clubdisco.service.WidgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class WidgetServiceImpl implements WidgetService {

    private final UserRepository userRepository;

    private final WidgetRepository widgetRepository;

    @Override
    public Widget addWidgetToUsers(Long userId, Long widgetId) {
        Widget widget = widgetRepository.findWidgetById(widgetId);
        User user = userRepository.findById(userId).get();
        widget.getUsers().add(user);
        user.getWidgets().add(widget);
        userRepository.save(user);
        widgetRepository.save(widget);
        return widget;
    }

    @Override
    public void removeWidgetToUser(Long userId, Long widgetId) {
        Widget widget = widgetRepository.findWidgetById(widgetId);
        User user = userRepository.findById(userId).get();
        widget.getUsers().remove(user);
        user.getWidgets().remove(widget);
        userRepository.save(user);
        widgetRepository.save(widget);
    }

    @Override
    public List<Widget> getAllWidgets() {
        return widgetRepository.findAll();
    }

    @Override
    public List<Widget> getAllWidgetOfUser(Long userId) {
        List<Widget> widgetList = new ArrayList<>(userRepository.findById(userId).get().getWidgets());
        return widgetList;
    }

    @Override
    public boolean userHaveSpotifyWidget(Long userId) {
        for (Widget w : userRepository.findById(userId).get().getWidgets()){
            if (w.getName().equals(WidgetsName.SPOTIFY.getWidgetName())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean userHaveWidgetId(Long userId, Long widgetId) {
        for (Widget w : userRepository.findById(userId).get().getWidgets()){
            if (w.getName().equals(widgetRepository.findWidgetById(widgetId).getName())){
                return true;
            }
        }
        return false;
    }


}
