package africa.semoicolon.service.inferaces;

import africa.semoicolon.data.model.User;
import africa.semoicolon.dtos.request.DeleteUserRequest;
import africa.semoicolon.dtos.request.LogOut;
import africa.semoicolon.dtos.request.LoginRequest;
import africa.semoicolon.dtos.request.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void save(User user);
    void register(RegisterRequest request);
    long countAllUsers();
    void deleteUser(DeleteUserRequest delete);
    void deleteAll();
    void login(LoginRequest login);
    void isValidUsername(String username);
    User getUser(LoginRequest login);
    User getUser(String username);
    void logOut(LogOut logout);
    void validateUserLogin(LoginRequest login);
}
