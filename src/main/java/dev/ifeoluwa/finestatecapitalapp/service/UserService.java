package dev.ifeoluwa.finestatecapitalapp.service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import dev.ifeoluwa.finestatecapitalapp.dto.LoginDTO;
import dev.ifeoluwa.finestatecapitalapp.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author on 24/04/2023
 * @project
 */
@Service
@Slf4j
public class UserService {

    @Value("${aws.cognito.user-pool-id}")
    private String userPoolId;

    @Value("${aws.cognito.client-id}")
    private String clientId;

//    @Value("${aws.cognito.client-secret}")
    private String clientSecret = "1kb17u5l3m6v0pshd8a6g9ji25oflpdtt2lt0dp1h7iq3oe7eo75";

    @Autowired
    private AWSCognitoIdentityProvider cognitoClient;

    @Autowired
    private ImageService imageService;



    public ResponseEntity<?> signUp(String name, String email, String phoneNumber,
                                    String password, MultipartFile picture) throws IOException {
        String key = imageService.storeImage(picture.getOriginalFilename(), picture.getBytes());
        log.info("client id: ", clientId);
        try{
            AdminCreateUserRequest createUserRequest = new AdminCreateUserRequest()
                    .withUserPoolId(userPoolId)
                    .withUsername(email)
                    .withTemporaryPassword("TempPass@123!")
                    .withDesiredDeliveryMediums(DeliveryMediumType.EMAIL)
                    .withUserAttributes(
                            new AttributeType().withName("given_name").withValue(name),
                            new AttributeType().withName("phone_number").withValue(phoneNumber),
                            new AttributeType().withName("picture").withValue(key)
                    );

            AdminCreateUserResult createUserResult = cognitoClient.adminCreateUser(createUserRequest);

            log.info("new user created: ", createUserResult);

            AdminSetUserPasswordRequest setPasswordRequest = new AdminSetUserPasswordRequest()
                    .withUserPoolId(userPoolId)
                    .withUsername(email)
                    .withPassword(password)
                    .withPermanent(true);

            AdminSetUserPasswordResult setPasswordResult = cognitoClient.adminSetUserPassword(setPasswordRequest);

            log.info("password reset triggered: ", setPasswordResult);

            return new ResponseEntity<>(createUserResult, HttpStatus.CREATED);

        } catch (Exception e) {
            log.error("Error signing up user: {}", e.getMessage(), e);
            throw new RuntimeException("Error signing up user", e);
        }

    }



    public ResponseEntity<?> login(LoginDTO loginDTO) {

        try{
            Map<String, String> authParams = new HashMap<>();
            authParams.put("USERNAME", loginDTO.getEmail());
            authParams.put("PASSWORD", loginDTO.getPassword());
            AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest()
                    .withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                    .withClientId(clientId)
                    .withUserPoolId(userPoolId)
                    .withAuthParameters(authParams);

            AdminInitiateAuthResult authResult = cognitoClient.adminInitiateAuth(authRequest);

            log.info("user authenticated: ", authResult);

            return new ResponseEntity<>(authResult, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Error logging in user: {}", e.getMessage(), e);
            throw new RuntimeException("Error logging in user", e);
        }
    }


    public ResponseEntity<?> logout(String email) {
        try {
            AdminUserGlobalSignOutRequest signOutRequest = new AdminUserGlobalSignOutRequest()
                    .withUserPoolId(userPoolId)
                    .withUsername(email);

            cognitoClient.adminUserGlobalSignOut(signOutRequest);

            return new ResponseEntity<>("User logged out successfully", HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error logging out user: {}", e.getMessage(), e);
            throw new RuntimeException("Error logging out user", e);
        }
    }




}
