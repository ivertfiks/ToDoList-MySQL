package org.todolist.exceptions;

public class DuplicateTaskException extends Exception {
    public DuplicateTaskException() {
        super();
    }

    public DuplicateTaskException(String message) {
        super(message);
    }

    public DuplicateTaskException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateTaskException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
