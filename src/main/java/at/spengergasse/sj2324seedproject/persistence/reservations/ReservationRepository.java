package at.spengergasse.sj2324seedproject.persistence.reservations;

import at.spengergasse.sj2324seedproject.domain.Reservation;
import at.spengergasse.sj2324seedproject.domain.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ReservationRepository implements PanacheRepository<Reservation> {

    public boolean existsByReservationId(String resID) {
        return count("reservationId", resID) > 0;
    }

    public List<Reservation> findByReservedByUserId(String userId) {
        return list("reservedBy.userId", userId);
    }

    public Optional<Reservation> findByReservationId(String reservationId) {
        return find("reservationId", reservationId).firstResultOptional();
    }

    public List<Reservation> findByCompleted(Boolean completed) {
        return list("completed", completed);
    }

    public void deleteByReservationId(String reservationId) {
        delete("reservationId", reservationId);
    }

    public List<ReservationProjections.ReservationUser> reservationsByDateAndOrUser(
        Optional<User> user, LocalDateTime fromDateTime) {

        List<Reservation> reservations = user
            .map(u -> list("reservedBy = ?1 AND reservedAt > ?2", u, fromDateTime))
            .orElseGet(() -> list("reservedAt > ?1", fromDateTime));

        return reservations.stream()
            .map(res -> new ReservationProjections.ReservationUser(
                res.getReservationId(),
                res.getReservedAt(),
                res.getReservedFor(),
                res.getReservationDescription()))
            .toList();
    }
}
