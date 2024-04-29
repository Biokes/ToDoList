package africa.semoicolon.data.model;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

@Data
@Document("AssignTasks")
public class AssignTask {
    private String id;
    private String assigneeUsername;
    private String assignerUsername;
    private LocalDate dateCreated;
    private TaskStatus status;
}
