package africa.semoicolon.service.implementations;

import africa.semoicolon.dtos.request.*;
import africa.semoicolon.dtos.response.*;
import africa.semoicolon.service.inferaces.TaskService;
import africa.semoicolon.service.inferaces.AppService;
import africa.semoicolon.service.inferaces.UserService;
import africa.semoicolon.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToDoListAppService implements AppService {
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;
    public void deleteAll() {
        taskService.deleteAll();
        userService.deleteAll();
    }
    public void register(RegisterRequest register){
       userService.register(register);
    }
    public long countAllUsers(){
        return userService.countAllUsers();
    }
    public CreateTaskResponse createTask(CreateTaskRequest createTaskRequest){
        userService.isValidUsername(createTaskRequest.getUsername());
        return taskService.createTask(createTaskRequest);
    }
    public void deleteAccount(DeleteUserRequest deleteRequest){
        userService.deleteUser(deleteRequest);
    }
    public long countAllUserTask(String username){
        userService.isValidUsername(username);
        return taskService.countTaskByUsername(username);
    }
    public void deleteTask(DeleteTaskRequest deleteTaskRequest){
        userService.isValidUsername(deleteTaskRequest.getUsername());
        validateUserInfo(deleteTaskRequest.getUsername(),deleteTaskRequest.getPassword());
        taskService.deleteTaskWith(deleteTaskRequest);
    }
    public UpdateTaskResponse updateTask(UpdateTaskRequest updateTask) {
        validateUserInfo(updateTask.getUsername(), updateTask.getPassword());
        return taskService.updateTask(updateTask);
    }
    public StartTaskResponse startTask(StartTaskRequest startTaskRequest) {
        validateUserInfo(startTaskRequest.getUsername(), startTaskRequest.getPassword());
        return taskService.startTaskWith(startTaskRequest);
    }
    public CompleteTaskResponse completeTask(CompleteTaskRequest completeTaskRequest) {
        validateUserInfo(completeTaskRequest.getUsername(),completeTaskRequest.getPassword());
        return taskService.completeTask(completeTaskRequest);
    }
    public AssignTaskResponse assignTask(AssignTaskRequest assignTaskRequest){
        Validator.validateAssignTaskRequest(assignTaskRequest);
        validateUserInfo(assignTaskRequest.getAssignerUsername(), assignTaskRequest.getPassword());
        userService.isValidUsername(assignTaskRequest.getAssigneeUsername());
        notifyUserForNotification(assignTaskRequest.getAssigneeUsername());
        return taskService.assignTask(assignTaskRequest);
    }
    private void notifyUserForNotification(String assigneeUsername) {
        userService.getUser(assigneeUsername);
    }

    public LoginResponse login(LoginRequest login){
        validateUserInfo(login.getUsername(),login.getPassword());
        userService.getUser(login);
        return null;
    }
    private void validateUserInfo(String username, String password){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(password);
        loginRequest.setUsername(username);
        userService.login(loginRequest);
    }
}
