package africa.semoicolon.service;

import africa.semoicolon.service.inferaces.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService service;
    @Test
    public void register_testUserIsregistered(){

    }
}
