package com.davydovskyi.spotify.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.davydovskyi.spotify.domain.LoginResponseDTO;
import com.davydovskyi.spotify.services.SpotifyAuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AuthorizationController {

  @Autowired
  private SpotifyAuthService spotifyAuthService;

  private List<String> stateList = new ArrayList<String>();

  @GetMapping("/login")
  public @ResponseBody ResponseEntity<LoginResponseDTO> login() {
    var state = generateState(); // Generate a random state value
    var scope = "user-read-private user-read-email"; // Specify the required scopes

    var authUri = spotifyAuthService.buildAuthorizeUrl(state, scope);
    log.info("Auth URL: {}", authUri);
    return ResponseEntity.ok(authUri);
  }

  @GetMapping("/callback")
  public String callback(@RequestParam("code") String code,
      @RequestParam("state") String state) {
    log.info("Received callback with code: {} and state: {}", code, state);
    if (isValidState(state)) {
      // Exchange the authorization code for an access token
      spotifyAuthService.exchangeCodeForToken(code);
      log.info("Auth success");
      return "redirect:/success"; // Redirect to a success page or perform further actions
    } else {
      log.info("Auth Failed");
      return "redirect:/error"; // Redirect to an error page if the state is invalid
    }
  }

  // Utility method to validate the received state
  private boolean isValidState(String state) {
    if (stateList.contains(state)) {
      stateList.remove(state);
      return true;
    }
    return false;
  }

  private String generateState() {
    var state = UUID.randomUUID().toString();
    log.info("Adding state: {}", state);
    stateList.add(state);
    return state;
  }
}
