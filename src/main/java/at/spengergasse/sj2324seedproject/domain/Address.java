package at.spengergasse.sj2324seedproject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// implemented by MM
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor


@Embeddable
public class Address {
    @Column(length = 128)
    private @NotNull @NotEmpty @NotBlank String street;
    @Column(length = 32)
    private @NotNull @NotEmpty @NotBlank Integer number;
    @Column(length = 64)
    private String addressAddition;
    @Column(length = 16)
    private @NotNull @NotEmpty @NotBlank Integer zipcode;
    @Column(length = 64)
    private @NotNull @NotEmpty @NotBlank String city;









}
