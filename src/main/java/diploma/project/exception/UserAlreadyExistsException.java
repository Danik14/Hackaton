package diploma.project.exception;

import diploma.project.enums.UserAlreadyExistsType;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(UserAlreadyExistsType type, String parameter) {
        super(createMessage(type, parameter));
    }

    private static String createMessage(UserAlreadyExistsType type, String parameter) {
        if (type == UserAlreadyExistsType.EMAIL) {
            return "User with such email already exists";
        } else if (type == UserAlreadyExistsType.USERNAME) {
            return "User with such username already exists";
        }
        return "User already exists";
    }
}
