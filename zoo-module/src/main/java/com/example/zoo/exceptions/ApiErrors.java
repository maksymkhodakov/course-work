package com.example.zoo.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiErrors {

    // mariadb
    ZOO_NOT_FOUND("Zoo with provided id was not found!"),
    COUNTRY_NOT_FOUND("Country with provided id was not found!"),
    ANIMAL_NOT_FOUND("Animal with provided id was not found!"),

    // Security
    USER_NOT_FOUND("User not found"),
    NO_EMAIL("No email was extracted from OAuth2 provider"),
    USER_EXISTS("User exists by this email"),

    // AWS S3
    RESOURCE_NOT_FOUND("Resource not found on S3 bucket"),
    FAILED_S3_UPLOAD("Failed S3 upload"),
    EMPTY_FILE("Empty file"),
    ANIMAL_LOAD_NOT_FOUND("Animal load result not found");

    private final String message;
}
