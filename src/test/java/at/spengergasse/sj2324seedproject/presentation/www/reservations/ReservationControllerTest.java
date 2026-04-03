package at.spengergasse.sj2324seedproject.presentation.www.reservations;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

import at.spengergasse.sj2324seedproject.domain.Reservation;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import at.spengergasse.sj2324seedproject.service.ReservationService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

@QuarkusTest
class ReservationControllerTest {

  @InjectMock
  private ReservationService reservationService;

  @Test
  void ensureGetReservationsReturnsProperView() {
    List<Reservation> reservations = List.of(FixtureFactory.reservationFixture(),
        FixtureFactory.reservationFixture());

    when(reservationService.fetchReservations(Optional.empty())).thenReturn(reservations);

    given()
        .get(ReservationController.BASE_URL)
        .then()
        .statusCode(200);
  }
}
