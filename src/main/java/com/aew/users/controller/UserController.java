package com.aew.users.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping(value = "/api/v1")
public class UserController {


@RequestMapping(value="/users", method=RequestMethod.GET)
public String getUsers() {
    return "Estas listando todos los users";
}

@RequestMapping(value="/user/{id}", method=RequestMethod.GET)
public String getUser(@PathVariable("id") long id) {
    return "Estas viendo el usuario " + id;
}

@RequestMapping(value="/user/{id}", method=RequestMethod.DELETE)
public String deleteUser(@PathVariable("id") long id) {
    return "Estas borrando el usuario " + id;
}

@RequestMapping(value="/user", method=RequestMethod.POST)
public String addUser() {
    return "Estas creando un nuevo user";
}

@RequestMapping(value="/logout", method = RequestMethod.GET)
public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null){    
        new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return "Te has deslogueado";
}


}