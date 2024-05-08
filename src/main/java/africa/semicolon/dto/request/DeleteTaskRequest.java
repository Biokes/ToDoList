package africa.semicolon.dto.request;

import lombok.Data;

@Data
public class DeleteTaskRequest{
    private String taskName;
    private String username;
    private String password;
}
