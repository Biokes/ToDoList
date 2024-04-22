package africa.semoicolon.exceptions;

public enum ExceptionMessages{
    TASK_NOT_FOUND("Task not found`"),
    TASK_STARTED("Task is already started"),
    TASK_EXISTS("Task with title already exists"),
    TASK_NOT_STARTED("Task not started"),
    ELAPSED_DATE("Date provided has elapsed"),
    INVALID_DATE("Invalid date provided.Pls provide date in dd/mm/yyyy format"),
    INVALID_DETAILS("Invalid details provided");
    ExceptionMessages(String message){
        this.message = message;
    }
    String message;
    public String getMessage(){
        return this.message;
    }
}
