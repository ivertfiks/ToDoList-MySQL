package org.todolist.service.database;

public class DatabaseQueries {

    private static String table;

    public DatabaseQueries(String table) {
        DatabaseQueries.table = table;
    }

    public static final String TABLE_NAME = table;

    public static final String INSERT_INTO_TABLE = "INSERT INTO " + TABLE_NAME + "(Title, TaskPriority, TaskStatus) VALUES (?,?,?)";

    public static final String SELECT_ALL_FROM_TABLE = "SELECT * FROM "+ TABLE_NAME +".tasks";

    public static final String DELETE_FROM_TABLE_ID = "DELETE FROM "+ TABLE_NAME +".tasks WHERE Id = ?;";

    public static final String UPDATE_AFTER_DELETE = "UPDATE "+ TABLE_NAME +".tasks SET Id = Id - 1 WHERE Id > ?;";

    public static final String SELECT_COUNT_FROM_TABLE = "SELECT COUNT(*) FROM "+ TABLE_NAME +".tasks";

    public static final String ALTER_TABLE_AUTO_INCREMENT = "ALTER TABLE "+ TABLE_NAME +".tasks AUTO_INCREMENT = ?";

    public static final String UPDATE_TASK_ALL_PARAMETERS = "UPDATE "+ TABLE_NAME +".tasks SET Title = ?, TaskPriority = ?, TaskStatus = ?  WHERE id = ?";

    public static final String UPDATE_TASK_TASK_STATUS = "UPDATE "+ TABLE_NAME +".tasks SET TaskStatus = ? WHERE id = ?";

    public static final String UPDATE_TASK_TASK_PRIORITY = "UPDATE "+ TABLE_NAME +".tasks SET TaskPriority = ? WHERE id = ?";







}
