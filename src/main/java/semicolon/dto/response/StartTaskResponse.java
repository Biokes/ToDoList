package semicolon.dto.response;

import semicolon.data.model.TaskStatus;
import lombok.Data;

@Data
public class StartTaskResponse{
    private String username;
    private  String taskTitle;
    private String description;
    private TaskStatus status;
}
