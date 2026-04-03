package at.spengergasse.sj2324seedproject.presentation.www.storages;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

import at.spengergasse.sj2324seedproject.domain.Storage;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import at.spengergasse.sj2324seedproject.service.StorageService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

@QuarkusTest
class StorageControllerTest {

  @InjectMock
  private StorageService storageService;

  @Test
  void ensureGetStorageReturnsProperView() {
    List<Storage> storages = List.of(FixtureFactory.storageFixture(),
        FixtureFactory.storageFixture());

    when(storageService.fetchStorage(Optional.empty())).thenReturn(storages);

    given()
        .get(StorageController.BASE_URL)
        .then()
        .statusCode(200);
  }
}
