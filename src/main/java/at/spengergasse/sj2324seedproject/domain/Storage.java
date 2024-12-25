package at.spengergasse.sj2324seedproject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

//    Implementend by MM
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name="storages")
public class Storage extends AbstractPersistable<Long> {


    protected static final int NAME_LENGTH = 128;
    @Column(name="storage_name", length = NAME_LENGTH)
    private @NotNull @NotEmpty @NotBlank String name;


    @Embedded
    private Address address;



}
