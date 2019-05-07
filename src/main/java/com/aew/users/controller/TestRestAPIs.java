package com.aew.users.controller;

import com.aew.users.messages.response.UserSummary;
import com.aew.users.security.CurrentUser;
import com.aew.users.security.UserPrinciple;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/test")
public class TestRestAPIs {

  @GetMapping("user")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public String userAccess() {
    return ">>> User Contents!";
  }

  @GetMapping("mod")
  @PreAuthorize("hasRole('MOD') or hasRole('ADMIN')")
  public String projectManagementAccess() {
    return ">>> Board Management Project";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String adminAccess() {
    return ">>> Admin Contents";
  }

  /**
   * Endpoint to fetch current user info V1.
   */
  @GetMapping("/user/me")
  @PreAuthorize("hasRole('USER')")
  public UserSummary getCurrentUser(@CurrentUser UserPrinciple currentUser) {

    UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
    return userSummary;
  }

  /**
   * Endpoint to fetch current user info V2.
   */
  @GetMapping("/user/me2")
  @PreAuthorize("hasRole('USER')")
  public UserPrinciple getCurrentUser2(@AuthenticationPrincipal UserPrinciple currentUser) {
    return currentUser;
  }

}