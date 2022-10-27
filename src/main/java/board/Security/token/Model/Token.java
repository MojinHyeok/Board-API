package board.Security.token.Model;

import board.Security.token.Exception.InvalidTokenException;
import lombok.*;

import javax.persistence.*;

@Table(name = "token")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tokenId;

    private String userEmail;

    private String token;

    public void validateSameToken(String token) {
        if (!this.token.equals(token)) {
            throw new InvalidTokenException();
        }
    }

}
