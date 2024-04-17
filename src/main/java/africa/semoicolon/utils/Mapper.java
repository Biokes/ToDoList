package africa.semoicolon.utils;

import africa.semoicolon.model.Task;
import africa.semoicolon.request.CreateTaskRequest;
import africa.semoicolon.response.CreateTaskResponse;
import africa.semoicolon.response.StartTaskResponse;

import java.time.format.DateTimeFormatter;

import static africa.semoicolon.model.TaskStatus.PENDING;

public class Mapper{
    public static Task mapCreateTaskRequest(CreateTaskRequest request){
    Task task = new Task();
    task.setUsername(request.getUsername());
    task.setTaskTitle(request.getTaskTitle());
    task.setDescription(request.getDescription());
    task.setStatus(PENDING);
    task.setDateCreated(task.getStatus().getDate().
         format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a")));
    return task;
    }

    public static CreateTaskResponse mapTaskToRequest(Task task){
        CreateTaskResponse response = new CreateTaskResponse();
        response.setStatus(task.getStatus());
        response.setDescription(task.getDescription());
        response.setUsername(task.getUsername());
        response.setTitle(task.getTaskTitle());
        response.setDateCreated(task.getDateCreated());
        response.setStatus(task.getStatus());
        return response;
    }

    public static StartTaskResponse mapTaskToStartTaskResponse(Task task){
        StartTaskResponse response = new StartTaskResponse();
        response.setTaskTitle(task.getTaskTitle());
        response.setStatus(task.getStatus());
        if(task.getDescription().isBlank())
            response.setDescription("\"nothing saved as description\"");
        else response.setDescription(response.getDescription());
        response.setUsername(task.getUsername());
        return response;
    }
}
