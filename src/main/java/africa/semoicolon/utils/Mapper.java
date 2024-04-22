package africa.semoicolon.utils;

import africa.semoicolon.data.model.Task;
import africa.semoicolon.dtos.request.CreateTaskRequest;
import africa.semoicolon.dtos.response.CompleteTaskResponse;
import africa.semoicolon.dtos.response.CreateTaskResponse;
import africa.semoicolon.dtos.response.StartTaskResponse;
import africa.semoicolon.exceptions.InvalidDetails;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static africa.semoicolon.data.model.TaskStatus.IN_PROGRESS;
import static africa.semoicolon.data.model.TaskStatus.PENDING;
import static africa.semoicolon.exceptions.ExceptionMessages.INVALID_DATE;

public class Mapper{
    public static Task mapCreateTaskRequestToTask(CreateTaskRequest request){
    Task task = new Task();
    task.setUsername(request.getUsername());
    task.setTaskTitle(request.getTaskTitle());
    task.setDescription(request.getDescription());
    task.setStatus(PENDING);
    task.setDueDate(Validator.validateDate(convertToDate(request.getDueDate())));
    task.setDateCreated(task.getStatus().getDate().toString());
    return task;
    }
    private static LocalDate convertToDate(String dateGiven){
        try{
            dateGiven = dateGiven.replaceAll("\\s+", " ");
            dateGiven = dateGiven.replaceAll("\\D+", "/");
            return LocalDate.parse(dateGiven, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        catch (Exception exception){
            throw new InvalidDetails(INVALID_DATE.getMessage());
        }
    }
    public static CreateTaskResponse mapTaskToResponse(Task task){
        CreateTaskResponse response = new CreateTaskResponse();
        response.setStatus(task.getStatus());
        response.setDescription(setDescription(task.getDescription()));
        response.setUsername(task.getUsername());
        response.setTitle(task.getTaskTitle());
        LocalDateTime created = LocalDateTime.parse(task.getDateCreated());
        response.setDateCreated(created.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a")));
        response.setDueDate(task.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        response.setStatus(task.getStatus());
        return response;
    }
    private static String setDescription(String given){
        Optional<String> string = Optional.ofNullable(given);
        if(string.isEmpty())
            given= "no description provided";
        return given;
    }
    public static StartTaskResponse mapTaskToStartTaskResponse(Task task){
        StartTaskResponse response = new StartTaskResponse();
        response.setTaskTitle(task.getTaskTitle());
        task.setStatus(IN_PROGRESS);
        response.setStatus(task.getStatus());
        LocalDateTime date = task.getStatus( ).getDate( );
        task.setDateStarted(task.getStatus().getDate());
        if(task.getDescription().isBlank())
            response.setDescription("\"nothing saved as description\"");
        else response.setDescription(task.getDescription());
        response.setUsername(task.getUsername());
        return response;
    }
    public static CompleteTaskResponse mapToCompleteTaskResponse(Task complete){
        CompleteTaskResponse response = new CompleteTaskResponse();
        response.setTaskName(complete.getTaskTitle());
        response.setUsername(complete.getUsername());
        response.setStatus(complete.getStatus());
        response.setDateCreated(complete.getDateCreated());
        response.setDuration(getDuration(complete.getDateStarted().toString()));
        LocalDateTime date = complete.getDateStarted();
        response.setStartDate(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a")));
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
