package board.Security.token.Service;

import board.Security.token.Exception.InvalidTokenException;
import board.Security.token.Model.Token;
import board.Security.token.Repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class TokenService {

    @Autowired
    TokenRepository tokenRepository;

    @Transactional
    public void saveToken(String token, String userEmail) {
        deleteToken(userEmail);
        Token saveToekn = Token.builder().userEmail(userEmail).token(token).build();
        tokenRepository.save(saveToekn);
    }

    public Token getTokenByUserEmail(String userEmail) {
        return tokenRepository.findByUserEmail(userEmail).orElseThrow(InvalidTokenException::new);
    }

    @Transactional
    public void deleteToken(String userEmail) {
        tokenRepository.deleteAllByUserEmail(userEmail);
    }
}
