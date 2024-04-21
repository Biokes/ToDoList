package africa.semoicolon.response;

import africa.semoicolon.model.TaskStatus;
import lombok.Data;

@Data
public class CreateTaskResponse{
    private String username;
    private String title;
    private String description;
    private TaskStatus status;
    private String dateCreated;
    private String dueDate;
}
