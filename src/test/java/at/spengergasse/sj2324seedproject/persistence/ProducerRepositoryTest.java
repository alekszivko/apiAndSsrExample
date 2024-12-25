package at.spengergasse.sj2324seedproject.persistence;

import at.spengergasse.sj2324seedproject.domain.Producer;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProducerRepositoryTest {


    @Autowired
    private ProducerRepository repository;

    @Test
    void ensure_save_producer_into_DB(){

        //given
        Producer producer1 = FixtureFactory.producerFixture();



        Producer prod =  Producer.builder()
                                 .shortname("shortname1")
                                 .name("name1")
                                 .build();

        //when
        var saved = repository.saveAndFlush(producer1);
//        var saved2 = repository.save(producer2);
        var saved3 = repository.save(prod);


        //then
        assertThat(repository.findById(saved.getId()).get()).isSameAs(producer1);
//        assertThat(repository.findById(saved3.getId())).isSameAs(prod);


    }
}