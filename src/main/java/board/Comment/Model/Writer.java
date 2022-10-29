package board.Comment.Model;

import board.Security.Util.AESUtil;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class Writer {

    private static final Pattern PATTERN = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");

    private static final String EMAIL_NULL_OR_BLANK_ERROR = "이메일은 빈값 혹은 Null이 될 수 없습니다.";
    private static final String EMAIL_ENCODED_ERROR = "아직 디코딩되지 않은 이메일입니다.";

    private static final String EMAIL_PATTERN_ERROR = "이메일 형식에 맞지 않습니다.";

    private static final String EMAIL_MASKING_PATTERN = "(?<=.{3}).(?=.*@)";
    private static final String MASKING_SIGN = "*";

    private String writer;

    private Writer() {
    }

    public Writer(String email) {
        validate(email);
        this.writer = email;
    }

    private void validate(String email) {
        if (!PATTERN.matcher(email).matches()) {
            throw new RuntimeException(EMAIL_PATTERN_ERROR);
        }
        if (email == null || email.isEmpty()) {
            throw new RuntimeException(EMAIL_NULL_OR_BLANK_ERROR);
        }
    }

    public void decodeEmail(AESUtil aesUtil) throws Exception {
        this.writer = aesUtil.decode(writer);
    }

    public void encodeEmail(AESUtil aesUtil) throws Exception {
        this.writer = aesUtil.encode(writer);
    }

    public void maskingEmail() {
        checkBeforeDecodeEmail();
        this.writer = this.writer.replaceAll(EMAIL_MASKING_PATTERN, MASKING_SIGN);
    }

    private void checkBeforeDecodeEmail() {
        if (writer.charAt(writer.length() - 1) == '=') {
            throw new RuntimeException(EMAIL_ENCODED_ERROR);
        }
    }

    @Override
    public String toString() {
        return writer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Writer)) return false;
        Writer writer1 = (Writer) o;
        return Objects.equals(writer, writer1.writer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(writer);
    }
}
