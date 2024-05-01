package africa.semoicolon.service;
import africa.semoicolon.dtos.request.*;
import africa.semoicolon.dtos.response.*;
import africa.semoicolon.exceptions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static africa.semoicolon.data.model.TaskStatus.*;
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
        assertThrows(ToDoListException.class,()->taskService.createTask(createRequest));
        createRequest.setDueDate("2007.12/04");
        assertThrows(ToDoListException.class,()->taskService.createTask(createRequest));
        createRequest.setDueDate("2024.07/25");
        CreateTaskResponse response = taskService.createTask(createRequest);
        assertEquals("username", response.getUsername());
        assertEquals("taskTitle", response.getTitle());
        assertEquals("description", response.getDescription());
        assertEquals("2024-07-25", response.getDueDate());
        assertEquals(PENDING, response.getStatus());
    }
    @Test
    public void createTaskTwice_testExceptionIsThrown(){
        CreateTaskRequest createRequest = new CreateTaskRequest();
        createRequest.setUsername("username");
        createRequest.setTaskTitle("task title   ");
        createRequest.setDescription("description");
        createRequest.setDueDate("2024[09-19");
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
        CreateTaskRequest createRequest = new CreateTaskRequest();
        createRequest.setUsername("username");
        createRequest.setTaskTitle("nylon   ");
        createRequest.setDescription("description");
        createRequest.setDueDate("2024'09=27");
        taskService.createTask(createRequest);
        StartTaskResponse response = taskService.startTaskWith(startRequest);
        assertThrows(TaskStartedException.class,()-> taskService.startTaskWith(startRequest));
        assertEquals("username", response.getUsername());
        assertEquals("nylon", response.getTaskTitle());
        assertEquals("description",response.getDescription());
        assertEquals(IN_PROGRESS, response.getStatus());
    }
    @Test
    public void completeTask_testTaskIsCompleted() throws InterruptedException{
        CompleteTaskRequest complete = new CompleteTaskRequest();
        complete.setTaskName("nylon");
        complete.setUsername("username");
        assertThrows(TaskNotFoundException.class,()-> taskService.completeTask(complete));
        CreateTaskRequest createRequest = new CreateTaskRequest();
        createRequest.setUsername("username");
        createRequest.setTaskTitle("nylon   ");
        createRequest.setDescription("description");
        createRequest.setDueDate("2024[07]12");
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
        taskService.startTaskWith(startTask);
        CompleteTaskResponse completeResponse = taskService.completeTask(complete);
        assertEquals("username", completeResponse.getUsername());
        assertEquals("nylon",completeResponse.getTaskName());
        assertEquals(COMPLETED, completeResponse.getStatus());
    }
    @Test
    public void deleteTask_testTaskIsDeleted(){
        DeleteTaskRequest delete = new DeleteTaskRequest();
        delete.setTaskName("");
        delete.setUsername("");
        assertThrows(ToDoListException.class,()-> taskService.deleteTaskWith(delete));
        delete.setTaskName(" task  ");
        assertThrows(ToDoListException.class,()-> taskService.deleteTaskWith(delete));
        delete.setUsername("username");
        assertThrows(ToDoListException.class,()-> taskService.deleteTaskWith(delete));
        CreateTaskRequest create = new CreateTaskRequest();
        create.setUsername("username");
        create.setTaskTitle("task");
        create.setDueDate("2024;12.31");
        taskService.createTask(create);
        assertThrows(TaskExistsException.class,()->taskService.createTask(create));
        taskService.deleteTaskWith(delete);
        assertThrows(ToDoListException.class,()->taskService.deleteTaskWith(delete));
    }
    @Test
    public void updateTaskTitle_testTaskTitleIsUpdated(){
        UpdateTaskRequest update = new UpdateTaskRequest();
        update.setUsername("");
        update.setOldTitle("");
        update.setNewTitle("");
        update.setDescription("");
        assertThrows(ToDoListException.class,()->taskService.updateTask(update));
        update.setUsername("name");
        update.setNewTitle("1234");
        update.setDescription("my house");
        update.setOldTitle("title");
        assertThrows(ToDoListException.class,()->taskService.updateTask(update));
        CreateTaskRequest request = new CreateTaskRequest();
        request.setUsername("name");
        request.setTaskTitle("title");
        request.setDueDate("2024.12.12");
        CreateTaskResponse response = taskService.createTask(request);
        assertEquals("name", response.getUsername());
        assertEquals("no description provided", response.getDescription());
        assertEquals(PENDING,response.getStatus());
        UpdateTaskResponse updateResponse = taskService.updateTask(update);
        assertEquals("1234", updateResponse.getTaskTitle());
    }
    @Test
    public void assignTask_testTaskIsAssigned(){
        AssignTaskRequest assign= new AssignTaskRequest();
        assign.setAssignerUsername("");
        assign.setAssigneeUsername("");
        assign.setDueDate("");
        assign.setTaskTitle("");
        assertThrows(ToDoListException.class,()->taskService.assignTask(assign));
        assign.setAssigneeUsername("username");
        assign.setDueDate("2024/04/21");
        assign.setTaskTitle("title");
        assign.setAssignerUsername("we");
        assertThrows(ToDoListException.class,()->taskService.assignTask(assign));
        assign.setDueDate("2024}09{21");
        AssignTaskResponse response  = taskService.assignTask(assign);
        assertEquals("we",response.getAssignerUsername());
        assertEquals("username", response.getAssigneeUsername());
        assertEquals(PENDING, response.getStatus());
        assertEquals("no description saved",response.getDescription());
        assertEquals("title",response.getTaskTitle());
        assertEquals("2024-09-21",response.getDueDate());
        assertEquals(1, taskService.countTaskByUsername("username"));
    }
    @Test
    public void startAssignedTask_testTaskIsStarted(){
        AssignTaskRequest assign= new AssignTaskRequest();
        assign.setAssigneeUsername("username");
        assign.setTaskTitle("title");
        assign.setAssignerUsername("we");
        assign.setDueDate("2024}09{21");
        AssignTaskResponse response  = taskService.assignTask(assign);
        assertEquals("we",response.getAssignerUsername());
        assertEquals("username", response.getAssigneeUsername());
        assertEquals(PENDING, response.getStatus());
        assertEquals("no description saved",response.getDescription());
        assertEquals("title",response.getTaskTitle());
        assertEquals("2024-09-21",response.getDueDate());
        assertEquals(1, taskService.countTaskByUsername("username"));
        StartTaskRequest startTask = new StartTaskRequest();
        startTask.setUsername("username");
        startTask.setTaskName("title");
        StartTaskResponse startResponse = taskService.startTaskWith(startTask);
        assertEquals(IN_PROGRESS, startResponse.getStatus());
        assertEquals("no description saved",response.getDescription());
        assertEquals("title",response.getTaskTitle());
        assertEquals("2024-09-21",response.getDueDate());
        CompleteTaskRequest complete = new CompleteTaskRequest();
        complete.setUsername("username");
        complete.setTaskName("title");
        CompleteTaskResponse  completeResponse = taskService.completeTask(complete);
        assertEquals("username", completeResponse.getUsername());
        assertEquals(COMPLETED, completeResponse.getStatus());
        assertEquals("we",completeResponse.getAssignerUsername());
    }
    @Test
    public void completeTaskTwice_testExceptionIsThrown(){
        AssignTaskRequest assign= new AssignTaskRequest();
        assign.setAssigneeUsername("username");
        assign.setTaskTitle("title");
        assign.setAssignerUsername("we");
        assign.setDueDate("2024}09{21");
        AssignTaskResponse response  = taskService.assignTask(assign);
        assertEquals("we",response.getAssignerUsername());
        assertEquals("username", response.getAssigneeUsername());
        assertEquals(PENDING, response.getStatus());
        assertEquals("no description saved",response.getDescription());
        assertEquals("title",response.getTaskTitle());
        assertEquals("2024-09-21",response.getDueDate());
        assertEquals(1, taskService.countTaskByUsername("username"));
        StartTaskRequest startTask = new StartTaskRequest();
        startTask.setUsername("username");
        startTask.setTaskName("title");
        StartTaskResponse startResponse = taskService.startTaskWith(startTask);
        assertEquals(IN_PROGRESS, startResponse.getStatus());
        assertEquals("no description saved",response.getDescription());
        assertEquals("title",response.getTaskTitle());
        assertEquals("2024-09-21",response.getDueDate());
        CompleteTaskRequest complete = new CompleteTaskRequest();
        complete.setUsername("username");
        complete.setTaskName("title");
        taskService.completeTask(complete);
        assertThrows(ToDoListException.class,()->taskService.completeTask(complete));
        assertThrows(TaskStartedException.class,()->taskService.startTaskWith(startTask));
        assertThrows(ToDoListException.class,()->taskService.completeTask(complete));
    }
    @Test
    public void deleteAllUserTask_testAllUserTaskIsDeleted(){
        CreateTaskRequest createRequest = new CreateTaskRequest();
        createRequest.setUsername("username");
        createRequest.setDescription("description");
        createRequest.setTaskTitle("taskTitle");
        createRequest.setDueDate("2024.07/25");
        taskService.createTask(createRequest);
        createRequest.setTaskTitle("taskTitle1");
        taskService.createTask(createRequest);
        createRequest.setTaskTitle("taskTitle2");
        taskService.createTask(createRequest);
        createRequest.setTaskTitle("taskTitle3");
        taskService.createTask(createRequest);
        assertEquals(4, taskService.countTaskByUsername("username"));
        taskService.deleteTasksByUsername("username");
        assertEquals(0, taskService.countTaskByUsername("username"));
    }
    @Test
    public void findAllStartedTask_testAllStartedTaskAreFound(){
        CreateTaskRequest createRequest = new CreateTaskRequest();
        createRequest.setUsername("username");
        createRequest.setDescription("description");
        createRequest.setTaskTitle("taskTitle");
        createRequest.setDueDate("2024.07/25");
        taskService.createTask(createRequest);
        createRequest.setTaskTitle("taskTitle1");
        taskService.createTask(createRequest);
        createRequest.setTaskTitle("taskTitle2");
        taskService.createTask(createRequest);
        createRequest.setTaskTitle("taskTitle3");
        taskService.createTask(createRequest);
        assertEquals(4, taskService.countPendingTask("username").size());
    }
}