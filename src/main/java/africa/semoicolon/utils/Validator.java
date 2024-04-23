package africa.semoicolon.utils;

import africa.semoicolon.dtos.request.*;
import africa.semoicolon.exceptions.InvalidDetails;
import africa.semoicolon.exceptions.ToDoListException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static africa.semoicolon.exceptions.ExceptionMessages.ELAPSED_DATE;
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

    public static void validateDeleteTaskRequest(DeleteTaskRequest delete){
        delete.setTaskName(validate(delete.getTaskName()));
        delete.setUsername(validate(delete.getUsername()));
    }

    public static LocalDate validateDate(LocalDate localDate) {
        if(localDate.isBefore(LocalDate.now()))
            throw new ToDoListException(ELAPSED_DATE.getMessage());
        return localDate;
    }

    public static void validateUpdate(UpdateTaskRequest update) {
        update.setUsername(validate(update.getUsername()));
        update.setNewTitle(validate(update.getNewTitle()));
        update.setOldTitle(validate(update.getOldTitle()));
        if(Optional.ofNullable(update.getDescription()).isPresent()) {
            update.setDescription(validate(update.getDescription()));
        }
    }

    public static void validateAssignTaskRequest(AssignTaskRequest assign) {
        assign.setTaskTitle(validate(assign.getTaskTitle()));
        assign.setAssignerUsername(validate(assign.getAssignerUsername()));
        assign.setAssigneeUsername(validate(assign.getAssignerUsername()));
        assign.setDueDate(validateDate(Mapper.convertToDate(assign.getDueDate())).toString());
        if(Optional.ofNullable(assign.getDescription()).isPresent()){
            assign.setDescription(validate(assign.getDescription()));
            return;
        }
        assign.setDescription("no content");
    }


}
