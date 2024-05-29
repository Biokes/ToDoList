package africa.semicolon.dto.storeRequest;

import lombok.Data;

@Data
public class RegisterCustomer {
    private String email;
    private String username;
    private String password;
}
