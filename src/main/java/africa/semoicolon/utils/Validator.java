package africa.semoicolon.utils;

import africa.semoicolon.exceptions.InvalidDetails;
import africa.semoicolon.request.CompleteTaskRequest;
import africa.semoicolon.request.CreateTaskRequest;
import africa.semoicolon.request.StartTaskRequest;

import java.util.Optional;

import static africa.semoicolon.exceptions.ExceptionMessages.INVALID_DETAILS;

public class Validator{
    public static String validate(String data){
        Optional<String> newData = Optional.ofNullable(data);
        if(Optional.ofNullable(data).isEmpty() || Optional.of(data).get().isBlank())
            throw new InvalidDetails(INVALID_DETAILS.getMessage());
        data = data.strip();
        return data.replaceAll("\\s+", " ");
    }
    public static void validateCreateTaskRequest(CreateTaskRequest request){
        request.setTaskTitle(validate(request.getTaskTitle()));
        request.setUsername(validate(request.getUsername()));
    }
    public static void validateCompleteTaskRequest(CompleteTaskRequest complete){
        complete.setTaskName(validate(complete.getTaskName()));
        complete.setUsername(validate(complete.getUsername()));
    }
    public static void validateStartTAskRequest(StartTaskRequest startRequest){
        startRequest.setTaskName(validate(startRequest.getTaskName()));
        startRequest.setUsername(validate(startRequest.getUsername()));
    }
}
