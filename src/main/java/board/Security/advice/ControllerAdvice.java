package board.Security.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import board.Security.token.Exception.InvalidTokenException;
import board.User.Exception.LoginFailException;

@RestControllerAdvice
public class ControllerAdvice {
	
	
	@ExceptionHandler(LoginFailException.class)
	public ResponseEntity handleLoginFailException(LoginFailException e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
	
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity handleInvalidToken(InvalidTokenException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
}
