package africa.semoicolon.service;

import africa.semoicolon.model.Task;
import africa.semoicolon.repo.TaskRepository;
import africa.semoicolon.request.CreateTaskRequest;
import africa.semoicolon.response.CreateTaskResponse;
import africa.semoicolon.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Task task = Mapper.mapCreateTaskRequest(request);
        return null;
    }
}
