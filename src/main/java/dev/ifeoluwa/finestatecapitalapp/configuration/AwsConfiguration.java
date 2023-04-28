package dev.ifeoluwa.finestatecapitalapp.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author on 24/04/2023
 * @project
 */

@Configuration
public class AwsConfiguration {

    private String accessKey = "AKIA5WDCPJZICUXP2QNM";
    private String secretKey = "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";


    @Bean
    public AWSCognitoIdentityProvider cognitoClient() {
        AWSCognitoIdentityProvider cognitoClient = AWSCognitoIdentityProviderClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .withRegion(Regions.US_EAST_1)
                .build();
        return cognitoClient;
    }


    @Bean
    public AmazonS3 s3Client() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
        return s3Client;
    }


}
