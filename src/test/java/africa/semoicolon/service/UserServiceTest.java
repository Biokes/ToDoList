package africa.semoicolon.service;

import africa.semoicolon.dtos.request.RegisterRequest;
import africa.semoicolon.exceptions.InvalidDetails;
import africa.semoicolon.service.inferaces.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;
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
    }
}
