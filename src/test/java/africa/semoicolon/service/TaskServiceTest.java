package africa.semoicolon.service;
import africa.semoicolon.exceptions.InvalidDetails;

import africa.semoicolon.exceptions.TaskExistsException;
import africa.semoicolon.exceptions.TaskNotFoundException;
import africa.semoicolon.request.CreateTaskRequest;
import africa.semoicolon.request.StartTaskRequest;
import africa.semoicolon.response.CreateTaskResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static africa.semoicolon.model.TaskStatus.PENDING;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TaskServiceTest{
    @Autowired
    private TaskService taskService;
    @BeforeEach
    public void wipe(){
        taskService.deleteAll();
    }
    @Test
    public void CreateTask_TestTaskIsCreated(){
        CreateTaskRequest createRequest = new CreateTaskRequest();
        createRequest.setUsername("username");
        createRequest.setTaskTitle("");
        createRequest.setDescription("description");
        assertThrows(InvalidDetails.class,()->taskService.createTask(createRequest));
        createRequest.setTaskTitle("taskTitle");
        CreateTaskResponse response = taskService.createTask(createRequest);
        assertEquals("username", response.getUsername());
        assertEquals("taskTitle", response.getTitle());
        assertEquals("description", response.getDescription());
        assertEquals(PENDING, response.getStatus());
    }
    @Test
    public void createTaskTwice_testExceptionIsThrown(){
        CreateTaskRequest createRequest = new CreateTaskRequest();
        createRequest.setUsername("username");
        createRequest.setTaskTitle("task title   ");
        createRequest.setDescription("description");
        CreateTaskResponse response = taskService.createTask(createRequest);
        assertThrows(TaskExistsException.class,()->taskService.createTask(createRequest));
        assertEquals("task title",response.getTitle());
    }
    @Test
    public void startTask_testStartIsStarted(){
        StartTaskRequest startRequest = new StartTaskRequest();
        startRequest.setUsername("username");
        startRequest.setTaskName("nylon");
        assertThrows(TaskNotFoundException.class,()-> taskService.startTaskWith(startRequest));
    }
}