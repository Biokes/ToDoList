package africa.semoicolon.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class TaskRepositoryTest{
    @Autowired
    private TaskRepository repo;
    @BeforeEach
    public void deleteAll(){
    repo.deleteAll();
    }
    @Test
    public void createTask_testTaskIsCreated(){}
}