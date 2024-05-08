package africa.semicolon.service.implementations;

import africa.semicolon.data.model.Notifications;
import africa.semicolon.data.model.Task;
import africa.semicolon.data.model.TaskStatus;
import africa.semicolon.data.model.User;
import africa.semicolon.dto.request.*;
import africa.semicolon.dto.response.*;
import africa.semicolon.inferaces.AppService;
import semicolon.dto.request.*;
import semicolon.dto.response.*;
import africa.semicolon.inferaces.TaskService;
import africa.semicolon.inferaces.UserService;
import africa.semicolon.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import africa.semicolon.utils.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Task task = taskService.findTask(startTaskRequest.getUsername(), startTaskRequest.getTaskName());
        notifyUserForNotification(task);
        return response;
    }
    public CompleteTaskResponse completeTask(CompleteTaskRequest completeTaskRequest){
        validateUserInfo(completeTaskRequest.getUsername(),completeTaskRequest.getPassword());
        CompleteTaskResponse response = taskService.completeTask(completeTaskRequest);
        Task task = taskService.findTask(completeTaskRequest.getUsername(), completeTaskRequest.getTaskName());
        notifyUserForNotification(task);
        return response;
    }
    public AssignTaskResponse assignTask(AssignTaskRequest assignTaskRequest){
        Validator.validateAssignTaskRequest(assignTaskRequest);
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
            notification.setNotification(task.getTaskTitle()+" assigned To "+ task.getUsername()+  " is " + task.getStatus());
            notification.setTaskTitle(task.getTaskTitle());
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
        notifications.add(notification);
        user.setNotifications(notifications);
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
        Validator.validateLogin(login);
        userService.validateUserLogin(login);
        return taskService.findAllTask(login.getUsername());
    }
    public List<CompleteTaskResponse> getAllCompleteTask(LoginRequest login){
        Validator.validateLogin(login);
        userService.validateUserLogin(login);
        return taskService.getAllCompleteTasks(login.getUsername());
    }
    public List<ViewTaskResponse> getAllTaskNotCompleted(LoginRequest login){
        Validator.validateLogin(login);
        userService.validateUserLogin(login);
        List<ViewTaskResponse> responses = taskService.findAllTask(login.getUsername());
        List<ViewTaskResponse> output = new ArrayList<>();
        responses.forEach(task->{if(task.getStatus()!= TaskStatus.COMPLETED) output.add(task);});
        return output;
    }
    public List<CreateTaskResponse> getAllPendingTask(LoginRequest login){
        Validator.validateLogin(login);
        userService.validateUserLogin(login);
        return taskService.getAllPendingTasks(login.getUsername());
    }
    public List<ViewTaskResponse> getAllAssignedTask(LoginRequest login) {
        Validator.validateLogin(login);
        userService.validateUserLogin(login);
        List<ViewTaskResponse> responses = taskService.findAllTask(login.getUsername());
        List<ViewTaskResponse> output = new ArrayList<>();
        responses.forEach(task->{if(!task.getAssignerUsername().equalsIgnoreCase("self")) output.add(task);});
        return output;
    }
    public List<Notifier> viewNotifications(LoginRequest login) {
        Validator.validateLogin(login);
        User user = userService.getUser(login);
        return getNotifiers(login, user);
    }
    private List<Notifier> getNotifiers(LoginRequest login, User user){
        List<Task> tasks= taskService.findAllDueTasks(login.getUsername());
        List<Notifications> notifications = Mapper.mapToNotifications(tasks);
        List<Notifications> notes = user.getNotifications();
        notes.addAll(notifications);
        user.setNotifications(notes);
        userService.save(user);
        return Mapper.mapToUserNotification(user.getNotifications());
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