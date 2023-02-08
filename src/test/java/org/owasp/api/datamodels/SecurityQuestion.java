package org.owasp.api.datamodels;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SecurityQuestion {
        private int id;
        private String question;
        private String createdAt;
        private String updatedAt;
}
