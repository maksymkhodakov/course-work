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

    // Azure Storage
    DOWNLOAD_FILE_FAILED("Error occurred during file downloading from the Azure Storage."),
    UPLOAD_FILE_ERROR("Error occurred during file upload to the Azure Storage"),

    // Security
    USER_NOT_FOUND("User not found"),
    NO_EMAIL("No email was extracted from OAuth2 provider"),
    USER_EXISTS("User exists by this email");

    private final String message;
}
