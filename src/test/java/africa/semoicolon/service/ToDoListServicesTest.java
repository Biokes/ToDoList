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
        createRequest.setDueDate("2014.07.25");
        createRequest.setDescription("description");
        assertThrows(ToDoListException.class,()->appService.createTask(createRequest));
        createRequest.setDueDate("2024.07.25");
        appService.createTask(createRequest);
        createRequest.setTaskTitle("title1");
        appService.createTask(createRequest);
        createRequest.setTaskTitle("title2");
        appService.createTask(createRequest);
        assertEquals(3,appService.countAllUserTask(deleteRequest.getUsername()));
        assertThrows(ToDoListException.class,()->appService.deleteAccount(deleteRequest));
        deleteRequest.setPassword("password1");
        appService.deleteAccount(deleteRequest);
        assertEquals(0,appService.countAllUsers());
        assertThrows(ToDoListException.class,()->appService.countAllUserTask(deleteRequest.getUsername()));

    }
    @Test
    public void deleteTask_testTaskIsDeleted(){
        DeleteTaskRequest deleteTaskRequest = new DeleteTaskRequest();
        deleteTaskRequest.setTaskName("task1");
        deleteTaskRequest.setUsername("username");
        deleteTaskRequest.setPassword("");
        RegisterRequest register = new RegisterRequest();
        register.setUsername("username");
        register.setPassword("password");
        appService.register(register);
        assertThrows(ToDoListException.class,()->appService.deleteTask(deleteTaskRequest));
        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setUsername("usernam");
        createTaskRequest.setTaskTitle("task1");
        createTaskRequest.setDueDate("2023-07-09");
        assertThrows(ToDoListException.class, ()->appService.createTask(createTaskRequest));
        createTaskRequest.setUsername("username");
        assertThrows(ToDoListException.class,()->appService.createTask(createTaskRequest));
        createTaskRequest.setDueDate("2024-06-10");
        appService.createTask(createTaskRequest);
        assertThrows(ToDoListException.class,()->appService.deleteTask(deleteTaskRequest));
        deleteTaskRequest.setPassword("password");
    }
}
//register
// delete user
//create task
//delete
