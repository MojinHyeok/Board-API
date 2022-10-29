package board.Security.token.Exception;

public class InvalidTokenException extends RuntimeException {

    private static final String MESSAGE = "유효하지 않은 토크입니다.";

    public InvalidTokenException() {
        super(MESSAGE);
    }

}
