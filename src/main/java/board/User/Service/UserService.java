package board.User.Service;

import board.Security.Util.AESUtil;
import board.Security.Util.SHA256Util;
import board.User.Dto.UserDto;
import board.User.Model.User;
import board.User.Repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User RegistUser(UserDto userDto) throws Exception {
        AESUtil aesUtil = new AESUtil();
        User user = User.builder().email(userDto.getEmail())
                .password(userDto.getPassword()).build();
        user.encodeId(aesUtil);
        user.encodePassword();
        return userRepository.save(user);
    }

    @SneakyThrows
    public void login(UserDto userDto){
        AESUtil aesUtil = new AESUtil();
        String userEmail = aesUtil.encode(userDto.getEmail());
        String password = SHA256Util.encode(userDto.getPassword());
        User user =userRepository.findByEmailAndPassword(userEmail,password).orElseThrow();
    }

}