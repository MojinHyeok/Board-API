package board.User.Exception;

import lombok.Getter;

@Getter
public class LoginFailException extends RuntimeException {

	private static final String MESSAGE = "아이디 혹은 비밀번호가 잘못되었습니다.";
	
	public LoginFailException() {
		super(MESSAGE);
	}

}
