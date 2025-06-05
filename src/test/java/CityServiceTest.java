import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CityServiceTest {

    @Test
    public void testFilterValidCityNames() {
        List<String> input = Arrays.asList("London", "", "Paris", "Paris", null, "  ", "Cairo");

        Set<String> filtered = new LinkedHashSet<>();
        for (String city : input) {
            if (city != null && !city.trim().isEmpty()) {
                filtered.add(city.trim());
            }
        }

        assertEquals(3, filtered.size());
        assertTrue(filtered.contains("London"));
        assertTrue(filtered.contains("Paris"));
        assertTrue(filtered.contains("Cairo"));
    }
}

