package at.spengergasse.sj2324seedproject.persistence;

import at.spengergasse.sj2324seedproject.domain.Customer;
import at.spengergasse.sj2324seedproject.domain.Reservation;
import at.spengergasse.sj2324seedproject.domain.User;
import at.spengergasse.sj2324seedproject.fixture.FixtureFactory;
import at.spengergasse.sj2324seedproject.persistence.reservations.ReservationProjections.ReservationUser;
import at.spengergasse.sj2324seedproject.persistence.reservations.ReservationRepository;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@QuarkusTest
@TestTransaction
class ReservationRepositoryTest {

  @Inject
  private UserRepository userRepository;
  @Inject
  private ReservationRepository reservationRepository;


  @Test
  void ensureSaveAndReadWorks() {
    //Given
    User userGiven = FixtureFactory.userFixture();
    userRepository.persist(userGiven);
    Reservation reservationGiven = FixtureFactory.reservationFixture(userGiven);

    //When
    reservationRepository.persist(reservationGiven);

    //Then
    assertThat(reservationGiven).isNotNull();
    assertThat(reservationGiven.getReservedBy()).isNotNull();
    assertThat(reservationGiven.getReservedFor()).isNotNull();
  }

  @Test
  void ensureReservationIDExistsWorks() {
    //Given
    Reservation reservation = FixtureFactory.reservationFixture(FixtureFactory.userFixture());
    reservation.setReservedFor(Customer.builder().connectionNo("1234919").build());
    //When
    reservationRepository.persist(reservation);
    //Then
    assertThat(
            reservationRepository.existsByReservationId(
                    reservation.getReservationId())).isTrue();
    assertThat(
            reservationRepository.existsByReservationId("12312312312312")).isFalse();
  }

  @Test
  void ensureGetReservationByReservedBy_IdWorks() {
    //Given
    User userGiven = FixtureFactory.userFixture();
    Reservation reservationGiven = FixtureFactory.reservationFixture(userGiven);
    reservationRepository.persist(reservationGiven);
    String userID = reservationGiven.getReservedBy().getUserId();
    //Then
    org.assertj.core.api.Assertions.assertThat(reservationRepository.findByReservedByUserId(userID)).isNotNull();
    org.assertj.core.api.Assertions.assertThat(reservationRepository.findByReservedByUserId(userID)).contains(
            reservationGiven);
  }

  @Test
  void ensureGetReservationByReservationIdWorks() {
    //Given
    Reservation reservationGiven = FixtureFactory.reservationFixture(FixtureFactory.userFixture());
    reservationRepository.persist(reservationGiven);
    var reservationID = reservationGiven.getReservationId();
    //Then
    assertThat(reservationRepository.findByReservationId(reservationID)).isNotNull();
    assertThat(reservationRepository.findByReservationId(reservationID))
            .isEqualTo(Optional.of(reservationGiven));
  }

  @Test
  void ensureGetReservationsByCompletedWorks() {
    //Given
    Reservation reservationGiven = FixtureFactory.reservationFixture();

    //When
    reservationRepository.persist(reservationGiven);

    //Then
    assertThat(reservationGiven).isNotNull();
    assertThat(reservationRepository.findByCompleted(reservationGiven.isCompleted())).isNotNull();
  }

  @Test
  void ensureReservationsByDateAndOrUserWorks() {
    Reservation reservation = FixtureFactory.reservationFixture();
    reservationRepository.persist(reservation);
    reservationRepository.reservationsByDateAndOrUser(Optional.of(reservation.getReservedBy()),
                                                      LocalDateTime.now().minusDays(20));
  }

  @Test
  void ensureResrvationsByDateAndOrUserWorksWithDateOnly() {
    //Given
    Reservation reservation2 = FixtureFactory.reservationFixture();
    reservation2.setReservedAt(LocalDateTime.now().minusDays(100));

    Reservation savedReservation = FixtureFactory.reservationFixture();
    reservationRepository.persist(savedReservation);
    ReservationUser reservationUser = new ReservationUser(savedReservation.getReservationId(),
                                                          savedReservation.getReservedAt(), savedReservation.getReservedFor(),
                                                          savedReservation.getReservationDescription());
    reservationRepository.persist(reservation2);
    ReservationUser reservationUser2 = new ReservationUser(reservation2.getReservationId(),
                                                           reservation2.getReservedAt(), reservation2.getReservedFor(),
                                                           reservation2.getReservationDescription());

    //When
    var reservations = reservationRepository.reservationsByDateAndOrUser(Optional.empty(),
                                                                         LocalDateTime.now().minusDays(20));

    //Then
    Assertions.assertTrue(reservations.contains(reservationUser));
    Assertions.assertFalse(reservations.contains(reservationUser2));
  }
}
