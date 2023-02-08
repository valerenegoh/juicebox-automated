package org.owasp.api.datamodels;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterCustomerData {
    private String email;
    private String password;
    private String passwordRepeat;
    private SecurityQuestion securityQuestion;
    private String securityAnswer;
}
