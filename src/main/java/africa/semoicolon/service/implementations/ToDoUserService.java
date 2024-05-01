package africa.semoicolon.service.implementations;

import africa.semoicolon.data.model.User;
import africa.semoicolon.data.repo.UserRepository;
import africa.semoicolon.dtos.request.DeleteUserRequest;
import africa.semoicolon.dtos.request.LoginRequest;
import africa.semoicolon.dtos.request.RegisterRequest;
import africa.semoicolon.exceptions.InvalidDetails;
import africa.semoicolon.exceptions.ToDoListException;
import africa.semoicolon.service.inferaces.UserService;
import africa.semoicolon.utils.Mapper;
import africa.semoicolon.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static africa.semoicolon.exceptions.ExceptionMessages.USER_ALREADY_EXIST;
import static africa.semoicolon.exceptions.ExceptionMessages.USER_DOES_NOT_EXIST;

@Service
public class ToDoUserService implements UserService {
    @Autowired
    private UserRepository repository;
    public void register(RegisterRequest request) {
        Validator.validateRegister(request);
        validateUserExistence(request.getUsername());
        User user = Mapper.mapToUser(request);
        repository.save(user);
    }
    public long countAllUsers(){
        return repository.count();
    }
    public void deleteUser(DeleteUserRequest delete){
        Validator.validateDeleteUer(delete);
        List<User> users = repository.findAll();
        for(User user : users){
            if(user.getUsername().equalsIgnoreCase(delete.getUsername()))
                    if(user.getPassword().equalsIgnoreCase(delete.getPassword())) {
                        repository.delete(user);
                        return;
                    }
            throw new InvalidDetails(USER_DOES_NOT_EXIST.getMessage());
        }
        throw new InvalidDetails(USER_DOES_NOT_EXIST.getMessage());
    }
    public void deleteAll(){
        repository.deleteAll();
    }
    public void login(LoginRequest login){
        Validator.validateLogin(login);
        validateLoginDetails(login);
    }
    public void validatUserExistence(String username) {
        List<User> users = repository.findAll();
        users.forEach(user-> {if(user.getUsername().equalsIgnoreCase(username))return;});
        throw new ToDoListException(USER_DOES_NOT_EXIST.getMessage());
    }

    private void validateLoginDetails(LoginRequest login){
        List<User> allUsers = repository.findAll();
        for(User user: allUsers)
            if(user.getUsername().equalsIgnoreCase(login.getUsername())&&user.getPassword().equalsIgnoreCase(login.getPassword()))
                return;
        throw new ToDoListException(USER_DOES_NOT_EXIST.getMessage());
    }
    private void validateUserExistence(String username){
        List<User> allUsers = repository.findAll();
        allUsers.forEach(user->{if(user.getUsername().equalsIgnoreCase(username)){
            throw new ToDoListException(USER_ALREADY_EXIST.getMessage());}});
    }

}
