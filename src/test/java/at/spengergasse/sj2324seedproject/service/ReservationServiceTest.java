package at.spengergasse.sj2324seedproject.service;

import at.spengergasse.sj2324seedproject.domain.Reservation;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import at.spengergasse.sj2324seedproject.foundation.ApiKeyGenerator;
import at.spengergasse.sj2324seedproject.persistence.UserRepository;
import at.spengergasse.sj2324seedproject.persistence.reservations.ReservationRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApiKeyGenerator idGenerator;

    @Test
    void ensureFetchReservationWithoutParamCallsFindAll() {
        //Given
        Optional<Boolean> isCompleted = Optional.empty();
        var reservation = FixtureFactory.reservationFixture();
        when(reservationRepository.listAll()).thenReturn(List.of(reservation));

        //When
        var result = reservationService.fetchReservations(isCompleted);

        //Then
        verify(reservationRepository, times(1)).listAll();
    }

    @Test
    void ensureGetReservationsByUserIdWorks() {
        //given
        String userId = "23sdf";
        var reservation = FixtureFactory.reservationFixture();
        when(reservationRepository.findByReservedByUserId(userId)).thenReturn(List.of(reservation));

        //when
        var result = reservationService.getReservationByUserID(userId);

        //then
        assertThat(result).containsExactly(reservation);
        verify(reservationRepository, times(1)).findByReservedByUserId(userId);
    }

    @Test
    void ensureGetReservationByReservationIdWorks() {
        //given
        Reservation reservation = FixtureFactory.reservationFixture();
        when(reservationRepository.findByReservationId(reservation.getReservationId())).thenReturn(Optional.of(reservation));

        //when
        var result = reservationService.getReservationByReservationID(reservation.getReservationId());

        //then
        assertThat(result).contains(reservation);
    }

    @Test
    void ensureFetchReservationsWorks() {
        //given
        var reservation = FixtureFactory.reservationFixture();
        when(reservationRepository.listAll()).thenReturn(List.of(reservation));

        //when
        var result = reservationService.fetchReservations(Optional.empty());

        //then
        assertThat(result).containsExactly(reservation);
        verify(reservationRepository, times(1)).listAll();
    }
}
