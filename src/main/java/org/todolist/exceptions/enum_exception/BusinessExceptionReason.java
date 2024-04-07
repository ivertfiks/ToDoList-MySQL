package org.todolist.exceptions.enum_exception;

public enum BusinessExceptionReason {
    FAILED_DATABASE_CONNECTION_EXCEPTION("Ошибка: Ошибка подключения к базе данных..."),
    SQL_COMMAND_ADD_TASK_EXCEPTION("Ошибка: Ошибка во время добовления таска в таблицу"),
    SQL_COMMAND_DELETE_TASK_EXCEPTION("Ошибка: Ошибка во время удаления таска из таблицы"),
    SQL_UPDATE_TASK_EXCEPTION("Ошибка: Ошибка во обновления данных таска"),
    SQL_UPDATE_AUTO_INCREMENT_EXCEPTION("Ошибка: Ошибка во время обновления счета autoincrement"),
    GET_ROW_COUNT_EXCEPTION("Ошибка: Невозможно получить доступ к количеству строк в таблице"),
    SQL_GET_TASK_EXCEPTION("Ошибка: Ошибка выполнения sql кода во время получения списка тасков в таблице");

    public String commandName;

    BusinessExceptionReason(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
