package at.spengergasse.sj2324seedproject.service;

import at.spengergasse.sj2324seedproject.domain.Customer;
import at.spengergasse.sj2324seedproject.domain.Reservation;
import at.spengergasse.sj2324seedproject.foundation.ApiKeyGenerator;
import at.spengergasse.sj2324seedproject.persistence.reservations.ReservationRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@Transactional
public class ReservationService {

    @Inject
    ReservationRepository reservationRepository;

    @Inject
    ApiKeyGenerator idGenerator;

    public List<Reservation> fetchReservations(Optional<Boolean> completed) {
        return completed.map(reservationRepository::findByCompleted)
            .orElseGet(reservationRepository::listAll);
    }

    public List<Reservation> getReservationByUserID(String userId) {
        return reservationRepository.findByReservedByUserId(userId);
    }

    public Reservation createReservation(String description, String connectionNo) {
        Reservation reservation = Reservation.builder()
            .reservationId(idGenerator.getRandomKey(10))
            .reservationDescription(description)
            .reservedAt(LocalDateTime.now())
            .completed(false)
            .lastModified(LocalDateTime.now())
            .reservedFor(Customer.builder()
                .connectionNo(connectionNo)
                .build())
            .build();
        reservationRepository.persist(reservation);
        return reservation;
    }

    public Optional<Reservation> getReservationByReservationID(String reservationID) {
        return reservationRepository.findByReservationId(reservationID);
    }

    public void removeReservation(String reservationID) {
        reservationRepository.deleteByReservationId(reservationID);
    }

    public Reservation updateReservation(String reservationId, String description,
        String connectionNo, boolean completed) {
        return reservationRepository.findByReservationId(reservationId).map(r -> {
            r.setReservationDescription(description);
            r.setReservedFor(Customer.builder().connectionNo(connectionNo).build());
            r.setLastModified(LocalDateTime.now());
            r.setCompleted(completed);
            return r;
        }).orElseThrow(
            () -> new IllegalArgumentException(
                "Reservation with reservationId " + reservationId + " not found"));
    }
}
