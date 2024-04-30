package africa.semoicolon.service;

import africa.semoicolon.dtos.request.*;
import africa.semoicolon.dtos.response.*;
import org.springframework.stereotype.Service;

@Service
public interface TaskService{
    void deleteAll();
    CreateTaskResponse createTask(CreateTaskRequest request);
    StartTaskResponse startTaskWith(StartTaskRequest startRequest);
    CompleteTaskResponse completeTask(CompleteTaskRequest complete);
    void deleteTaskWith(DeleteTaskRequest delete);
    UpdateTaskResponse updateTask(UpdateTaskRequest update);
    AssignTaskResponse assignTask(AssignTaskRequest assign);
    long countTaskByUsername(String username);
}
