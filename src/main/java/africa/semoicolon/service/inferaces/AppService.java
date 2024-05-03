package africa.semoicolon.service.inferaces;

import africa.semoicolon.dtos.request.*;
import africa.semoicolon.dtos.response.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

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
    CompleteTaskResponse completeTask(CompleteTaskRequest completeTaskRequest);
    AssignTaskResponse assignTask(AssignTaskRequest assignTaskRequest);
    LoginResponse login(LoginRequest login);
    void logOut(LogOut logout);
    List<ViewTaskResponse> findAllTask(LoginRequest login);
    List<ViewTaskResponse> getAllCompleteTask(LoginRequest login);
    List<ViewTaskResponse> getAllCreatedTask(LoginRequest login);
    List<ViewTaskResponse> getAllWorkingTask(LoginRequest login);
    List<ViewTaskResponse> getAllAssignedTask(LoginRequest login);
}
