package africa.semoicolon.dtos.response;

import africa.semoicolon.data.model.TaskStatus;
import lombok.Data;

@Data
public class CompleteTaskResponse{
    private String username;
    private String taskName;
    private String startDate;
    private String dateCreated;
    private TaskStatus status;
    private String duration;
    private String assignerUsername;
}
