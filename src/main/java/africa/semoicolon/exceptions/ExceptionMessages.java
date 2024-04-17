package africa.semoicolon.exceptions;

public enum ExceptionMessages{
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
