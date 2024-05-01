package africa.semoicolon.dtos.response;

import africa.semoicolon.data.model.TaskStatus;
import lombok.Data;

@Data
public class AssignedTasksResponse {
    private String username;
    private String assigneeUsername;
    private TaskStatus status;
    private String dateAssigned;
    private String dueDate;
    private String title;
    private String description;
    private String dateStarted;

}
