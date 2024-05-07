package semicolon.dto.response;

import lombok.Data;

@Data
public class Notifier {
    private String description;
    private String taskTitle;
    private String timeNotified;
}
