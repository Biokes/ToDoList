package africa.semoicolon.exceptions;

public enum ExceptionMessages{
    TASK_NOT_FOUND("Task not found"),
    TASK_STARTED("Task is already started"),
    TASK_EXISTS("Task with title already exists"),
    INVALID_DETAILS("Invalid details provided");
    ExceptionMessages(String message){
        this.message = message;
    }
    String message;
    public String getMessage(){
        return this.message;
    }
}
