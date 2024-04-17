package africa.semoicolon.repo;

import africa.semoicolon.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String>{

}
