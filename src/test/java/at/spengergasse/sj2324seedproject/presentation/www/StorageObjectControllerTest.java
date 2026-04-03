package at.spengergasse.sj2324seedproject.presentation.www;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

import at.spengergasse.sj2324seedproject.domain.StorageObject;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import at.spengergasse.sj2324seedproject.presentation.www.storageObjects.StorageObjectController;
import at.spengergasse.sj2324seedproject.service.StorageObjectService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@QuarkusTest
class StorageObjectControllerTest {

  @InjectMock
  private StorageObjectService serviceStorageObject;

  @Test
  void ensureGetStorageObjectReturnsProperView() {
    List<StorageObject> storageObjectList = List.of(FixtureFactory.storageObjectFixture(),
        FixtureFactory.storageObjectFixture());

    when(serviceStorageObject.findAll()).thenReturn(storageObjectList);

    given()
        .get("/storageObjects")
        .then()
        .statusCode(200);
  }
}
