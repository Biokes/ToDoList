package africa.semoicolon.service;

import africa.semoicolon.response.CreateTaskResponse;
import org.springframework.stereotype.Service;

@Service
public interface TaskService{
    void deleteAll();
    CreateTaskResponse createTask();

}
