package at.spengergasse.sj2324seedproject.presentation.api;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

import at.spengergasse.sj2324seedproject.domain.SfpType;
import at.spengergasse.sj2324seedproject.domain.StorageObjectMeta;
import at.spengergasse.sj2324seedproject.domain.Type;
import at.spengergasse.sj2324seedproject.service.StorageObjectMetaService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.util.Optional;
import org.junit.jupiter.api.Test;


@QuarkusTest
class StorageObjectMetaRestControllerTest {

  @InjectMock
  StorageObjectMetaService storageObjectMetaService;

  @Test
  void ensure_test_fetching_meta() {
    String exp = "eta n";
    StorageObjectMeta storageObjectMeta = StorageObjectMeta.builder()
        .name("meta name1")
        .type(Type.IP_PHONE)
        .osVersion("version1")
        .consumablesPerBox(2)
        .sfpType(SfpType.MM)
        .wavelength("1550nm")
        .interfacespeed("100-Mbps")
        .build();
    when(storageObjectMetaService.findStorageObjectMeta(exp)).thenReturn(Optional.of(storageObjectMeta));

    given()
        .accept(ContentType.JSON)
        .get(StorageObjectMetaRestController.BASE_URL + "/" + exp)
        .then()
        .statusCode(200);
  }
}
