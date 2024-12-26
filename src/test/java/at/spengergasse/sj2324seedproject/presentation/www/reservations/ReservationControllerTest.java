package at.spengergasse.sj2324seedproject.presentation.www.reservations;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import at.spengergasse.sj2324seedproject.domain.Reservation;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import at.spengergasse.sj2324seedproject.presentation.api.reservations.ReservationDTO;
import at.spengergasse.sj2324seedproject.service.ReservationService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

  private @Autowired MockMvc mockMvc;
  private @MockitoBean ReservationService reservationService;

  @Test
  void ensureGetReservationsReturnsProperView() throws Exception {
    List<Reservation> reservations = List.of(FixtureFactory.reservationFixture(),
        FixtureFactory.reservationFixture());

    when(reservationService.fetchReservations(Optional.empty())).thenReturn(reservations);

    // expect

    mockMvc.perform(get(ReservationController.BASE_URL))
        .andExpect(status().isOk())
        .andExpect(model().attribute("reservations",
            reservations.stream().map(ReservationDTO::new).toList()))
        .andExpect(view().name("reservations/list"))
        .andDo(print());
  }
}