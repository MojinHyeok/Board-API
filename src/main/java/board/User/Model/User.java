package board.User.Model;

import board.Security.Util.AESUtil;
import board.Security.Util.SHA256Util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String email;

    private String password;


    public void encodeId(AESUtil aesUtil) throws Exception {
        this.email = aesUtil.encode(email);
    }
    public void encodePassword(){
        this.password = SHA256Util.encode(password);
    }

}
