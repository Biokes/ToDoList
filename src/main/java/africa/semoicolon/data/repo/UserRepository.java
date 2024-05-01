package africa.semoicolon.data.repo;

import africa.semoicolon.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
