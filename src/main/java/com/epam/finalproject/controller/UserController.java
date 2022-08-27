package com.epam.finalproject.controller;

import com.epam.finalproject.dto.RoleDTO;
import com.epam.finalproject.dto.UserDTO;
import com.epam.finalproject.framework.security.AccessDeniedException;
import com.epam.finalproject.framework.security.UserDetails;
import com.epam.finalproject.framework.security.annotation.PreAuthorize;
import com.epam.finalproject.framework.security.util.SecurityUtils;
import com.epam.finalproject.framework.web.annotation.Controller;
import com.epam.finalproject.framework.web.annotation.GetMapping;
import com.epam.finalproject.framework.web.annotation.PathVariable;
import com.epam.finalproject.framework.web.annotation.RequestMapping;
import com.epam.finalproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(UserController.class);
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}/profile")
    @PreAuthorize("isAuthenticated()")
    String profilePage(HttpServletRequest request, UserDetails userDetails, @PathVariable("id") Long id) {
        UserDTO user = userService.findById(id);
        log.trace("User {}", user);
        if (SecurityUtils.hasRole(userDetails, "CUSTOMER") && isCustomer(user) && !user.getUsername()
                .equals(userDetails.getUsername())) {
            throw new AccessDeniedException(String.format("Not permit another user page %s", userDetails));
        }
        request.setAttribute("user", user);
        return "user";
    }

    private boolean isCustomer(UserDTO user) {
        return user.getRoles().stream().map(RoleDTO::getName).anyMatch(s -> s.equals("CUSTOMER"));
    }

}
