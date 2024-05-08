package africa.semicolon.inferaces;

import africa.semicolon.data.model.Task;
import africa.semicolon.dto.request.*;
import africa.semicolon.dto.response.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService{
    void deleteAll();
    CreateTaskResponse createTask(CreateTaskRequest request);
    StartTaskResponse startTaskWith(StartTaskRequest startRequest);
    CompleteTaskResponse completeTask(CompleteTaskRequest complete);
    void deleteTaskWith(DeleteTaskRequest delete);
    UpdateTaskResponse updateTask(UpdateTaskRequest update);
    AssignTaskResponse assignTask(AssignTaskRequest assign);
    long countTaskByUsername(String username);
    void deleteTasksByUsername(String username);
    List<CreateTaskResponse> getAllPendingTasks(String username);
    List<StartTaskResponse> getAllTasksInProgress(String username);
    List<CompleteTaskResponse> getAllCompleteTasks(String username);
    List<AssignedTasksResponse> getallAssignedTasks(String boss);
    void checkTaskExistence(AssignTaskRequest request);
    Task findTask(String username, String taskName);
    List<ViewTaskResponse> findAllTask(String username);
    List<Task> findAllDueTasks(String username);
}
