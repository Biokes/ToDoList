package africa.semoicolon.service.inferaces;

import africa.semoicolon.dtos.request.CreateTaskRequest;
import africa.semoicolon.dtos.request.DeleteTaskRequest;
import africa.semoicolon.dtos.request.DeleteUserRequest;
import africa.semoicolon.dtos.request.RegisterRequest;
import africa.semoicolon.dtos.response.CreateTaskResponse;
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
}
