package board.Comment.Model;

import board.Security.Util.AESUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.*;

class WriterTest {


    @Test
    @DisplayName("Writer클래스에 대한 정상적으로 생성되어지는지에 대한 테스트")
    public void 생성테스트_정상생성자_테스트() {
        Writer writer = new Writer("mojh123@naver.com");
        Writer writer2 = new Writer("mojh123@naver.com");
        assertThat(writer).isEqualTo(writer2);
    }

    @Test
    @DisplayName("Writer에 대한 이메일 형식에 맞지 않는 값을 넣을 때 에러가 발생하는지에 대한 테스트")
    public void 생성테스트_이메일형식에맞지않는값_테스트() {
        assertThatThrownBy(() -> new Writer("mojh")).isInstanceOf(RuntimeException.class);
    }

    @ParameterizedTest(name = "Wirter를 생성할 때 Null 혹은 빈값을 넣을 경우 에러를 발생한다.")
    @NullAndEmptySource
    public void 생성테스트_에러테스트_Null_Or_빈값넣기(String text) {
        assertThatThrownBy(() -> new Writer(text)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("이름을 생성하고 암호화가 잘 작동되어지는지에 대한 테스트")
    public void 암호화_정상테스트() throws Exception {
        AESUtil aesUtil = new AESUtil();
        Writer writer = new Writer("mojh123@naver.com");
        writer.encodeEmail(aesUtil);
        assertThat(writer.toString().charAt(writer.toString().length() - 1)).isEqualTo('=');
    }

    @Test
    @DisplayName("암호화한 것을 복호화하였을 때 똑같은 값이 나온는지에 대한 테스트")
    public void 복호화_정상테스트() throws Exception {
        AESUtil aesUtil = new AESUtil();
        Writer writer = new Writer("mojh123@naver.com");
        writer.encodeEmail(aesUtil);
        writer.decodeEmail(aesUtil);
        assertThat(writer.toString()).isEqualTo("mojh123@naver.com");
    }

    @Test
    @DisplayName("마스킹이 정상적으로 처리되는지에 대한 테스트")
    public void 마스킹_정상테스트() {
        Writer writer = new Writer("mojh123@naver.com");
        writer.maskingEmail();
        assertThat(writer.toString().contains("*")).isEqualTo(true);
    }


}