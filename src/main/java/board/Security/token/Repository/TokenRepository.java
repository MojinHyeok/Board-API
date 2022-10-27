package board.Security.token.Repository;

import board.Security.token.Model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByUserEmail(String userEmail);
    @Transactional
    void deleteAllByUserEmail(String userEmail);
}
