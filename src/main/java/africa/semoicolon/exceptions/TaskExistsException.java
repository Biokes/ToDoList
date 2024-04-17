package africa.semoicolon.exceptions;

public class TaskExistsException extends ToDoListException{
    public TaskExistsException(String message){
        super(message);
    }
}
