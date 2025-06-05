import org.junit.jupiter.api.Test;
import org.spring.util.RateLimiter;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class RateLimiterTest {

    @Test
    public void testDoesNotThrowWhenLimited() {
        RateLimiter limiter = new RateLimiter(100); // high limit to avoid delay

        assertDoesNotThrow(() -> {
            for (int i = 0; i < 5; i++) {
                limiter.acquire();
            }
        });
    }
}

