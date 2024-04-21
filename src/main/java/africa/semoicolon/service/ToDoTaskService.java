package africa.semoicolon.service;

import africa.semoicolon.exceptions.*;
import africa.semoicolon.model.Task;
import africa.semoicolon.repo.TaskRepository;
import africa.semoicolon.request.*;
import africa.semoicolon.response.CompleteTaskResponse;
import africa.semoicolon.response.CreateTaskResponse;
import africa.semoicolon.response.StartTaskResponse;
import africa.semoicolon.response.UpdateTaskResponse;
import africa.semoicolon.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static africa.semoicolon.exceptions.ExceptionMessages.*;
import static africa.semoicolon.model.TaskStatus.COMPLETED;
import static africa.semoicolon.model.TaskStatus.IN_PROGRESS;
import static africa.semoicolon.utils.Validator.*;

@Service
public class ToDoTaskService implements TaskService{
    @Autowired
    private TaskRepository repository;
    public void deleteAll(){
        repository.deleteAll();
    }
    public CreateTaskResponse createTask(CreateTaskRequest request){
        validateCreateTaskRequest(request);
        if(isExistingTask(request.getTaskTitle(),request.getUsername()))
            throw new TaskExistsException(TASK_EXISTS.getMessage());
        Task task = Mapper.mapCreateTaskRequestToTask(request);
        task = repository.save(task);
        return Mapper.mapTaskToResponse(task);
    }
    public StartTaskResponse startTaskWith(StartTaskRequest startRequest){
        validateStartTAskRequest(startRequest);
        Optional<Task> taskFound = repository.findTaskByTaskTitleAndUsername(startRequest.getTaskName(),startRequest.getUsername());
        validateTask(taskFound);
        taskFound.get().setStatus(IN_PROGRESS);
        taskFound.get().setDateStarted(taskFound.get()
                                               .getStatus()
                                               .getDate());
        return Mapper.mapTaskToStartTaskResponse(repository.save(taskFound.get()));
    }
    public CompleteTaskResponse completeTask(CompleteTaskRequest complete){
        validateCompleteTaskRequest(complete);
        if(!isExistingTask(complete.getTaskName( ),complete.getUsername()))
            throw new TaskNotFoundException(TASK_NOT_FOUND.getMessage( ));
        if(!isStartedTask(complete.getTaskName(),complete.getUsername()))
            throw new TaskNotStartedException(TASK_NOT_STARTED.getMessage());
        return Mapper.mapToCompleteTaskResponse(complete(complete));
    }
    public void deleteTaskWith(DeleteTaskRequest delete){
        validateDeleteTaskRequest(delete);
        if(!isExistingTask(delete.getTaskName(), delete.getUsername()))
            throw new ToDoListException(TASK_NOT_FOUND.getMessage());
        repository.deleteTaskByUsernameAndTaskTitle(delete.getUsername(),delete.getTaskName());
    }

    @Override
    public UpdateTaskResponse updateTask(UpdateTaskRequest update) {
        return null;
    }

    private void checkTaskExistence(Optional<Task> taskFound){
        if(taskFound.isEmpty())
            throw new TaskNotFoundException(TASK_NOT_FOUND.getMessage());
    }
    private void validateTask(Optional<Task> taskFound){
        checkTaskExistence(taskFound);
        if (taskFound.get().getStatus() == IN_PROGRESS)
            throw new TaskStartedException(TASK_STARTED.getMessage());
    }
    private Task complete(CompleteTaskRequest complete){
        Optional<Task> task = repository.findTaskByTaskTitleAndUsername(complete.getTaskName( ), complete.getUsername( ));
        task.get().setStatus(COMPLETED);
        return repository.save(task.get());
    }
    private boolean isStartedTask(String taskName, String username){
        Optional<Task> task=repository.findTaskByTaskTitleAndUsername(taskName, username);
        return task.get().getStatus()==IN_PROGRESS;
    }
    private boolean isExistingTask(String title,String username){
        List<Task> tasks = repository.findAll();
        for(Task task : tasks){
            if(task.getTaskTitle().equalsIgnoreCase(title)&&task.getUsername().equalsIgnoreCase(username))
               return true;
        }
        return false;
    }
}
