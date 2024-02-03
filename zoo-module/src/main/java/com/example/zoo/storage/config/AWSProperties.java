package com.example.zoo.storage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("aws-s3")
public class AWSProperties {
    private String zooServiceBucketName;
    private String zooServiceAnimalStreamLoadBucketName;
}
