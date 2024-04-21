package africa.semoicolon.repo;

import africa.semoicolon.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task, String>{
    Optional<Task> findTaskByTaskTitleAndUsername(String title,String username);

    void deleteTaskByUsernameAndTaskTitle(String username, String taskName);
}
