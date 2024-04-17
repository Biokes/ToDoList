package africa.semoicolon.service;

import africa.semoicolon.exceptions.TaskExistsException;
import africa.semoicolon.model.Task;
import africa.semoicolon.repo.TaskRepository;
import africa.semoicolon.request.CreateTaskRequest;
import africa.semoicolon.request.StartTaskRequest;
import africa.semoicolon.response.CreateTaskResponse;
import africa.semoicolon.response.StartTaskResponse;
import africa.semoicolon.utils.Mapper;
import africa.semoicolon.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static africa.semoicolon.exceptions.ExceptionMessages.TASK_EXISTS;
import static africa.semoicolon.utils.Validator.validateCreateTaskRequest;

@Service
public class ToDoTaskService implements TaskService{
    @Autowired
    private TaskRepository repository;
    public void deleteAll(){
        repository.deleteAll();
    }
    public CreateTaskResponse createTask(CreateTaskRequest request){
        validateCreateTaskRequest(request);
        checkExistingTask(request.getTaskTitle(),request.getUsername());
        Task task = Mapper.mapCreateTaskRequest(request);
        task = repository.save(task);
        return Mapper.mapTaskToRequest(task);
    }
    public StartTaskResponse startTaskWith(StartTaskRequest startRequest){
        checkExistingTask(startRequest.getTaskName(), startRequest.getUsername());
        repository.findTaskByTaskTitleAndUsername(start){}
        return null;
    }

    private void checkExistingTask(String title,String username){
        List<Task> tasks = repository.findAll();
        for(Task task : tasks){
            if(task.getTaskTitle().equalsIgnoreCase(title)&&task.getUsername().equalsIgnoreCase(username))
                throw new TaskExistsException(TASK_EXISTS.getMessage());
        }

    }
}
