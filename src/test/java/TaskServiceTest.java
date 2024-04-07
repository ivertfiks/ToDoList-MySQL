import org.junit.Before;
import org.junit.Test;
import org.todolist.model.Task;
import org.todolist.model.enum_model.TaskPriority;
import org.todolist.model.enum_model.TaskStatus;
import org.todolist.service.TaskService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TaskServiceTest {
    TaskService taskService;
    Task task;

    @Before
    public void setUp(){
        taskService = new TaskService();
        task = new Task("Test task");
    }
    @Test
    public void addTask_shouldAddTask(){
        taskService.addTask(task);
        assertTrue(taskService.getTaskList().contains(task));
    }

    @Test
    public void removeTask_shouldRemoveTask(){
        taskService.addTask(task);
        taskService.removeTask(task);
        assertTrue(taskService.getTaskList().isEmpty());
    }
    @Test
    public void getList_shouldReturnList(){
        taskService.addTask(task);
        taskService.addTask(new Task(2,"test", TaskPriority.HIGH, TaskStatus.PENDING));
        List<Task> list = new ArrayList<>();
        list.add(task);
        list.add(new Task(2,"test", TaskPriority.HIGH, TaskStatus.PENDING));
        assertEquals(taskService.getTaskList(), list);
    }
    @Test
    public void getList_shouldReturnEmptyList(){
        taskService.addTask(task);
        taskService.removeTask(task);
        assertTrue(taskService.getTaskList().isEmpty());
    }
}
