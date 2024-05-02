package africa.semoicolon.data.repo;

import africa.semoicolon.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findAllByUsernameIgnoreCase(String username);
}
