import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeoServiceImplTest {

    @BeforeAll
    public static void start() {
        System.out.println("Class 'GeoServiceImplTest' tests start");
    }

    @BeforeEach
    public void startTest() {
    }

    @AfterAll
    public static void finish() {
        System.out.println("Class 'GeoServiceImplTest' tests finish");
    }

    @AfterEach
    public void finishTest() {
    }

    @ParameterizedTest
    @MethodSource("location")
    void byIpTest(Country country, String ip) {
        GeoService sut = new GeoServiceImpl();
        Country actual = sut.byIp(ip).getCountry();
        assertEquals(country, actual);
        System.out.println("Location test is OK... (" + country + ", " + ip + ")");
    }

    private static Stream<Arguments> location() {
        return Stream.of(Arguments.of(Country.USA, "96.1.1.1"),
                Arguments.of(Country.RUSSIA, "172.2.2.2"),
                Arguments.of(null, "127.0.0.1"));
    }

    @ParameterizedTest
    @MethodSource("coordinates")
    void byCoordinatesTest(Double x, Double y) {
        GeoService sut = new GeoServiceImpl();
        Assertions.assertThrows(RuntimeException.class,
                () -> sut.byCoordinates(x, y));
        System.out.println("Coordinates test is OK... (" + x + ", " + y + ")");
    }

    private static Stream<Arguments> coordinates() {
        return Stream.of(Arguments.of(11.1, 22.22),
                Arguments.of(33.33, 44.4));
    }

}
