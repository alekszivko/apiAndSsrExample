package at.spengergasse.sj2324seedproject.persistence;

import at.spengergasse.sj2324seedproject.domain.User;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@QuarkusTest
@TestTransaction
class UserRepositoryTest {

    @Inject
    private UserRepository userRepository;

    @Test
    void ensureSaveAndReadWorks() {
        //Given
        User userGiven = FixtureFactory.userFixture();

        //When
        userRepository.persist(userGiven);

        //Then
        assertThat(userGiven).isNotNull();
        assertThat(userGiven.getId()).isNotNull().isPositive();
        assertThat(userGiven.getProfile()).isNotNull();
    }
}
