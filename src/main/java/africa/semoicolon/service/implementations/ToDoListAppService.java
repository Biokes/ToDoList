package africa.semoicolon.service.implementations;

import africa.semoicolon.data.model.Notifications;
import africa.semoicolon.data.model.Task;
import africa.semoicolon.data.model.User;
import africa.semoicolon.dtos.request.*;
import africa.semoicolon.dtos.response.*;
import africa.semoicolon.service.inferaces.TaskService;
import africa.semoicolon.service.inferaces.AppService;
import africa.semoicolon.service.inferaces.UserService;
import africa.semoicolon.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static africa.semoicolon.utils.Validator.validateAssignTaskRequest;
import static africa.semoicolon.utils.Validator.validateLogin;

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
    public UpdateTaskResponse updateTask(UpdateTaskRequest updateTask){
        validateUserInfo(updateTask.getUsername(), updateTask.getPassword());
        UpdateTaskResponse response = taskService.updateTask(updateTask);
        Task task = taskService.findTask(updateTask.getUsername(),response.getTaskTitle());
        notifyUserForNotification(task);
        return response;
    }
    public StartTaskResponse startTask(StartTaskRequest startTaskRequest){
        validateUserInfo(startTaskRequest.getUsername(), startTaskRequest.getPassword());
        StartTaskResponse response = taskService.startTaskWith(startTaskRequest);
        Task task = taskService.findTask(response.getUsername(), response.getTaskTitle());
        notifyUserForNotification(task);
        return response;
    }
    public CompleteTaskResponse completeTask(CompleteTaskRequest completeTaskRequest){
        validateUserInfo(completeTaskRequest.getUsername(),completeTaskRequest.getPassword());
        CompleteTaskResponse response = taskService.completeTask(completeTaskRequest);
        Task task = taskService.findTask(response.getUsername(), response.getTaskName());
        notifyUserForNotification(task);
        return response;
    }
    public AssignTaskResponse assignTask(AssignTaskRequest assignTaskRequest){
        validateAssignTaskRequest(assignTaskRequest);
        validateUserInfo(assignTaskRequest.getAssignerUsername(), assignTaskRequest.getPassword());
        taskService.checkTaskExistence(assignTaskRequest);
        userService.isValidUsername(assignTaskRequest.getAssigneeUsername());
        AssignTaskResponse response = taskService.assignTask(assignTaskRequest);
        notifyUserForNotification(assignTaskRequest);
        return response;
    }
    private void notifyUserForNotification(AssignTaskRequest assignTaskRequest){
       User user =  userService.getUser(assignTaskRequest.getAssigneeUsername());
       extracted(assignTaskRequest, user);
    }
    private void notifyUserForNotification(Task task){
        if(Optional.ofNullable(task.getAssignerUsername()).isPresent()&&
           !Optional.of(task.getAssignerUsername()).get().equalsIgnoreCase("self")) {
            User user = userService.getUser(task.getAssignerUsername());
            Notifications notification = new Notifications();
            notification.setNotification(task.getTaskTitle()+" assigned To "+ task.getUsername()+  "is " + task.getStatus());
            List<Notifications> notifications= user.getNotifications();
            notifications.add(notification);
            user.setNotifications(notifications);
            userService.save(user);
        }
    }
    private void extracted(AssignTaskRequest assignTaskRequest, User user){
        Notifications notification = Mapper.mapAssignTaskToNotification(assignTaskRequest);
        List<Notifications> notifications = user.getNotifications();
        if(notifications.isEmpty()){
            extracted(user, notifications, notification);
            return;
        }
        user.setNotifications(new ArrayList<>());
        userService.save(user);
    }
    public LoginResponse login(LoginRequest login){
        userService.login(login);
        User user = userService.getUser(login);
        List<Notifications> box = user.getNotifications();
        box.removeIf(Notifications::isSeen);
        user.getNotifications().forEach(note->{note.setSeen(true);});
        user.setNotifications(box);
        userService.save(user);
        return Mapper.mapUserToLogInResponse(user);
    }
    public void logOut(LogOut logout){
        userService.logOut(logout);
    }
    public List<ViewTaskResponse> findAllTask(LoginRequest login){
        validateLogin(login);
        userService.validateUserLogin(login);
        return taskService.findAllTask(login.getUsername());
    }
    public List<CompleteTaskResponse> getAllCompleteTask(LoginRequest login){
        validateLogin(login);
        userService.validateUserLogin(login);
        return taskService.getAllCompleteTasks(login.getUsername());
    }
    public List<ViewTaskResponse> getAllTaskNotCompleted(LoginRequest login){
        validateLogin(login);
        userService.validateUserLogin(login);
        return null;
    }
    public List<CreateTaskResponse> getAllPendingTask(LoginRequest login){
        validateLogin(login);
        userService.validateUserLogin(login);
        return taskService.getAllPendingTasks(login.getUsername());
    }
    @Override
    public List<ViewTaskResponse> getAllAssignedTask(LoginRequest login) {
        validateLogin(login);
        userService.validateUserLogin(login);
        return null;
    }

    private void extracted(User user, List<Notifications> notifications, Notifications notification) {
        notifications.add(notification);
        user.setNotifications(notifications);
        userService.save(user);
    }
    private void validateUserInfo(String username, String password){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword(password);
        loginRequest.setUsername(username);
        userService.login(loginRequest);
    }
}