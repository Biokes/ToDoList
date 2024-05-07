package semicolon.inferaces;

import semicolon.data.model.User;
import semicolon.dto.request.DeleteUserRequest;
import semicolon.dto.request.LogOut;
import semicolon.dto.request.LoginRequest;
import semicolon.dto.request.RegisterRequest;
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
