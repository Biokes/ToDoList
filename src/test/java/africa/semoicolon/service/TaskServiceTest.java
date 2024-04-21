package africa.semoicolon.service;
import africa.semoicolon.exceptions.*;

import africa.semoicolon.request.CompleteTaskRequest;
import africa.semoicolon.request.CreateTaskRequest;
import africa.semoicolon.request.StartTaskRequest;
import africa.semoicolon.response.CompleteTaskResponse;
import africa.semoicolon.response.CreateTaskResponse;
import africa.semoicolon.response.StartTaskResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static africa.semoicolon.model.TaskStatus.*;
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
        System.out.println(response);
    }
    @Test
    public void startTask_testStartIsStarted(){
        StartTaskRequest startRequest = new StartTaskRequest();
        startRequest.setUsername("username");
        startRequest.setTaskName("nylon");
        assertThrows(TaskNotFoundException.class,()-> taskService.startTaskWith(startRequest));
        CreateTaskRequest createRequest = new CreateTaskRequest();
        createRequest.setUsername("username");
        createRequest.setTaskTitle("nylon   ");
        createRequest.setDescription("description");
        taskService.createTask(createRequest);
        StartTaskResponse response = taskService.startTaskWith(startRequest);
        assertThrows(TaskStartedException.class,()-> taskService.startTaskWith(startRequest));
        assertEquals("username", response.getUsername());
        assertEquals("nylon", response.getTaskTitle());
        assertEquals("description",response.getDescription());
        assertEquals(IN_PROGRESS, response.getStatus());
    }
    @Test
    public void completeTask_testTaskIsCopmpleted(){
        CompleteTaskRequest complete = new CompleteTaskRequest();
        complete.setTaskName("nylon");
        complete.setUsername("username");
        assertThrows(TaskNotFoundException.class,()-> taskService.completeTask(complete));
        CreateTaskRequest createRequest = new CreateTaskRequest();
        createRequest.setUsername("username");
        createRequest.setTaskTitle("nylon   ");
        createRequest.setDescription("description");
        taskService.createTask(createRequest);
        assertThrows(TaskNotStartedException.class,()-> taskService.completeTask(complete));
        StartTaskRequest startTask = new StartTaskRequest();
        assertThrows(InvalidDetails.class,()->taskService.startTaskWith(startTask));
        startTask.setUsername(" username  ");
        assertThrows(InvalidDetails.class,()->taskService.startTaskWith(startTask));
        startTask.setTaskName("hjewb");
        assertThrows(TaskNotFoundException.class,()->taskService.startTaskWith(startTask));
        startTask.setTaskName("  nylon  ");
        startTask.setUsername("username");
        CompleteTaskResponse completeResponse = taskService.completeTask(complete);
        assertEquals("username", completeResponse.getUsername());
        assertEquals("nylon",completeResponse.getTaskName());
        assertEquals(COMPLETED, completeResponse.getStatus());


    }
}