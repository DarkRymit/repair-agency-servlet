package com.epam.finalproject.framework.web;

import com.epam.finalproject.framework.beans.annotation.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

@Component
public class FlashAttributesManager {

    private static final String FLASH_MAP_SESSION_ATTRIBUTE = FlashAttributesManager.class.getName() + ".FLASH_MAPS";

    protected Map<String,Object> retrieveFlashMap(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null ? (Map<String, Object>) session.getAttribute(FLASH_MAP_SESSION_ATTRIBUTE) : null;
    }
    public Map<String,Object> retrieveAndCleanFlashMap(HttpServletRequest request) {
        Map<String,Object> flashMap = retrieveFlashMap(request);
        request.getSession().setAttribute(FLASH_MAP_SESSION_ATTRIBUTE, null);
        return flashMap;
    }

    public void updateFlashMap(Map<String,Object> flashMap, HttpServletRequest request) {
        request.getSession().setAttribute(FLASH_MAP_SESSION_ATTRIBUTE, !flashMap.isEmpty() ? flashMap : null);
    }

}
