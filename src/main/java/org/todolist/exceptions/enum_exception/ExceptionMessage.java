package org.todolist.exceptions.enum_exception;

public enum ExceptionMessage {
    DUPLICATE_TASK_EXCEPTION("Ошибка: Такой таск уже есть в листе"),
    INVALID_TASK_EXCEPTION("Ошибка: Вы ввели неправильный формат таска"),
    INPUT_MISMATCH_EXCEPTION("Ошибка: Вы ввели неправильный тип данных"),
    INDEX_OUT_OF_BOUNCE_EXCEPTION("Ошибка: Вы вышли за пределы границ массива"),
    ILLEGAL_ARGUMENT_EXCEPTION("Ошибка: Вы ввели непраильный аргумент..."),
    UNEXPECTED_ERROR("Ошибка: unexpected error"),
    WRONG_MENU_NUMBER("Ошибка: Вы ввели неправильный номер пункта меню..."),
    FILE_NOT_FOUND_EXCEPTION("Ошибка: неправильное имя файла");
    public String commandName;

    ExceptionMessage(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
