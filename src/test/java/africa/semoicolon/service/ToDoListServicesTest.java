package africa.semoicolon.service;


import africa.semoicolon.service.inferaces.AppService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ToDoListServicesTest {
    @Autowired
    private AppService operations;
    @BeforeEach
    void wipe(){
        operations.deleteAll();
    }

}
