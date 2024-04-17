package africa.semoicolon.service;

import africa.semoicolon.request.CreateTaskRequest;
import africa.semoicolon.response.CreateTaskResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        CreateTaskResponse response = taskService.createTask();
    }
}