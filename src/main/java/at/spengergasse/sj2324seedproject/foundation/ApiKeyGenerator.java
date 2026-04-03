package at.spengergasse.sj2324seedproject.foundation;

import at.spengergasse.sj2324seedproject.exceptions.KeyLengthTooShortException;
import jakarta.enterprise.context.ApplicationScoped;
import java.security.SecureRandom;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//SRC/DOCS: https://de.wikipedia.org/wiki/ISO/IEC_7064#Algorithmus_f%C3%BCr_hybride_Systeme

@NoArgsConstructor
@Slf4j

@ApplicationScoped
public class ApiKeyGenerator {

    private static final char[] ALPHABET =
        "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int BASE = 58;
    private static final int MOD = BASE + 1;

    private static char calcChecksum(char[] randomString) {
        int product = MOD - String.valueOf(randomString).chars()
            .mapToObj(c -> (char) c)
            .map(c -> String.valueOf(ALPHABET).indexOf(c))
            .reduce(BASE, (carry, next)
                -> (((convertZeroToBase((carry + next) % BASE) * 2) % (MOD))));

        if (product == BASE) {
            product = 0;
        }

        return ALPHABET[product];
    }

    public static boolean isValid(String id) {
        String checkedId = Guard.ensureMatchesBase58(id);
        log.info("Comparing checksum of ID {} with calculated checksum", checkedId.charAt(1));
        return checkedId.charAt(0) == calcChecksum(checkedId.substring(1).toCharArray());
    }

    private static int convertZeroToBase(int sum) {
        return sum == 0 ? BASE : sum;
    }

    public String getRandomKey(int lengthID) {
        if ((lengthID) < 5) {
            throw new KeyLengthTooShortException(
                "LengthID minus prefix must be at least 5 characters long!");
        }

        char[] randomString = new char[lengthID - 1];
        for (int i = 0; i < lengthID - 1; i++) {
            randomString[i] = ALPHABET[RANDOM.nextInt(ALPHABET.length)];
        }

        var calcChecksum = calcChecksum(randomString);
        return calcChecksum + String.valueOf(randomString);
    }
}
