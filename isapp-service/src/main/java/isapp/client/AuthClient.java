package isapp.client;

import isapp.model.user.UserKeycloak;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.Collections;

@Component
public class AuthClient {

    public final String registrationSuccessUrl;
    public final KeycloakClientConfig keycloakClientConfig;
    public final Keycloak keycloakClient;

    public AuthClient(KeycloakClientConfig keycloakClientConfig,
                      @Value("${ps-ui.registration-success.url}") String registrationSuccessUrl) {
        this.registrationSuccessUrl = registrationSuccessUrl;
        this.keycloakClientConfig = keycloakClientConfig;
        this.keycloakClient = keycloakClientConfig.getKeycloakClient();
    }

    public Response registerUser(UserKeycloak userRegistrationRequest) throws Exception {

        UserRepresentation user = createUserInfo(userRegistrationRequest);

        try {
            RealmResource realmResource = keycloakClient.realm(keycloakClientConfig.getKeycloakRealm());
            UsersResource usersResource = realmResource.users();
            return usersResource.create(user);

        } catch (Exception e) {

            throw new Exception(e);
        }
    }

    public UserRepresentation createUserInfo(UserKeycloak userRegistrationRequest){

        UserRepresentation user = new UserRepresentation();

        CredentialRepresentation credential = createPasswordCredentials(userRegistrationRequest.getPassword());

        user.setEmail(userRegistrationRequest.getEmail());
        user.setCredentials(Collections.singletonList(credential));
        user.setUsername(userRegistrationRequest.getEmail());
        user.setEnabled(true);

        return user;
    }

    public CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

}
