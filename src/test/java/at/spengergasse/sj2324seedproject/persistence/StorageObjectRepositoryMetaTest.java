package at.spengergasse.sj2324seedproject.persistence;

import at.spengergasse.sj2324seedproject.domain.StorageObjectMeta;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestTransaction
public class StorageObjectRepositoryMetaTest {

    @Inject
    private StorageObjectMetaRepository repository;

    @Test
    void ensure_save_storageObjectMeta_into_DB() {

        //given
        StorageObjectMeta storageObjectMeta = FixtureFactory.storageObjectMetaFixture();

        //when
        repository.persist(storageObjectMeta);

        //then
        assertThat(repository.findById(storageObjectMeta.getId())).isSameAs(storageObjectMeta);
    }
}
