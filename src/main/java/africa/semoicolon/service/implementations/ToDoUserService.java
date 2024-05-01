package africa.semoicolon.service.implementations;

import africa.semoicolon.data.model.User;
import africa.semoicolon.data.repo.UserRepository;
import africa.semoicolon.dtos.request.RegisterRequest;
import africa.semoicolon.service.inferaces.UserService;
import africa.semoicolon.utils.Mapper;
import africa.semoicolon.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToDoUserService implements UserService {
    @Autowired
    private UserRepository repository;
    public void register(RegisterRequest request) {
        Validator.validateRegister(request);
        User user = Mapper.mapToUser(request);
        repository.save(user);
    }
    public long countAllUsers() {
        return repository.count();
    }
}
