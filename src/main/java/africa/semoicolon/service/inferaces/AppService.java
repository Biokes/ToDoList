package africa.semoicolon.service.inferaces;

import africa.semoicolon.dtos.request.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface AppService {
    void deleteAll();
    void register(RegisterRequest register);
}
