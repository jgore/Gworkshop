package pl.goreit.blog.api;

import org.keycloak.adapters.OidcKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class KeycloakLogoutHandler extends SecurityContextLogoutHandler {

    Logger logger = LoggerFactory.getLogger(KeycloakLogoutHandler.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        super.logout(request, response, authentication);

        KeycloakAuthenticationToken keycloakauthentication = (KeycloakAuthenticationToken) authentication;

        propagateLogoutToKeycloak((keycloakauthentication.getAccount()));
    }

    private void propagateLogoutToKeycloak(OidcKeycloakAccount user) {

        String endSessionEndpoint = "localhost:8080+/protocol/openid-connect/logout";

        UriComponentsBuilder builder = UriComponentsBuilder //
                .fromUriString(endSessionEndpoint) //
                .queryParam("id_token_hint", user.getKeycloakSecurityContext().getIdToken());

        ResponseEntity<String> logoutResponse = restTemplate.getForEntity(builder.toUriString(), String.class);
        if (logoutResponse.getStatusCode().is2xxSuccessful()) {
            logger.info("Successfulley logged out in Keycloak");
        } else {
            logger.info("Could not propagate logout to Keycloak");
        }
    }
}