package africa.semoicolon.service;

import africa.semoicolon.request.CreateTaskRequest;
import africa.semoicolon.request.StartTaskRequest;
import africa.semoicolon.response.CreateTaskResponse;
import africa.semoicolon.response.StartTaskResponse;
import org.springframework.stereotype.Service;

@Service
public interface TaskService{
    void deleteAll();
    CreateTaskResponse createTask(CreateTaskRequest request);

    StartTaskResponse startTaskWith(StartTaskRequest startRequest);
}
