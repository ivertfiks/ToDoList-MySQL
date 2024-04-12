package org.todolist.service.database;

import org.todolist.exceptions.BusinessException;
import org.todolist.exceptions.enum_exception.BusinessExceptionReason;
import org.todolist.model.Task;
import org.todolist.model.enum_model.TaskPriority;
import org.todolist.model.enum_model.TaskStatus;
import org.todolist.service.TaskService;
import org.todolist.service.files.TaskListService;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;

public class DatabaseOperations {
    private static TaskListService taskListService;
    private static DatabaseOperations instance;
    private static Connection connection;
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;


    private DatabaseOperations() throws SQLException {
        DatabaseOperations.taskListService = new TaskListService();
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(".env"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        DB_URL = props.getProperty("DB_URL");
        DB_USER = props.getProperty("DB_USER");
        DB_PASSWORD = props.getProperty("DB_PASSWORD");
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connection success");
        } catch (SQLException e) {
            throw new BusinessException(BusinessExceptionReason.FAILED_DATABASE_CONNECTION_EXCEPTION.getCommandName());
        }
    }

    public static DatabaseOperations getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseOperations();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void addTask(String description, String taskPriority, String taskStatus) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DatabaseQueries.INSERT_INTO_TABLE)) {
            statement.setString(1, description);
            statement.setString(2, taskPriority);
            statement.setString(3, taskStatus);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException(BusinessExceptionReason.SQL_COMMAND_ADD_TASK_EXCEPTION.getCommandName());
        }
    }

    public void deleteTask(String tableName, int taskId) throws SQLException {
        String sqlCommand = DatabaseQueries.DELETE_FROM_TABLE_ID;
        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setInt(1, taskId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException(BusinessExceptionReason.SQL_COMMAND_DELETE_TASK_EXCEPTION.getCommandName());
        }
        updateIdAfterTaskDelete(tableName, taskId);
        autoIncrementUpdate(tableName);
    }

    private void updateIdAfterTaskDelete(String tableName, int taskId) throws SQLException {
        String sqlCommand = DatabaseQueries.UPDATE_AFTER_DELETE;
        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setInt(1, taskId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException(BusinessExceptionReason.SQL_UPDATE_TASK_EXCEPTION.getCommandName());
        }
    }

    private void autoIncrementUpdate(String tableName) throws SQLException {
        String sqlCommand = DatabaseQueries.SELECT_COUNT_FROM_TABLE;
        int rowCount;
        try (PreparedStatement countStatement = connection.prepareStatement(sqlCommand)) {
            try (ResultSet resultSet = countStatement.executeQuery()) {
                if (resultSet.next()) {
                    rowCount = resultSet.getInt(1);
                } else {
                    throw new BusinessException(BusinessExceptionReason.GET_ROW_COUNT_EXCEPTION.getCommandName());
                }
            }
        } catch (SQLException e) {
            throw new BusinessException(BusinessExceptionReason.SQL_UPDATE_AUTO_INCREMENT_EXCEPTION.getCommandName());
        }
        sqlCommand = DatabaseQueries.ALTER_TABLE_AUTO_INCREMENT;
        try (PreparedStatement alterStatement = connection.prepareStatement(sqlCommand)) {
            alterStatement.setInt(1, rowCount + 1);
            alterStatement.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException(BusinessExceptionReason.SQL_UPDATE_AUTO_INCREMENT_EXCEPTION.getCommandName());
        }
    }

    public void updateTask(String tableName, int taskId, String title, TaskPriority taskPriority, TaskStatus taskStatus) throws SQLException {
        String sqlCommand = DatabaseQueries.UPDATE_TASK_ALL_PARAMETERS;
        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setString(1, title);
            statement.setString(2, String.valueOf(taskPriority));
            statement.setString(3, String.valueOf(taskStatus));
            statement.setInt(4, taskId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException(BusinessExceptionReason.SQL_UPDATE_TASK_EXCEPTION.getCommandName());
        }
    }

    public void updateTask(String tableName, int taskId, TaskStatus taskStatus) throws SQLException {
        String sqlCommand = DatabaseQueries.UPDATE_TASK_TASK_STATUS;
        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setString(1, String.valueOf(taskStatus));
            statement.setInt(2, taskId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException(BusinessExceptionReason.SQL_UPDATE_TASK_EXCEPTION.getCommandName());
        }
    }

    public void updateTask(String tableName, int taskId, TaskPriority taskPriority) throws SQLException {
        String sqlCommand = DatabaseQueries.UPDATE_TASK_TASK_PRIORITY;
        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setString(1, String.valueOf(taskPriority));
            statement.setInt(2, taskId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException(BusinessExceptionReason.SQL_UPDATE_TASK_EXCEPTION.getCommandName());
        }
    }

    public List<Task> convertTaskInList(String tableName) throws SQLException {
        String sqlCommand = DatabaseQueries.SELECT_ALL_FROM_TABLE;
        try (Statement statement = connection.prepareStatement(sqlCommand)) {
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()) {
                taskListService.addTask(new Task(resultSet.getInt("Id"),
                        resultSet.getString("Title"),
                        TaskPriority.valueOf(resultSet.getString("TaskPriority")),
                        TaskStatus.valueOf(resultSet.getString("TaskStatus"))));
            }
            return taskListService.getTaskList();
        } catch (SQLException e) {
            throw new BusinessException(BusinessExceptionReason.SQL_GET_TASK_EXCEPTION.getCommandName());
        }
    }

    public void closeConnection() throws SQLException {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
