package board.Security.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = AuthorizationExtractor.extractAccessToken(request);
        String userEmail = jwtTokenProvider.extractUserEmail(token);
        if (isInvalidToken(token) || checkSameToken(token, userEmail)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        return true;
    }

    private boolean checkSameToken(String token, String userEmail) {
        return !jwtTokenProvider.checkSameToken(token, userEmail);
    }

    private boolean isInvalidToken(String token) {
        return !jwtTokenProvider.isValid(token);
    }
}
