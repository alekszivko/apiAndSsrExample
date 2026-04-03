package at.spengergasse.sj2324seedproject.foundation;

import static at.spengergasse.sj2324seedproject.foundation.Guard.ensureMatchesBase58;
import static at.spengergasse.sj2324seedproject.foundation.Guard.ensureMatchesPattern;
import static at.spengergasse.sj2324seedproject.foundation.Guard.ensureNotNull;
import static at.spengergasse.sj2324seedproject.foundation.Guard.isNotNull;
import static at.spengergasse.sj2324seedproject.foundation.Guard.isNull;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class GuardTest {

    @Test
    void ensureNotNullWithNonNullArgumentReturnsArgument() {
        assertThat(isNotNull("blalba")).isEqualTo("blalba");

    }

    @Test
    void ensureNotNullWithNullThrowsException() {
        var exception = assertThrows(IllegalArgumentException.class, () -> isNotNull(null));
    }

    @Test
    void ensureNotNullWithNullAndNameThrowsException() {
        var exception = assertThrows(IllegalArgumentException.class, () -> isNotNull(null, "XXX"));
        assertThat(exception).hasMessageContaining("'XXX' must not be null!");
    }

    @Test
    void ensureMatchesBase58WithInvalidArgumentThrowsException() {
        var exception = assertThrows(IllegalArgumentException.class, () ->
                ensureMatchesBase58("12§"));

        assertThat(exception).hasMessage("'argument' must match pattern '[A-HJ-NP-Za-km-z1-9]*'");
    }

    @Test
    void ensureMatchesBase58WithValidArgumentReturnsArgument() {
        assertThat(ensureMatchesBase58("A")).isEqualTo("A");
    }

    @Test
    void ensureMatchesPatternWithInvalidCharacterThrowsException() {
        var exception = assertThrows(IllegalArgumentException.class, () ->
                ensureMatchesPattern("0*§", "^[A-HJ-NP-Za-km-z1-9]*$"));

        assertThat(exception).hasMessage("'argument' must match pattern '^[A-HJ-NP-Za-km-z1-9]*$'");
    }

    @Test
    void ensureIsNotNullWithNonNullArgumentReturnsArgument() {
        assertThat(ensureNotNull("B")).isEqualTo("B");
    }

    @Test
    void ensureIsNotNullWithNullThrowsException() {
        var exception = assertThrows(IllegalArgumentException.class, () ->
                ensureNotNull(null));

        assertThat(exception).hasMessage("'argument' must not be null");
    }

    @Test
    void ensureIsNotNullWithNullAndNameThrowsException() {
        var exception = assertThrows(IllegalArgumentException.class, () ->
                ensureNotNull(null, "AAA"));

        assertThat(exception).hasMessage("'AAA' must not be null");
    }

    @Test
    void ensurePredicatesWork() {
        assertThat(isNull.test(null)).isTrue();
        assertThat(isNull.test("y")).isFalse();
        assertThat(isNotNull.test(null)).isFalse();
        assertThat(isNotNull.test("y")).isTrue();
    }
}