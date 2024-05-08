package africa.semicolon.inferaces;

import africa.semicolon.data.model.User;
import africa.semicolon.dto.request.DeleteUserRequest;
import africa.semicolon.dto.request.LogOut;
import africa.semicolon.dto.request.LoginRequest;
import africa.semicolon.dto.request.RegisterRequest;
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
