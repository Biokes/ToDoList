package africa.semicolon.service.implementations;

import africa.semicolon.data.model.User;
import africa.semicolon.data.repo.UserRepository;
import africa.semicolon.dto.request.DeleteUserRequest;
import africa.semicolon.dto.request.LogOut;
import africa.semicolon.dto.request.LoginRequest;
import africa.semicolon.dto.request.RegisterRequest;
import africa.semicolon.exceptions.ToDoListException;
import africa.semicolon.inferaces.UserService;
import africa.semicolon.exceptions.ExceptionMessages;
import africa.semicolon.exceptions.InvalidDetails;
import africa.semicolon.utils.Mapper;
import africa.semicolon.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            throw new InvalidDetails(ExceptionMessages.USER_DOES_NOT_EXIST.getMessage());
        }
        throw new InvalidDetails(ExceptionMessages.USER_DOES_NOT_EXIST.getMessage());
    }
    public void deleteAll(){
        repository.deleteAll();
    }
    public void login(LoginRequest login){
        Validator.validateLogin(login);
        validateLoginDetails(login);
        User user = getUser(login.getUsername());
        user.setLoggedIn(true);
        repository.save(user);
    }
    public void isValidUsername(String username) {
        List<User> users = repository.findAll();
        for(User user : users){if(user.getUsername().equalsIgnoreCase(username))return;}
        throw new ToDoListException(ExceptionMessages.USER_DOES_NOT_EXIST.getMessage());
    }
    public User getUser(LoginRequest login) {
        validateLoginDetails(login);
        for(User user :repository.findAll()){
                if(user.getUsername().equalsIgnoreCase(login.getUsername()) &&
                        user.getPassword().equalsIgnoreCase(login.getPassword()))
                        return user;
        }
        throw new ToDoListException(ExceptionMessages.USER_DOES_NOT_EXIST.getMessage());
    }
    public User getUser(String username){
           Optional<User> user =repository.findAllByUsernameIgnoreCase(username);
           if(user.isPresent())
               return user.get();
           throw new ToDoListException(ExceptionMessages.USER_DOES_NOT_EXIST.getMessage());
    }
    public void logOut(LogOut logout){
        Validator.validateLogOut(logout);
        validateUserExistence(logout.getUsername());
        logUserOut(logout);
    }
    public void validateUserLogin(LoginRequest login) {
        getUser(login);
    }

    private void logUserOut(LogOut logout){
        User user = getUser(logout.getUsername());
        if(!user.getPassword().equalsIgnoreCase(logout.getPassword()))
            throw new InvalidDetails(ExceptionMessages.INVALID_DETAILS.getMessage());
        user.setLoggedIn(false);
    }
    private void validateLoginDetails(LoginRequest login){
        Validator.validateLogin(login);
        List<User> allUsers = repository.findAll();
        for(User user: allUsers)
            if(user.getUsername().equalsIgnoreCase(login.getUsername())&&user.getPassword().equalsIgnoreCase(login.getPassword()))
                return;
        throw new ToDoListException(ExceptionMessages.USER_DOES_NOT_EXIST.getMessage());
    }
    private void validateUserExistence(String username){
        List<User> allUsers = repository.findAll();
        allUsers.forEach(user->{if(user.getUsername().equalsIgnoreCase(username)){
            throw new ToDoListException(ExceptionMessages.USER_ALREADY_EXIST.getMessage());}});
    }

}
