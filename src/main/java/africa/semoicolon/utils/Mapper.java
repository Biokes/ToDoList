package africa.semoicolon.utils;

import africa.semoicolon.model.Task;
import africa.semoicolon.model.TaskStatus;
import africa.semoicolon.request.CreateTaskRequest;
import africa.semoicolon.response.CompleteTaskResponse;
import africa.semoicolon.response.CreateTaskResponse;
import africa.semoicolon.response.StartTaskResponse;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static africa.semoicolon.model.TaskStatus.IN_PROGRESS;
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
        task.setStatus(IN_PROGRESS);
        response.setStatus(task.getStatus());
        if(task.getDescription().isBlank())
            response.setDescription("\"nothing saved as description\"");
        else response.setDescription(task.getDescription());
        response.setUsername(task.getUsername());
        return response;
    }

    public static CompleteTaskResponse mapToCompleteTaskResponse(Task complete){
        CompleteTaskResponse response = new CompleteTaskResponse();
        response.setTaskName(complete.getTaskTitle( ));
        response.setUsername(complete.getUsername( ));
        response.setStatus(complete.getStatus( ));
        response.setDateCreated(complete.getDateCreated());
        response.setDuration(getDuration(complete.getDateStarted()));
        response.setStartDate(complete.getDateStarted());
        return response;
    }

    private static String getDuration(String status){
        Duration time = Duration.between(LocalDateTime.parse(status),LocalDateTime.now());
        return String.format("%s days, %s hours, %s minutes, %s seconds", time.toDays()%24,
                time.toHours()%(60),
                time.toMinutes()%(60),
                time.toSeconds()%60);
    }
}
