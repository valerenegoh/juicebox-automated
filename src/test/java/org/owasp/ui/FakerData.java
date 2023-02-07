package org.owasp.ui;

import com.github.javafaker.Faker;
import lombok.Getter;

@Getter
public class FakerData {
    private static Faker faker;
    private String email;
    private String password;
    private String country;
    private String name;
    private String mobileNumber;
    private String zipCode;
    private String address;
    private String city;
    private String state;
    public FakerData() {
        faker = new Faker();
        email = faker.internet().emailAddress();
        password = faker.internet().password(6, 12);
        country = faker.address().country();
        name = faker.name().fullName();
        mobileNumber = faker.phoneNumber().toString();
        zipCode = faker.address().zipCode();
        address = faker.address().streetAddress();
        city = faker.address().city();
        state = faker.address().state();
    }
}
