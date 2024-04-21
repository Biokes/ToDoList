package africa.semoicolon.response;

import africa.semoicolon.model.TaskStatus;
import lombok.Data;

@Data
public class CompleteTaskResponse{
    private String username;
    private String taskName;
    private String startDate;
    private String dateCreated;
    private TaskStatus status;
    private String duration;
}
