package africa.semoicolon.service.implementations;

import africa.semoicolon.dtos.request.RegisterRequest;
import africa.semoicolon.service.inferaces.TaskService;
import africa.semoicolon.service.inferaces.AppService;
import africa.semoicolon.service.inferaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToDoListAppService implements AppService {
    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;
    public void deleteAll() {
        taskService.deleteAll();
        userService.deleteAll();
    }

    @Override
    public void register(RegisterRequest register) {
       userService.register(register);
    }

}
