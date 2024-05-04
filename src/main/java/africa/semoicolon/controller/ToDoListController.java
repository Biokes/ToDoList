package africa.semoicolon.controller;


import africa.semoicolon.dtos.request.RegisterRequest;
import africa.semoicolon.dtos.response.ApiResponse;
import africa.semoicolon.exceptions.ToDoListException;
import africa.semoicolon.service.inferaces.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v1/toDoList/")
public class ToDoListController{
    @Autowired
    private AppService appService;
    @PostMapping()
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        try{
            appService.register(request);
            return new ResponseEntity<>(new ApiResponse(true, "Registered successfully"), CREATED);
        }
        catch(ToDoListException exception){
            return new ResponseEntity<>(new ApiResponse(false,exception.getMessage()), BAD_REQUEST);
        }
    }
}
//register user
// delete user
//create task
//delete task
//update task
// complete task
// assign task
//notify user after loggin in
// view notifications
//find all created tasks
// find all pending tasks
//find all completed
// find all assigned task
// get notifications from due tasks not completed
// view notifications