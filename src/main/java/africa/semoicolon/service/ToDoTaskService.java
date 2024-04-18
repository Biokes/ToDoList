package africa.semoicolon.service;

import africa.semoicolon.exceptions.TaskExistsException;
import africa.semoicolon.exceptions.TaskNotFoundException;
import africa.semoicolon.exceptions.TaskStartedException;
import africa.semoicolon.model.Task;
import africa.semoicolon.repo.TaskRepository;
import africa.semoicolon.request.CompleteTaskRequest;
import africa.semoicolon.request.CreateTaskRequest;
import africa.semoicolon.request.StartTaskRequest;
import africa.semoicolon.response.CompleteTaskResponse;
import africa.semoicolon.response.CreateTaskResponse;
import africa.semoicolon.response.StartTaskResponse;
import africa.semoicolon.utils.Mapper;
import africa.semoicolon.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static africa.semoicolon.exceptions.ExceptionMessages.*;
import static africa.semoicolon.model.TaskStatus.IN_PROGRESS;
import static africa.semoicolon.utils.Validator.validateCompleteTaskRequest;
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
        Validator.validateStartTAskRequest(startRequest);
        Optional<Task> taskFound = repository.findTaskByTaskTitleAndUsername(
                startRequest.getTaskName(),startRequest.getUsername());
        if(taskFound.isEmpty())
            throw new TaskNotFoundException(TASK_NOT_FOUND.getMessage());
        if (taskFound.get().getStatus() == IN_PROGRESS)
            throw new TaskStartedException(TASK_STARTED.getMessage());
        taskFound.get().setStatus(IN_PROGRESS);
        return Mapper.mapTaskToStartTaskResponse(repository.save(taskFound.get()));
    }
    public CompleteTaskResponse completeTask(CompleteTaskRequest complete){
        validateCompleteTaskRequest(complete);
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
