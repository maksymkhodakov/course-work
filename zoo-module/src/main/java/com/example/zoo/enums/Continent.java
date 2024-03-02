package com.example.zoo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum Continent {
    Africa("Africa"),
    Australia("Australia"),
    NorthAmerica("North America"),
    SouthAmerica("South America"),
    Antarctica("Antarctica"),
    Eurasia("Europe");

    private final String name;
}
