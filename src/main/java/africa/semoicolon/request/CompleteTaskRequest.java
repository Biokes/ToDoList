package africa.semoicolon.request;

import lombok.Data;

@Data
public class CompleteTaskRequest{
    private String taskName;
    private String username;
}
