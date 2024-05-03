package africa.semoicolon.dtos.response;

import africa.semoicolon.data.model.Notifications;
import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {
    private List<Notifier> notification;
}
