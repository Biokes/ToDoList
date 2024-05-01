package africa.semoicolon.service.inferaces;

import africa.semoicolon.dtos.request.DeleteUserRequest;
import africa.semoicolon.dtos.request.LoginRequest;
import africa.semoicolon.dtos.request.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void register(RegisterRequest request);
    long countAllUsers();
    void deleteUser(DeleteUserRequest delete);
    void deleteAll();
    void login(LoginRequest login);
}
