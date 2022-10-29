package board.User.Model;

import board.Security.Util.AESUtil;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class Email {

    private static final Pattern PATTERN = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");
    private static final String EMAIL_PATTERN_ERROR = "이메일 형식에 맞지 않습니다.";

    private static final String EMAIL_NULL_OR_BLANK_ERROR = "이메일은 빈값 혹은 Null이 될 수 없습니다.";
    private String email;

    private Email() {
    }

    public Email(String email) {
        validate(email);
        this.email = email;
    }

    private void validate(String email) {
        if (!PATTERN.matcher(email).matches()) {
            throw new RuntimeException(EMAIL_PATTERN_ERROR);
        }
        if (email == null || email.isEmpty()) {
            throw new RuntimeException(EMAIL_NULL_OR_BLANK_ERROR);
        }
    }

    public void encodeEmail(AESUtil aesUtil) throws Exception {
        this.email = aesUtil.encode(email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;
        Email email1 = (Email) o;
        return Objects.equals(email, email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
