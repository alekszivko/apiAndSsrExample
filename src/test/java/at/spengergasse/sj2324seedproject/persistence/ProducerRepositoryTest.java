package at.spengergasse.sj2324seedproject.persistence;

import at.spengergasse.sj2324seedproject.domain.Producer;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestTransaction
public class ProducerRepositoryTest {

    @Inject
    private ProducerRepository repository;

    @Test
    void ensure_save_producer_into_DB() {

        //given
        Producer producer1 = FixtureFactory.producerFixture();

        Producer prod = Producer.builder()
                                .shortname("shortname1")
                                .name("name1")
                                .build();

        //when
        repository.persist(producer1);
        repository.persist(prod);

        //then
        assertThat(repository.findById(producer1.getId())).isSameAs(producer1);
    }
}
