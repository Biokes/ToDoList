package africa.semoicolon.service;

import africa.semoicolon.data.model.Task;
import africa.semoicolon.dtos.request.*;
import africa.semoicolon.dtos.response.*;
import org.springframework.stereotype.Service;

import java.util.List;

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
    void deleteTasksByUsername(String username);
    List<CreateTaskResponse> countPendingTask(String username);
}
