package at.spengergasse.sj2324seedproject.persistence.reservations;

import at.spengergasse.sj2324seedproject.domain.Customer;
import java.time.LocalDateTime;

public class ReservationProjections {

    public record ReservationUser(String key, LocalDateTime reservedAt, Customer reservedFor,
                                  String reservationDescription) {
    }
}
