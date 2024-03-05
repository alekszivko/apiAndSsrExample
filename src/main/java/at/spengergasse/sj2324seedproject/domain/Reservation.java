package at.spengergasse.sj2324seedproject.domain;

import at.spengergasse.sj2324seedproject.foundation.ApiKey;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservations")
public class Reservation extends AbstractPersistable<Long> {

  private static final int DESCRIPTION_LENGTH = 350;
  private static final int RESERVATION_ID_LENGTH = 10;

  @Column(name = "reservation_id", unique = true)
  @NotNull
  private @ApiKey String reservationId;

  @PastOrPresent
  private LocalDateTime reservdAt;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "reserved_by", foreignKey = @ForeignKey(name = "fk_user"))
  private User reservedBy;

  @Embedded
  @Valid
  private Customer reservedFor;

  //@Length(max = DESCRIPTION_LENGTH)
  private String reservationDescription;
  private boolean completed;

  @PastOrPresent
  private LocalDateTime lastModified;

}

