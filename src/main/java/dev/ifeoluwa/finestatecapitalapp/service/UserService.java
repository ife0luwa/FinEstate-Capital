package dev.ifeoluwa.finestatecapitalapp.service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import dev.ifeoluwa.finestatecapitalapp.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

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



    public ResponseEntity<?> signUp(UserDTO userDTO) {

        log.info("client id: ", clientId);
        try{
            AdminCreateUserRequest createUserRequest = new AdminCreateUserRequest()
                    .withUserPoolId(userPoolId)
                    .withUsername(userDTO.getEmail())
                    .withTemporaryPassword("TempPass@123!")
                    .withDesiredDeliveryMediums(DeliveryMediumType.EMAIL)
                    .withUserAttributes(
                            new AttributeType().withName("given_name").withValue(userDTO.getName()),
                            new AttributeType().withName("phone_number").withValue(userDTO.getPhoneNumber()),
                            new AttributeType().withName("picture").withValue(userDTO.getPicture())
                    );

            AdminCreateUserResult createUserResult = cognitoClient.adminCreateUser(createUserRequest);

            log.info("new user created: ", createUserResult);

            AdminSetUserPasswordRequest setPasswordRequest = new AdminSetUserPasswordRequest()
                    .withUserPoolId(userPoolId)
                    .withUsername(userDTO.getEmail())
                    .withPassword(userDTO.getPassword())
                    .withPermanent(true);

            AdminSetUserPasswordResult setPasswordResult = cognitoClient.adminSetUserPassword(setPasswordRequest);

            log.info("password reset triggered: ", setPasswordResult);

            return new ResponseEntity<>(createUserResult, HttpStatus.CREATED);

        } catch (Exception e) {
            log.error("Error signing up user: {}", e.getMessage(), e);
            throw new RuntimeException("Error signing up user", e);
        }

    }





}
