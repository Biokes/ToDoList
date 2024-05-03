package africa.semoicolon.service.implementations;

import africa.semoicolon.data.model.User;
import africa.semoicolon.data.repo.UserRepository;
import africa.semoicolon.dtos.request.DeleteUserRequest;
import africa.semoicolon.dtos.request.LogOut;
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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static africa.semoicolon.exceptions.ExceptionMessages.*;

@Service
public class ToDoUserService implements UserService {
    @Autowired
    private UserRepository repository;
    public void save(User user){
        repository.save(user);
    }
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
    public void isValidUsername(String username) {
        List<User> users = repository.findAll();
        for(User user : users){if(user.getUsername().equalsIgnoreCase(username))return;}
        throw new ToDoListException(USER_DOES_NOT_EXIST.getMessage());
    }
    public User getUser(LoginRequest login) {
        validateLoginDetails(login);
        for(User user :repository.findAll()){
                if(user.getUsername().equalsIgnoreCase(login.getUsername()) &&
                        user.getPassword().equalsIgnoreCase(login.getPassword()))
                        return user;
        }
        throw new ToDoListException(USER_DOES_NOT_EXIST.getMessage());
    }
    public User getUser(String username){
           Optional<User> user =repository.findAllByUsernameIgnoreCase(username);
           if(user.isPresent())
               return user.get();
           throw new ToDoListException(USER_DOES_NOT_EXIST.getMessage());
    }
    public void logOut(LogOut logout){
        Validator.validateLogOut(logout);
        validateUserExistence(logout.getUsername());
        logUserOut(logout);
    }
    private void logUserOut(LogOut logout){
        User user = getUser(logout.getUsername());
        if(!user.getPassword().equalsIgnoreCase(logout.getPassword()))
            throw new InvalidDetails(INVALID_DETAILS.getMessage());
        user.setLoggedIn(false);
    }
    private void validateLoginDetails(LoginRequest login){
        Validator.validateLogin(login);
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
