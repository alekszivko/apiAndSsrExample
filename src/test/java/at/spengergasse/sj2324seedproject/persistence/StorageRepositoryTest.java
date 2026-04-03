package at.spengergasse.sj2324seedproject.persistence;


import static org.assertj.core.api.Assertions.assertThat;

import at.spengergasse.sj2324seedproject.domain.Storage;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.util.List;
import org.junit.jupiter.api.Test;


@QuarkusTest
@TestTransaction
class StorageRepositoryTest {

  @Inject
  private StorageRepository storageRepository;


  @Test
  void ensureSaveAndReReadWorks() {

    //Given
    Storage storageGiven = FixtureFactory.storageFixture();

    //When
    storageRepository.persist(storageGiven);

    //Then
    assertThat(storageGiven).isNotNull();
    assertThat(storageGiven.getId()).isNotNull();
    assertThat(storageGiven.getAddress()).isEqualTo(storageGiven.getAddress());

  }


  @Test
  void ensureFindAllByNameContainingIgnoreCaseWorks() {

    //Given
    Storage storage1 = FixtureFactory.storageFixture();
    storage1.setName("test1");
    Storage storage2 = FixtureFactory.storageFixture();

    storageRepository.persist(storage1);
    storageRepository.persist(storage2);

    //When
    List<Storage> found = storageRepository.findAllByNameContainingIgnoreCase(storage1.getName());

    //Then
    assertThat(found).containsExactly(storage1);

  }

}
