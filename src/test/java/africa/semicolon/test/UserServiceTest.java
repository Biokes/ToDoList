package africa.semicolon.test;


import africa.semicolon.ToDoList;
import africa.semicolon.dto.request.*;
import africa.semicolon.exceptions.InvalidDetails;
import africa.semicolon.exceptions.ToDoListException;
import africa.semicolon.inferaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @BeforeEach
    void wipe(){
        userService.deleteAll();
    }
@Test
public void register_testUserIsregistered(){
        RegisterRequest request = new RegisterRequest();
        request.setUsername("");
        request.setPassword("");
        assertThrows(InvalidDetails.class,()-> userService.register(request));
        request.setUsername("username");
        request.setPassword("password");
        userService.register(request);
        assertEquals(1, userService.countAllUsers());
        assertThrows(ToDoListException.class,()->userService.register(request));
    }
    @Test
    public void deActivateAccount_testAccountIsDeactivated(){
        DeleteUserRequest delete = new DeleteUserRequest();
        delete.setUsername("");
        delete.setPassword("");
        assertThrows(InvalidDetails.class,()->userService.deleteUser(delete));
        delete.setUsername("username");
        delete.setPassword("password");
        assertThrows(ToDoListException.class,()->userService.deleteUser(delete));
        RegisterRequest request = new RegisterRequest();
        request.setUsername("username");
        request.setPassword("password1");
        userService.register(request);
        assertEquals(1, userService.countAllUsers());
        delete.setPassword("password");
        assertThrows(ToDoListException.class,()->userService.deleteUser(delete));
        assertEquals(1, userService.countAllUsers());
        delete.setPassword(request.getPassword());
        userService.deleteUser(delete);
        assertEquals(0, userService.countAllUsers());
    }
@Test
public void test_userCanLogin(){
        LoginRequest login = new LoginRequest();
        login.setUsername("");
        login.setPassword("");
        assertThrows(ToDoListException.class,()->userService.login(login));
        RegisterRequest request = new RegisterRequest();
        request.setUsername("username");
        request.setPassword("password");
        userService.register(request);
        login.setUsername("username");
        login.setPassword("password");
        userService.login(login);
    }

}
