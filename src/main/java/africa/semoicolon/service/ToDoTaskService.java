package africa.semoicolon.service;

import africa.semoicolon.repo.TaskRepository;
import africa.semoicolon.response.CreateTaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToDoTaskService implements TaskService{
    @Autowired
    private TaskRepository repository;
    public void deleteAll(){
        repository.deleteAll();
    }
    public CreateTaskResponse createTask(){
        return null;
    }
}
