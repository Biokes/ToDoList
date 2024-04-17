package africa.semoicolon.service;

import africa.semoicolon.request.CreateTaskRequest;
import africa.semoicolon.response.CreateTaskResponse;
import org.springframework.stereotype.Service;

@Service
public interface TaskService{
    void deleteAll();
    CreateTaskResponse createTask(CreateTaskRequest request);
}
