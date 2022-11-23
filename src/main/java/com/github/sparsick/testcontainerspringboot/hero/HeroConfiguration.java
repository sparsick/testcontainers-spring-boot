package com.github.sparsick.testcontainerspringboot.hero;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@EnableConfigurationProperties(HeroConfiguration.AwsS3ConfigurationProperties.class)
public class HeroConfiguration {


    @Bean
    S3Client awsS3Client(AwsS3ConfigurationProperties awsS3config) {
        return S3Client.builder()
                .endpointOverride(awsS3config.getEndpoint())
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(awsS3config.getAccessKey(), awsS3config.getSecretKey())
                        )
                )
                .region(Region.of(awsS3config.getRegion()))
                .build();

    }


    @ConfigurationProperties(prefix="aws.s3")
    public static class AwsS3ConfigurationProperties {

        private URI endpoint;
        private String accessKey;
        private String secretKey;
        private String region;

        public URI getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(URI endpoint) {
            this.endpoint = endpoint;
        }

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }
    }
}
