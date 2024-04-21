package africa.semoicolon.request;

import lombok.Data;

@Data
public class DeleteTaskRequest{
    private String taskName;
    private String username;
}
