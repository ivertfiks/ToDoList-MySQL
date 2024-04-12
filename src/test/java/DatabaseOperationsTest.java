import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;


public class DatabaseOperationsTest {
    DatabaseOperations databaseOperations;
    String tableName;
    @Before
    public void setUp() throws SQLException {
        databaseOperations = DatabaseOperations.getInstance("127.0.0.1:3306", "root", "password");
        tableName = "todo_list_test";
    }

    @Test
    public void addTask_shouldAddTask(){

    }

    @Test
    public void deleteTask_shouldDeleteTask(){

    }

    @Test
    public void updateTask_shouldDeleteTask(){

    }
}
