package isapp.client;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeycloakClientConfig {

    public final String keycloakUrl;
    public final String keycloakRealm;
    public final String keycloakUser;
    public final String keycloakPassword;
    public final String keycloakClientId;

    public KeycloakClientConfig(@Value("${keycloak.url}") String keycloakUrl,
                                @Value("${keycloak.realm}") String keycloakRealm,
                                @Value("${keycloak.user}") String keycloakUser,
                                @Value("${keycloak.password}") String keycloakPassword,
                                @Value("${keycloak.client-id}") String keycloakClientId) {
        this.keycloakUrl = keycloakUrl;
        this.keycloakRealm = keycloakRealm;
        this.keycloakUser = keycloakUser;
        this.keycloakPassword = keycloakPassword;
        this.keycloakClientId = keycloakClientId;
    }

    public Keycloak getKeycloakClient() {
        return KeycloakBuilder
                .builder()
                .serverUrl(keycloakUrl)
                .realm(keycloakRealm)
                .username(keycloakUser)
                .password(keycloakPassword)
                .clientId(keycloakClientId)
                .build();
    }

    public String getKeycloakClientId() {
        return this.keycloakClientId;
    }

    public String getKeycloakRealm() {
        return this.keycloakRealm;
    }
}

