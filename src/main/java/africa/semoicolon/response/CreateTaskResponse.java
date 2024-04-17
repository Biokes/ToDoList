package africa.semoicolon.response;

import lombok.Data;

@Data
public class CreateTaskResponse{
    private String username;
    private String title;
    private String description;
}
