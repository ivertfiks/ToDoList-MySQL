package org.todolist.service.database;

import org.todolist.model.Task;
import org.todolist.model.enum_model.TaskStatus;
import org.todolist.service.TaskService;
import java.sql.SQLException;
import java.util.List;

public class DatabaseService implements TaskService {

    private final DatabaseOperations databaseOperations;

    public DatabaseService() {
        try {
            this.databaseOperations = DatabaseOperations.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addTask(Task task) {
        try {
            databaseOperations.addTask(task.getTask(), task.getTaskPriority().getPriority(), task.getTaskStatus().getStatus());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTaskById(int id) {
        try {
            databaseOperations.deleteTask(DatabaseQueries.TABLE_NAME, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Task> getAllTasks() {
        try {
            return databaseOperations.convertTaskInList(DatabaseQueries.TABLE_NAME);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void changeTaskStatus(int id, String status) {
        try {
            databaseOperations.updateTask(DatabaseQueries.TABLE_NAME, id, TaskStatus.valueOf(status));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
