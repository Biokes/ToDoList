package africa.semoicolon.dtos.request;

import lombok.Data;

@Data
public class AssignTaskRequest {
    private String username;
    private String taskTitle;
    private String description;
    private String dueDate;
    private String assignee;
}
