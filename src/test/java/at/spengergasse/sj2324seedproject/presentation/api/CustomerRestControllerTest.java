package at.spengergasse.sj2324seedproject.presentation.api;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.Mockito.when;

import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import at.spengergasse.sj2324seedproject.service.CustomerService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.util.Optional;
import org.junit.jupiter.api.Test;

@QuarkusTest
class CustomerRestControllerTest {

  @InjectMock
  CustomerService customerService;

  @Test
  void ensureFetchCustomerDataReturnsOkForExistingCustomer() {
    //given
    var customer = FixtureFactory.customerDTOFixture("123");
    when(customerService.retrieveCustomerData("123")).thenReturn(Optional.of(customer));

    //expect
    given()
        .accept(ContentType.JSON)
        .queryParam("connectionNo", "123")
        .get(CustomerRestController.BASE_URL)
        .then()
        .statusCode(200);
  }

  @Test
  void ensureFetchCustomerDataReturnsCustomerDataForExistingCustomer() {
    //given
    var customer = FixtureFactory.customerDTOFixture("123");
    when(customerService.retrieveCustomerData("123")).thenReturn(Optional.of(customer));

    //expect
    given()
        .accept(ContentType.JSON)
        .queryParam("connectionNo", "123")
        .get(CustomerRestController.BASE_URL)
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .extract().response();
  }
}
