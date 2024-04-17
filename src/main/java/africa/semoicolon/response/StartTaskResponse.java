package africa.semoicolon.response;

import africa.semoicolon.model.TaskStatus;
import lombok.Data;

@Data
public class StartTaskResponse{
    private String username;
    private  String taskTitle;
    private String description;
    private TaskStatus status;
}
