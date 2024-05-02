package africa.semoicolon.service.implementations;

import africa.semoicolon.dtos.request.CreateTaskRequest;
import africa.semoicolon.dtos.request.DeleteUserRequest;
import africa.semoicolon.dtos.request.RegisterRequest;
import africa.semoicolon.dtos.response.CreateTaskResponse;
import africa.semoicolon.dtos.response.ViewTaskResponse;
import africa.semoicolon.service.inferaces.TaskService;
import africa.semoicolon.service.inferaces.AppService;
import africa.semoicolon.service.inferaces.UserService;
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
    public CreateTaskResponse createTask(CreateTaskRequest createTaskRequest) {
        userService.isValidUsername(createTaskRequest.getUsername());
        return taskService.createTask(createTaskRequest);
    }
    public void deleteAccount(DeleteUserRequest deleteRequest) {

    }

    @Override
    public List<ViewTaskResponse> getAllUserTasks(String username) {
        return null;
    }
}