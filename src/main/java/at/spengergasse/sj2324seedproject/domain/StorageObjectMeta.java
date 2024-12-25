package at.spengergasse.sj2324seedproject.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)

@Entity
@Table(name = "storage_object_metas")
public class StorageObjectMeta extends AbstractPersistable<Long> {

  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  public List<StorageObject> storageobject;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "fk_producer", foreignKey = @ForeignKey(name = "fk_producer_2_storageObejctMeta"))
  private Producer producer;


  @Column(name = "storage_object_type")
  @Enumerated(EnumType.STRING)
  private Type type;

  @NotBlank
  @Column(name = "storage_object_name")
  private String name;

  @NotBlank
  @Column(name = "os_version")
  private String osVersion;

  @Min(0)
  @Column(name = "consumables_per_box")
  private Integer consumablesPerBox;

  @Enumerated(EnumType.STRING)
  @Column(name = "sfp_type")
  private SfpType sfpType;

  @NotBlank
  @Column(name = "wave_length")
  private String wavelength;

  @NotBlank
  @Column(name = "interface_speed")
  private String interfacespeed;


}
