package africa.semoicolon.dtos.request;

import lombok.Data;

@Data
public class StartTaskRequest{
    private String taskName;
    private String username;
}
