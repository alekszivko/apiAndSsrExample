package at.spengergasse.sj2324seedproject.presentation.api.reservations;

import at.spengergasse.sj2324seedproject.domain.Reservation;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import at.spengergasse.sj2324seedproject.service.ReservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assumptions.assumeThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationRestController.class)
public class ReservationRestControllerTest{

    private @Autowired MockMvc            mockMvc;
    private @Autowired ObjectMapper       mapper;
    private @MockitoBean ReservationService reservationService;


    @BeforeEach
    void setUp(){
        assumeThat(mockMvc).isNotNull();
        assumeThat(mapper).isNotNull();
    }

    @Test
    void ensureFetchAllReturnsNoContentForMissingData() throws Exception{
        when(reservationService.fetchReservations(Optional.empty())).thenReturn(Collections.emptyList());

        var request = get(ReservationRestController.BASE_URL).accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
               .andExpect(status().isNoContent())
               .andDo(print());
    }

    @Test
    void ensureFetchAllReturnsContentForExistingData() throws Exception{

        Reservation reservation = FixtureFactory.reservationFixture();

        when(reservationService.fetchReservations(Optional.of(reservation.isCompleted()))).thenReturn(List.of(reservation));

        var request = get(ReservationRestController.BASE_URL).param("completed", String.valueOf(reservation.isCompleted()))
                                                             .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andDo(print());
    }

    @Test
    void ensureCreateReservationProblemDetailInInternalServerErrorResponseDueToPersistenceException() throws Exception{
        //given

        var description = "description";
        when(reservationService.createReservation(eq(description), any())).thenThrow(new PersistenceException(""));
        CreateReservationCommand cmd = new CreateReservationCommand("123102", description);

        //expect

        var request = post(ReservationRestController.BASE_URL).accept(MediaType.APPLICATION_JSON)
                                                              .contentType(MediaType.APPLICATION_JSON)
                                                              .content(mapper.writeValueAsString(cmd));

        mockMvc.perform(request)
               .andExpect(status().isInternalServerError())
               .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
               .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
               .andExpect(jsonPath("$.title").value("Persistence Error"))
               .andDo(print());
    }

    @Test
    void ensureGetReservationByIDReturnsReservation() throws Exception{
        Reservation reservation = FixtureFactory.reservationFixture();

        when(reservationService.getReservationByReservationID(reservation.getReservationId())).thenReturn(Optional.of(reservation));

        var request = get(ReservationRestController.BASE_URL+"/{id}", reservation.getReservationId()).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
               .andExpect(jsonPath("$.reservationId").value(reservation.getReservationId()))
               .andDo(print());
    }
}
