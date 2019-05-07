package com.aew.users.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class UserController {

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() {
        return "BIENVENIDO";
    }

    @Secured("ADMIN")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUsers() {
        return "Estas listando todos los users";
    }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String getUser(@PathVariable("id") long id) {
        return "Estas viendo el usuario " + id;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable("id") long id) {
        return "Estas borrando el usuario " + id;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String addUser() {
        return "Estas creando un nuevo user";
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String userInfo(Authentication authentication) {
        if (authentication == null) {
            return "Nadie esta logueado";
        } else
            return "estas logueado como " + authentication.getName();
    }

}