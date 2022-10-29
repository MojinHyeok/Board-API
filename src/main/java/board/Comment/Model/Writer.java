package board.Comment.Model;

import board.Security.Util.AESUtil;

import javax.persistence.Embeddable;

@Embeddable
public class Writer {

    private static final String EMAIL_NULL_OR_BLANK_ERROR = "이메일은 빈값 혹은 Null이 될 수 없습니다.";
    private static final String EMAIL_ENCODED_ERROR = "아직 디코딩되지 않은 이메일입니다.";
    private static final String EMAIL_BITMASKING_PATTERN = "(?<=.{3}).(?=.*@)";
    private static final String BITMASKING_SIGN = "*";

    private String writer;

    private Writer() {
    }

    public Writer(String encodeEmail) {
        if (encodeEmail == null || encodeEmail.isEmpty()) {
            throw new RuntimeException(EMAIL_NULL_OR_BLANK_ERROR);
        }
        this.writer = encodeEmail;
    }

    public void decodeEmail(AESUtil aesUtil) throws Exception {
        this.writer = aesUtil.decode(writer);
    }

    public void encodeEmail(AESUtil aesUtil) throws Exception {
        this.writer = aesUtil.encode(writer);
    }

    public void maskingEmail() {
        checkBeforeDecodeEmail();
        this.writer = this.writer.replaceAll(EMAIL_BITMASKING_PATTERN, BITMASKING_SIGN);
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
}
