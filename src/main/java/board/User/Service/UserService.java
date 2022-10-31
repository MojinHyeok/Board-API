package board.User.Service;

import board.Security.Util.AESUtil;
import board.Security.Util.SHA256Util;
import board.User.Dto.UserDto;
import board.User.Exception.LoginFailException;
import board.User.Model.Email;
import board.User.Model.User;
import board.User.Repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registUser(UserDto userDto) throws Exception {
        AESUtil aesUtil = new AESUtil();
        User user = User.builder().email(userDto.getEmail())
                .password(userDto.getPassword()).build();
        user.getEmail().encodeEmail(aesUtil);
        user.encodePassword();
        userRepository.save(user);
    }

    @SneakyThrows
    public void login(UserDto userDto) {
        AESUtil aesUtil = new AESUtil();
        Email userEmail = new Email(userDto.getEmail());
        userEmail.encodeEmail(aesUtil);
        String password = SHA256Util.encode(userDto.getPassword());
        User user = userRepository.findByEmailAndPassword(userEmail, password).orElseThrow(LoginFailException::new);
    }

}
