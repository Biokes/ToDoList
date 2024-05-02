package africa.semoicolon.service;


import africa.semoicolon.dtos.request.CreateTaskRequest;
import africa.semoicolon.dtos.request.DeleteTaskRequest;
import africa.semoicolon.dtos.request.DeleteUserRequest;
import africa.semoicolon.dtos.request.RegisterRequest;
import africa.semoicolon.dtos.response.CreateTaskResponse;
import africa.semoicolon.exceptions.ToDoListException;
import africa.semoicolon.service.inferaces.AppService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static africa.semoicolon.data.model.TaskStatus.PENDING;
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
        createRequest.setUsername("name");
        createRequest.setDueDate("2021-04-21");
        createRequest.setTaskTitle("");
        assertThrows(ToDoListException.class,()->appService.createTask(createRequest));
        createRequest.setDueDate("2024-08-21");
        createRequest.setTaskTitle("task1");
        assertThrows(ToDoListException.class,()->appService.createTask(createRequest));
        createRequest.setUsername("username");
        CreateTaskResponse createResponse = appService.createTask(createRequest);
        assertEquals("username",createRequest.getUsername());
        assertEquals("task1", createResponse.getTitle());
        assertEquals(PENDING,createResponse.getStatus());
        System.out.println(createResponse);
    }
    @Test
    public void deleteAccount_testAccountAndTaskAreDeleted(){
        DeleteUserRequest deleteRequest = new DeleteUserRequest();
        deleteRequest.setUsername("username");
        deleteRequest.setPassword("password");
        assertThrows(ToDoListException.class,()-> appService.deleteAccount(deleteRequest));
        RegisterRequest register = new RegisterRequest();
        register.setUsername("username");
        register.setPassword("password1");
        appService.register(register);
        CreateTaskRequest createRequest = new CreateTaskRequest();
        createRequest.setUsername("username");
        createRequest.setTaskTitle("title");
        createRequest.setDueDate("2013-12-30");
        createRequest.setDescription("description");
        appService.createTask(createRequest);
        createRequest.setTaskTitle("title1");
        appService.createTask(createRequest);
        createRequest.setTaskTitle("title2");
        appService.createTask(createRequest);
        assertThrows(ToDoListException.class,()->appService.deleteAccount(deleteRequest));
        deleteRequest.setPassword("password1");
        appService.deleteAccount(deleteRequest);
        assertEquals(0,appService.countAllUsers());
        assertThrows(ToDoListException.class,()->appService.getAllUserTasks(deleteRequest.getUsername()));

    }
}
