package board.User.Model;

import board.Security.Util.AESUtil;
import board.Security.Util.SHA256Util;
import lombok.*;

import javax.persistence.*;

@Table(name = "user")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Embedded
    private Email email;

    private String password;

    @Builder
    public User(String email, String password){
        this.email = new Email(email);
        this.password = password;
    }


    public void encodePassword(){
        this.password = SHA256Util.encode(password);
    }

}
