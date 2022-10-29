package board.User.Repository;

import board.User.Model.Email;
import board.User.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(Email email);

    Optional<User> findByEmailAndPassword(Email eamil, String password);

}
