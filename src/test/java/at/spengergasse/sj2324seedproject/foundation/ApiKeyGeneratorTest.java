package at.spengergasse.sj2324seedproject.foundation;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


class ApiKeyGeneratorTest {

  private final ApiKeyGenerator idGenerator = new ApiKeyGenerator();

  @Test
  void ensureKeyLengthIsAsExpected() {
    //Given
    int expectedLength = 8;

    //When
    String id = idGenerator.getRandomKey(expectedLength);

    //Then
    assertEquals(expectedLength, id.length());
  }

  @Test
  void ensureValidKeyIsValid() {
    //Given
    String id = idGenerator.getRandomKey(6);
    String id2 = idGenerator.getRandomKey(7);
    String id3 = idGenerator.getRandomKey(15);

    //When
    boolean isValid = ApiKeyGenerator.isValid(id);
    boolean isValid2 = ApiKeyGenerator.isValid(id2);
    boolean isValid3 = ApiKeyGenerator.isValid(id3);

    //Then
    assertTrue(isValid);
    assertTrue(isValid3);
    assertTrue(isValid2);
  }


  @Test
  void ensureInvalidIdIsInvalid() {
    //Given
    var id = "R1a2b3c4d5e6f7g8h9i";
    //When
    boolean isValid = ApiKeyGenerator.isValid(id);
    //Then
    assertFalse(isValid);
  }

  @Test
  void ensureIdWithNotAllowedCharsThrowsException() {
    //Given
    String id = "34&";
    //When
    var exception = assertThrows(IllegalArgumentException.class,
        () -> ApiKeyGenerator.isValid(id));
    //Then
    assertThat(exception)
        .hasMessageContaining("'argument' must match pattern '[A-HJ-NP-Za-km-z1-9]*'");
  }

  @Test
  void ensureIDsWithInvalidCharsThrowException() {
    //Given
    String id = "2323$$*";
    //When
    var exception = assertThrows(IllegalArgumentException.class,
        () -> ApiKeyGenerator.isValid(id));
    //Then
    assertThat(exception)
        .hasMessageContaining("'argument' must match pattern '[A-HJ-NP-Za-km-z1-9]*'");
  }
}