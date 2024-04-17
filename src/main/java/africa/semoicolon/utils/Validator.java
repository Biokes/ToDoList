package africa.semoicolon.utils;

import africa.semoicolon.exceptions.InvalidDetails;
import africa.semoicolon.request.CreateTaskRequest;

import java.util.Optional;

import static africa.semoicolon.exceptions.ExceptionMessages.INVALID_DETAILS;

public class Validator{
    public static String validate(String data){
        Optional<String> newData = Optional.ofNullable(data);
        if(newData.isEmpty() || newData.get().isEmpty())
            throw new InvalidDetails(INVALID_DETAILS.getMessage());
        data = data.strip();
        return data.replaceAll("\\s+", " ");
    }
    public static void validateCreateTaskRequest(CreateTaskRequest request){
        request.setDescription(validate(request.getDescription()));
        request.setTaskTitle(validate(request.getTaskTitle()));
        request.setUsername(validate(request.getUsername()));
    }
}
