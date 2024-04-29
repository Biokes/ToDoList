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
        assertThrows(InvalidDetails.class,()->taskService.createTask(createRequest));
        createRequest.setDueDate("12/04.2007");
        assertThrows(ToDoListException.class,()->taskService.createTask(createRequest));
        createRequest.setDueDate("25/07/2024");
        CreateTaskResponse response = taskService.createTask(createRequest);
        assertEquals("username", response.getUsername());
        assertEquals("taskTitle", response.getTitle());
        assertEquals("description", response.getDescription());
        assertEquals("25/07/2024", response.getDueDate());
        assertEquals(PENDING, response.getStatus());
        System.out.println(response);
    }
    @Test
    public void createTaskTwice_testExceptionIsThrown(){
        CreateTaskRequest createRequest = new CreateTaskRequest();
        createRequest.setUsername("username");
        createRequest.setTaskTitle("task title   ");
        createRequest.setDescription("description");
        createRequest.setDueDate("27'09[2024");
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
        createRequest.setDueDate("27'09[2024");
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
        createRequest.setDueDate("27'09[2024");
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
        create.setDueDate("31.12;2024");
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
        request.setDueDate("12/12.2024");
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
        assign.setDueDate("21/04/2024");
        assign.setTaskTitle("title");
        assign.setAssignerUsername("we");
        assertThrows(ToDoListException.class,()->taskService.assignTask(assign));
        assign.setDueDate("21/05}2024");
        AssignTaskResponse response  = taskService.assignTask(assign);
        assertEquals("we",response.getAssignerUsername());
        assertEquals("username", response.getAssigneeUsername());
//        assertEquals(0,0);
    }

}