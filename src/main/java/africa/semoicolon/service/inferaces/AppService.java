package africa.semoicolon.service.inferaces;

import africa.semoicolon.dtos.request.CreateTaskRequest;
import africa.semoicolon.dtos.request.DeleteUserRequest;
import africa.semoicolon.dtos.request.RegisterRequest;
import africa.semoicolon.dtos.response.CreateTaskResponse;
import africa.semoicolon.dtos.response.ViewTaskResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppService {
    void deleteAll();
    void register(RegisterRequest register);
    long countAllUsers();
    CreateTaskResponse createTask(CreateTaskRequest createTaskRequest);
    void deleteAccount(DeleteUserRequest deleteRequest);
    List<ViewTaskResponse> getAllUserTasks(String username);
}
