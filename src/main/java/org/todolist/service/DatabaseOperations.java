package org.todolist.service;

import org.todolist.model.enum_model.TaskPriority;

import java.sql.*;

public class DatabaseOperations {
    private static DatabaseOperations instance;
    private static Connection connection;
    private DatabaseOperations() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/?user=root&password=password");
            System.out.println("Connection success");
        } catch (SQLException e) {
            System.out.println("Connection failed...");
            throw new RuntimeException(e);
        }
    }

    public static DatabaseOperations getInstance(){
        if(instance == null){
            instance = new DatabaseOperations();
        }
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }

    public void addTask(String description, String taskPriority, String taskStatus){
        String sqlCommand = "INSERT INTO todo_list.tasks (Title, TaskPriority, TaskStatus) VALUES (?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(sqlCommand)){
            statement.setString(1, description);
            statement.setString(2, taskPriority);
            statement.setString(3, taskStatus);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTask(int taskId){
        String sqlCommand = "DELETE FROM todo_list.tasks WHERE Id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sqlCommand)){
            statement.setInt(1,taskId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateTask(int taskId, String title, String taskPriority, String taskStatus){
        String sqlCommand = "UPDATE todo_list.tasks SET Title = ?, TaskPriority = ?, TaskStatus = ?  WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sqlCommand)){
            statement.setString(1, title);
            statement.setString(2, taskPriority);
            statement.setString(3, taskStatus);
            statement.setInt(4, taskId);
            statement.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void printTasks(){
        String sqlCommand = "SELECT * FROM todo_list.tasks";
        try(Statement statement = connection.prepareStatement(sqlCommand)){
            ResultSet resultSet = statement.executeQuery(sqlCommand);
            while (resultSet.next()){
                System.out.println(resultSet.getInt("Id") + ": " + resultSet.getString("Title") + ", " + resultSet.getString("TaskPriority")
                + ", " + resultSet.getString("TaskStatus"));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void closeConnection(){
        try{
            if(connection != null){
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}
