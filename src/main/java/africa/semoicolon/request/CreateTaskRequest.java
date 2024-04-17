package africa.semoicolon.request;

import lombok.Data;

@Data
public class CreateTaskRequest{
    private String username;
    private String taskTitle;
    private String description;
}
