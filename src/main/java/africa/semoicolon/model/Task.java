package africa.semoicolon.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Tasks")
public class Task{
    @Id
    private String id;
    private String username;
    private String description;
    private String taskTitle;
    private TaskStatus status;
    private String dateCreated;

}
