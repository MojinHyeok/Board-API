package board.User.Repository;

import board.User.Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    private static final String NORMAL_EMAIL = "gkgk246@naver.com";
    private static final String NORMAL_PASSWORD = "123456";
    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManager em;

    private void clear() {
        em.flush();
        ;
        em.clear();
    }

    @AfterEach
    private void after() {
        em.clear();
    }

    @Test
    @DisplayName("회원가입 성공에 대한 테스트")
    public void 회원가입_성공() throws Exception {
        User user = User.builder().email(NORMAL_EMAIL).password(NORMAL_PASSWORD).build();

        User successUser = userRepository.save(user);

        User properUser = userRepository.findByEmail(successUser.getEmail()).orElseThrow();
        assertThat(properUser).isEqualTo(successUser);
    }

    @Test
    @DisplayName("회원가입시 Email이 없는 것에 대한 테스트 ")
    public void 회원가입_오류_이메일이_존재하지않음() throws Exception {
        User user = User.builder().email(null).password(NORMAL_PASSWORD).build();

        assertThatThrownBy(() -> userRepository.save(user)).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("회원가입시 패스워드가 없는 것에 대한 테스트 ")
    public void 회원가입_오류_패스워드가_존재하지않음() throws Exception {
        User user = User.builder().email(NORMAL_EMAIL).password(null).build();

        assertThatThrownBy(() -> userRepository.save(user)).isInstanceOf(Exception.class);
    }

    @Test
    @DisplayName("중복된 이메일로 회원가입시 에러발생")
    public void 회원가입_오류_중복된_이메일로_가입시_에러() throws Exception {
        User user1 = User.builder().email(NORMAL_EMAIL).password(NORMAL_PASSWORD).build();
        User user2 = User.builder().email(NORMAL_EMAIL).password(NORMAL_PASSWORD + "!").build();

        userRepository.save(user1);

        assertThatThrownBy(() -> userRepository.save(user2)).isInstanceOf(Exception.class);
    }
}