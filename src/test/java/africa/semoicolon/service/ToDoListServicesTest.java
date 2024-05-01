package africa.semoicolon.service;


import africa.semoicolon.dtos.request.CreateTaskRequest;
import africa.semoicolon.dtos.request.RegisterRequest;
import africa.semoicolon.dtos.response.CreateTaskResponse;
import africa.semoicolon.exceptions.ToDoListException;
import africa.semoicolon.service.inferaces.AppService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ToDoListServicesTest {
    @Autowired
    private AppService appService;
    @BeforeEach
    void wipe(){
        appService.deleteAll();
    }
    @Test
    public void testUserRegisteration(){
        RegisterRequest register = new RegisterRequest();
        register.setUsername("username");
        register.setPassword("password");
        appService.register(register);
        assertEquals(1,appService.countAllUsers());
    }
    @Test
    public void registerAndCreateTaskTest(){
        RegisterRequest register = new RegisterRequest();
        register.setUsername("username");
        register.setPassword("password");
        appService.register(register);
        CreateTaskRequest createRequest = new CreateTaskRequest();
        createRequest.setUsername("username");
        createRequest.setDueDate("2021-04-21");
        createRequest.setTaskTitle("");
        assertThrows(ToDoListException.class,()->appService.createTask(createRequest));
        createRequest.setDueDate("2024-08-21");
        createRequest.setTaskTitle("task1");
        CreateTaskResponse createResponse = appService.createTask(createRequest);
    }
}
