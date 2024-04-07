package org.todolist.service;

import org.todolist.exceptions.BusinessException;
import org.todolist.exceptions.enum_exception.BusinessExceptionReason;
import org.todolist.model.enum_model.TaskPriority;
import org.todolist.model.enum_model.TaskStatus;

import java.sql.*;

public class DatabaseOperations {
    private static DatabaseOperations instance;
    private static Connection connection;

    private DatabaseOperations(String address, String userName, String password) throws SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://"+ address +"/?user="+ userName +"&password=" + password);
            System.out.println("Connection success");
        } catch (SQLException e) {
            throw new BusinessException(BusinessExceptionReason.FAILED_DATABASE_CONNECTION_EXCEPTION.getCommandName());
        }
    }

    public static DatabaseOperations getInstance(String address, String userName, String password) throws SQLException {
        if (instance == null) {
            instance = new DatabaseOperations(address, userName, password);
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void addTask(String tableName, String description, String taskPriority, String taskStatus) throws SQLException {
        String sqlCommand = "INSERT INTO "+ tableName +".tasks (Title, TaskPriority, TaskStatus) VALUES (?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setString(1, description);
            statement.setString(2, taskPriority);
            statement.setString(3, taskStatus);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException(BusinessExceptionReason.SQL_COMMAND_ADD_TASK_EXCEPTION.getCommandName());
        }
    }

    public void deleteTask(String tableName, int taskId) throws SQLException {
        String sqlCommand = "DELETE FROM "+ tableName +".tasks WHERE Id = ?;";
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
        String sqlCommand = "UPDATE "+ tableName +".tasks SET Id = Id - 1 WHERE Id > ?;";
        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setInt(1, taskId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException(BusinessExceptionReason.SQL_UPDATE_TASK_EXCEPTION.getCommandName());
        }
    }

    private void autoIncrementUpdate(String tableName) throws SQLException {
        String sqlCommand = "SELECT COUNT(*) FROM "+ tableName +".tasks";
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
        sqlCommand = "ALTER TABLE "+ tableName +".tasks AUTO_INCREMENT = ?";
        try (PreparedStatement alterStatement = connection.prepareStatement(sqlCommand)) {
            alterStatement.setInt(1, rowCount + 1);
            alterStatement.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException(BusinessExceptionReason.SQL_UPDATE_AUTO_INCREMENT_EXCEPTION.getCommandName());
        }
    }

    public void updateTask(String tableName, int taskId, String title, TaskPriority taskPriority, TaskStatus taskStatus) throws SQLException {
        String sqlCommand = "UPDATE "+ tableName +".tasks SET Title = ?, TaskPriority = ?, TaskStatus = ?  WHERE id = ?";
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
        String sqlCommand = "UPDATE "+ tableName +".tasks SET TaskStatus = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setString(1, String.valueOf(taskStatus));
            statement.setInt(2, taskId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException(BusinessExceptionReason.SQL_UPDATE_TASK_EXCEPTION.getCommandName());
        }
    }

    public void updateTask(String tableName, int taskId, TaskPriority taskPriority) throws SQLException {
        String sqlCommand = "UPDATE "+ tableName +".tasks SET TaskPriority = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            statement.setString(1, String.valueOf(taskPriority));
            statement.setInt(2, taskId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new BusinessException(BusinessExceptionReason.SQL_UPDATE_TASK_EXCEPTION.getCommandName());
        }
    }

    public void printTasks(String tableName) throws SQLException {
        String sqlCommand = "SELECT * FROM "+ tableName +".tasks";
        try (Statement statement = connection.prepareStatement(sqlCommand)) {
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("Id") + ": " + resultSet.getString("Title") + ", " + resultSet.getString("TaskPriority")
                        + ", " + resultSet.getString("TaskStatus"));
            }
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
