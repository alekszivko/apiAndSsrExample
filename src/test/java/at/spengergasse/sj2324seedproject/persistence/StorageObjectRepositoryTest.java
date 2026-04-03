package at.spengergasse.sj2324seedproject.persistence;

import at.spengergasse.sj2324seedproject.domain.StorageObject;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestTransaction
public class StorageObjectRepositoryTest {

    @Inject
    private StorageObjectRepository repository;

    @Test
    void ensure_save_storageOBject_into_DB() {

        //given
        StorageObject storageObject = FixtureFactory.storageObjectFixture();

        //when
        repository.persist(storageObject);

        //then
        assertThat(repository.findById(storageObject.getId())).isSameAs(storageObject);
    }
}
