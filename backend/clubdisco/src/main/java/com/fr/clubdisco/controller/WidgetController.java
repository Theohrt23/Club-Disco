package com.fr.clubdisco.controller;

import com.fr.clubdisco.dto.WidgetDTO;
import com.fr.clubdisco.model.Widget;
import com.fr.clubdisco.service.WidgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/widget")
public class WidgetController {

    private final WidgetService widgetService;

    @GetMapping("/{userId}/{widgetId}")
    @PreAuthorize("hasRole('USER')")
    public WidgetDTO addWidgetToUser(@PathVariable(value = "userId") Long userId, @PathVariable(value = "widgetId") Long widgetId) {
        Widget widget = widgetService.addWidgetToUsers(userId,widgetId);
        return new WidgetDTO(widget.getId(),widget.getName());
    }

    @DeleteMapping("/{userId}/{widgetId}")
    @PreAuthorize("hasRole('USER')")
    public void removeWidgetToUser(@PathVariable(value = "userId") Long userId, @PathVariable(value = "widgetId") Long widgetId) {
        widgetService.removeWidgetToUser(userId,widgetId);
    }

    @GetMapping()
    @PreAuthorize("hasRole('USER')")
    public List<WidgetDTO> getAllWidgets() {
        List<WidgetDTO> widgetDTOList = new ArrayList<>();
        for (Widget w : widgetService.getAllWidgets()) {
            widgetDTOList.add(new WidgetDTO(w.getId(), w.getName()));
        }
        return widgetDTOList;
    }


    @GetMapping("/currentWidgets/{userId}")
    @PreAuthorize("hasRole('USER')")
    public List<WidgetDTO> getCurrentUserWidgets(@PathVariable(value = "userId") Long userId){
        List<WidgetDTO> widgetDTOList = new ArrayList<>();
        for (Widget w : widgetService.getAllWidgetOfUser(userId)){
            widgetDTOList.add(new WidgetDTO(w.getId(),w.getName()));
        }
        return widgetDTOList;
    }

    @GetMapping("/haveSpotify/{userId}")
    @PreAuthorize("hasRole('USER')")
    public boolean haveSpotify(@PathVariable(value = "userId") Long userId){
        return widgetService.userHaveSpotifyWidget(userId);
    }

}
