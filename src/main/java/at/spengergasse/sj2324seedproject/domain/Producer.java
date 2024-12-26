package at.spengergasse.sj2324seedproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)


@Entity
@Table(name = "producers")
public class Producer extends AbstractPersistable<Long>{

    private static final int DEFAULT_LENGTH = 55;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<StorageObjectMeta> storageObjectMeta;

    @Column(name = "shortname", length = DEFAULT_LENGTH)
    @NotBlank
    private String shortname;

    @NotBlank
    @Column(name = "producer_name", length =  DEFAULT_LENGTH)
    private String name;



}
