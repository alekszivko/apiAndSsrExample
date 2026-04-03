package at.spengergasse.sj2324seedproject.presentation.api.reservations;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import at.spengergasse.sj2324seedproject.domain.Reservation;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import at.spengergasse.sj2324seedproject.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class ReservationRestControllerTest {

    @InjectMock
    private ReservationService reservationService;

    @Inject
    private ObjectMapper mapper;

    @Test
    void ensureFetchAllReturnsNoContentForMissingData() {
        when(reservationService.fetchReservations(Optional.empty())).thenReturn(Collections.emptyList());

        given()
            .accept(ContentType.JSON)
            .get(ReservationRestController.BASE_URL)
            .then()
            .statusCode(204);
    }

    @Test
    void ensureFetchAllReturnsContentForExistingData() {
        Reservation reservation = FixtureFactory.reservationFixture();
        when(reservationService.fetchReservations(Optional.of(reservation.isCompleted()))).thenReturn(List.of(reservation));

        given()
            .accept(ContentType.JSON)
            .queryParam("completed", String.valueOf(reservation.isCompleted()))
            .get(ReservationRestController.BASE_URL)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    void ensureCreateReservationProblemDetailInInternalServerErrorResponseDueToPersistenceException() throws Exception {
        //given
        var description = "description";
        when(reservationService.createReservation(eq(description), any())).thenThrow(new PersistenceException(""));
        CreateReservationCommand cmd = new CreateReservationCommand("123102", description);

        //expect
        given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(mapper.writeValueAsString(cmd))
            .post(ReservationRestController.BASE_URL)
            .then()
            .statusCode(500);
    }

    @Test
    void ensureGetReservationByIDReturnsReservation() {
        Reservation reservation = FixtureFactory.reservationFixture();
        when(reservationService.getReservationByReservationID(reservation.getReservationId())).thenReturn(Optional.of(reservation));

        given()
            .accept(ContentType.JSON)
            .get(ReservationRestController.BASE_URL + "/" + reservation.getReservationId())
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }
}
