package com.cotede.todolist.exceptions;

public class CustomExceptions {

    public static class UserNotFound extends RuntimeException {
        public UserNotFound(String message) {
            super(message);
        }
    }

    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String username) {
            super(username);
        }

    }

    public static class EmailAlreadyExistsException extends RuntimeException {
        public EmailAlreadyExistsException(String email) {
            super(email);
        }

    }

    public static class TaskNotFound extends RuntimeException {
        public TaskNotFound(String Title) {
            super(Title);
        }
    }

    public static class WrongPasswordOrEmail extends RuntimeException {
        public WrongPasswordOrEmail() {
            super();
        }
    }

}
