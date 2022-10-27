package board.User.Controller;

import board.Security.token.AuthorizationExtractor;
import board.Security.token.JwtTokenProvider;
import board.Security.token.Service.TokenService;
import board.User.Dto.UserDto;
import board.User.Model.User;
import board.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;


    @GetMapping("/")
    public String hello() {
        return "hello";
    }

    @PostMapping("/user/regist")
    public ResponseEntity<Map<String, Object>> regist(@RequestBody UserDto user) throws Exception {
        HashMap<String, Object> resultMap = new HashMap<>();
        User result = userService.RegistUser(user);
        resultMap.put("성공 여부", "succes");
        return new ResponseEntity<>(resultMap, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Void> generateToken(@RequestBody UserDto userDto) throws Exception {
        userService.login(userDto);
        String accessToken = jwtTokenProvider.createAccessToken(userDto.getEmail());
        tokenService.saveToken(accessToken, userDto.getEmail());
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .build();
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String token = AuthorizationExtractor.extractAccessToken(request);
        String userEmail = jwtTokenProvider.extractUserEmail(token);
        tokenService.deleteToken(userEmail);
        return ResponseEntity.noContent().build();
    }

}
