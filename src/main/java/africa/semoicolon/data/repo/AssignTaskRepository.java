package africa.semoicolon.data.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface AssignTaskRepository extends MongoRepository<AssignedTask, String> {
}
