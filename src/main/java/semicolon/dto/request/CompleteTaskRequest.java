package semicolon.dto.request;

import lombok.Data;

@Data
public class CompleteTaskRequest{
    private String taskName;
    private String username;
    private String password;
}