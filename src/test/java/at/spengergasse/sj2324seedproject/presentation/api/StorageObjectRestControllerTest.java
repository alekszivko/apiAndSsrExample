package at.spengergasse.sj2324seedproject.presentation.api;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.Mockito.when;

import at.spengergasse.sj2324seedproject.domain.StorageObject;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import at.spengergasse.sj2324seedproject.service.StorageObjectService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.util.List;
import org.junit.jupiter.api.Test;

@QuarkusTest
class StorageObjectRestControllerTest {

  @InjectMock
  private StorageObjectService storageObjectService;

  @Test
  void ensureFetchAllReturnsContentForExistingData() {
    //given
    StorageObject storageObject = FixtureFactory.storageObjectFixture();
    when(storageObjectService.findAll()).thenReturn(List.of(storageObject));

    //then, expect
    given()
        .accept(ContentType.JSON)
        .get(StorageObjectRestController.BASE_URL)
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON);
  }
}
