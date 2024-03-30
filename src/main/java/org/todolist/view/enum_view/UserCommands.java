package org.todolist.view.enum_view;

public enum UserCommands {
    ENTER_TASK("Введите таск"),
    ENTER_ID("Введите id"),
    REMOVE_NUMBER("Введите id таска которого хотите удалить"),
    ENTER_NAME_OF_FILE("Введите название CSV файл с которого хотите считать данные"),
    ENTER_PRIORITY("Введите приоретет таска в указанном формате (Low/Medium/High)"),
    ENTER_STATUS("Введите статус таска в указанном формате (Pending, Doing, Done)"),
    EDIT_STATUS("Введите id таска статус которого хотите изменить.");
    public String commandName;

    UserCommands(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
