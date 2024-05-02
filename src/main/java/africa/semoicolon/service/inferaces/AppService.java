package africa.semoicolon.service.inferaces;

import africa.semoicolon.dtos.request.*;
import africa.semoicolon.dtos.response.CreateTaskResponse;
import africa.semoicolon.dtos.response.StartTaskResponse;
import africa.semoicolon.dtos.response.UpdateTaskResponse;
import org.springframework.stereotype.Service;

@Service
public interface AppService {
    void deleteAll();
    void register(RegisterRequest register);
    long countAllUsers();
    CreateTaskResponse createTask(CreateTaskRequest createTaskRequest);
    void deleteAccount(DeleteUserRequest deleteRequest);
    long countAllUserTask(String username);
    void deleteTask(DeleteTaskRequest deleteTaskRequest);
    UpdateTaskResponse updateTask(UpdateTaskRequest updateTask);
    StartTaskResponse startTask(StartTaskRequest startTaskRequest);
}
