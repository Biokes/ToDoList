package africa.semicolon.controller;


import africa.semicolon.dto.request.*;
import africa.semicolon.dto.response.ApiResponse;
import africa.semicolon.exceptions.ToDoListException;
import africa.semicolon.inferaces.AppService;
import semicolon.dto.request.*;
import org.springframework.http.HttpStatus;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/toDoList")
public class ToDoListController{
    @Autowired
    private AppService appService;
    @PostMapping("/Register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        try{
            appService.register(request);
            return new ResponseEntity<>(new ApiResponse(true, "Registered successfully"), HttpStatus.CREATED);
        }
        catch(ToDoListException exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/Delete_account")
    public ResponseEntity<?> deActivateAccount(@RequestBody DeleteUserRequest delete){
        try{
            appService.deleteAccount(delete);
            return new ResponseEntity<>(new ApiResponse(true,delete.getUsername()+"deleted successfully"), HttpStatus.OK);
        }
        catch(ToDoListException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);

        }
    }
    @PutMapping("/Create-task")
    public ResponseEntity<?> createTask(@RequestBody CreateTaskRequest create){
        try{
            return new ResponseEntity<>(new ApiResponse(true,appService.createTask(create)), HttpStatus.CREATED);
        }
        catch(ToDoListException exception){
            return new ResponseEntity<>(new ApiResponse(false, exception.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete_task")
    public ResponseEntity<?> deleteTask(@RequestBody DeleteTaskRequest delete){
        try{
            appService.deleteTask(delete);
            return new ResponseEntity<>((new ApiResponse(false,delete.getTaskName()+" deleted successfully.")), HttpStatus.OK);
        }
        catch(ToDoListException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/update-task")
    public ResponseEntity<?> updateTask(@RequestBody UpdateTaskRequest update){
        try{
            return new ResponseEntity<>(new ApiResponse(true,appService.updateTask(update)), HttpStatus.OK);
        }
        catch(ToDoListException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/Complete-Task")
    public ResponseEntity<?> completeTask(@RequestBody CompleteTaskRequest complete){
        try{
           return new ResponseEntity<>(new ApiResponse(true,appService.completeTask(complete)), HttpStatus.OK);
        }
        catch(ToDoListException error){
            return new ResponseEntity<>(new ApiResponse(false,error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/assign-Task")
    public ResponseEntity<?> assignTask(@RequestBody AssignTaskRequest assign){
        try{
            return new ResponseEntity<>(new ApiResponse(true,appService.assignTask(assign)), HttpStatus.CREATED);
        }
        catch(ToDoListException error){
            return new ResponseEntity<>(new ApiResponse(false,error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/logIn")
    public ResponseEntity<?> login(@RequestBody LoginRequest login){
        try{
            return new ResponseEntity<>(new ApiResponse(true,appService.login(login)), HttpStatus.OK);
        }
        catch(ToDoListException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/notifications")
    public ResponseEntity<?> notifications(@RequestBody LoginRequest login){
        try{
            return new ResponseEntity<>(new ApiResponse(true,appService.login(login)), HttpStatus.OK);
        }
        catch(ToDoListException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/created-task-list")
    public ResponseEntity<?> checkAllTaskCreated(@RequestBody LoginRequest login){
        try{
            return new ResponseEntity<>(new ApiResponse(true,appService.findAllTask(login)), HttpStatus.OK);
        }
        catch(ToDoListException error){
            return new ResponseEntity<>(new ApiResponse(false,error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/checkPendingTask")
    public ResponseEntity<?> checkPendingTasks(@RequestBody LoginRequest login){
        try{
            return new ResponseEntity<>(new ApiResponse(true,appService.getAllPendingTask(login)), HttpStatus.OK);
        }
        catch(ToDoListException error){
            return new ResponseEntity<>(new ApiResponse(false,error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/check-completed-tasks")
    public ResponseEntity<?> checkCompletedTasks(@RequestBody LoginRequest login){
        try{
            return new ResponseEntity<>(new ApiResponse(true, appService.getAllCompleteTask(login)), HttpStatus.OK);
        }
        catch(ToDoListException error){
            return new ResponseEntity<>(new ApiResponse(false,error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/get-all-assigned-tasks")
    public ResponseEntity<?> getAllAssignedTasks(@RequestBody LoginRequest login){
        try{
            return new ResponseEntity<>(new ApiResponse(true,appService.getAllAssignedTask(login)), HttpStatus.OK);
        }
        catch(ToDoListException error){
            return new ResponseEntity<>(new ApiResponse(false,error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
// get notifications from due tasks not completed