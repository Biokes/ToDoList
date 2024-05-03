package africa.semoicolon.data.repo;

import africa.semoicolon.data.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task, String>{
    Optional<Task> findTaskByTaskTitleAndUsername(String title,String username);
    Optional<Task> findAllByUsernameIgnoreCaseAndAssignerUsername(String username, String assignerUsername);
    void deleteTaskByUsernameAndTaskTitle(String username, String taskName);
}
