package africa.semoicolon.dtos.response;
import africa.semoicolon.data.model.TaskStatus;
import lombok.Data;

@Data
public class AssignTaskResponse {
    private String assigneeUsername;
    private String taskTitle;
    private String assignerUsername;
    private String dueDate;
    private TaskStatus status;
    private String description;


}
