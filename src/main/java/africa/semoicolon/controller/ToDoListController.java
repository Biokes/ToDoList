package africa.semoicolon.controller;


import africa.semoicolon.dtos.request.*;
import africa.semoicolon.dtos.response.ApiResponse;
import africa.semoicolon.exceptions.ToDoListException;
import africa.semoicolon.service.inferaces.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/v1/toDoList/")
public class ToDoListController{
    @Autowired
    private AppService appService;
    @PostMapping("/Register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        try{
            appService.register(request);
            return new ResponseEntity<>(new ApiResponse(true, "Registered successfully"), CREATED);
        }
        catch(ToDoListException exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()), BAD_REQUEST);
        }
    }
    @DeleteMapping("/Delete_account")
    public ResponseEntity<?> deActivateAccount(@RequestBody DeleteUserRequest delete){
        try{
            appService.deleteAccount(delete);
            return new ResponseEntity<>(new ApiResponse(true,delete.getUsername()+"deleted successfully"),OK);
        }
        catch(ToDoListException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()),BAD_REQUEST);

        }
    }
    @PutMapping("/Create-task")
    public ResponseEntity<?> createTask(@RequestBody CreateTaskRequest create){
        try{
            return new ResponseEntity<>(new ApiResponse(true,appService.createTask(create)), OK);
        }
        catch(ToDoListException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()),BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete_task")
    public ResponseEntity<?> deleteTask(@RequestBody DeleteTaskRequest delete){
        try{
            appService.deleteTask(delete);
            return new ResponseEntity<>((new ApiResponse(false,delete.getTaskName()+" deleted successfully.")),OK);
        }
        catch(ToDoListException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()),BAD_REQUEST);
        }
    }
    @PatchMapping("/update-task")
    public ResponseEntity<?> updateTask(@RequestBody UpdateTaskRequest update){
        try{
            return new ResponseEntity<>(new ApiResponse(true,appService.updateTask(update)),OK);
        }
        catch(ToDoListException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()),BAD_REQUEST);
        }
    }
    @PatchMapping("/Complete-Task")
    public ResponseEntity<?> completeTask(@RequestBody CompleteTaskRequest complete){
        try{
           return new ResponseEntity<>(new ApiResponse(true,appService.completeTask(complete)),OK);
        }
        catch(ToDoListException error){
            return new ResponseEntity<>(new ApiResponse(false,error.getMessage()),BAD_REQUEST);
        }
    }
    @PostMapping("/assign-Task")
    public ResponseEntity<?> assignTask(@RequestBody AssignTaskRequest assign){
        try{
            return new ResponseEntity<>(new ApiResponse(true,appService.assignTask(assign)),OK);
        }
        catch(ToDoListException error){
            return new ResponseEntity<>(new ApiResponse(false,error.getMessage()),BAD_REQUEST);
        }
    }
    @GetMapping("/logIn")
    public ResponseEntity<?> login(@RequestBody LoginRequest login){
        try{
            return new ResponseEntity<>(new ApiResponse(true,appService.login(login)),OK);
        }
        catch(ToDoListException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()),BAD_REQUEST);
        }
    }
    @GetMapping("/notifications")
    public ResponseEntity<?> notifications(@RequestBody LoginRequest request){
        try{
            return new ResponseEntity<>(new ApiResponse(true,appService.viewNotifications(request)),OK);
        }
        catch(ToDoListException error){
            return new ResponseEntity<>(new ApiResponse(false,error.getMessage()),BAD_REQUEST);
        }
    }
}
// view notifications
//find all created tasks
// find all pending tasks
//find all completed
// find all assigned task
// get notifications from due tasks not completed
// view notifications