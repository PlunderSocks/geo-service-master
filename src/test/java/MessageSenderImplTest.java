import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.*;

public class MessageSenderImplTest {

    @BeforeAll
    public static void start() {
        System.out.println("Class 'MessageSenderImpl' tests start");
    }

    @BeforeEach
    public void startTest() {
    }

    @AfterAll
    public static void finish() {
        System.out.println("Class 'MessageSenderImpl' tests finish");
    }

    @AfterEach
    public void finishTest() {
    }

    @ParameterizedTest
    @MethodSource("source")
    void sendTest(String text, String ip) {

        GeoService geoService = Mockito.mock(GeoService.class);
        LocalizationService localizationService = Mockito.mock(LocalizationService.class);

        Mockito.when(geoService.byIp(Mockito.any()))
                .thenReturn(new Location(null, Country.USA, null, 0));
        Mockito.when(geoService.byIp(startsWith("172.")))
                .thenReturn(new Location(null, Country.RUSSIA, null, 0));

        Mockito.when(localizationService.locale(Mockito.any()))
                .thenReturn("Welcome");
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");

        MessageSender sut = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

        String actual = sut.send(headers);

        Assertions.assertEquals(text, actual);
        System.out.println(" = " + text + " (MessageSender test " + ip + " is OK...)");
    }

    private static Stream<Arguments> source() {
        String rusText = "Добро пожаловать";
        String engText = "Welcome";
        return Stream.of(Arguments.of(rusText, "172.1.1.1"),
                Arguments.of(rusText, "172.2.2.2"),
                Arguments.of(engText, "1.1.1.1"),
                Arguments.of(engText, "2.2.2.2"),
                Arguments.of(engText, "127.1.1.1"),
                Arguments.of(engText, "255.1.1.1"),
                Arguments.of(engText, "96.1.1.1"),
                Arguments.of(engText, "96.2.2.2"));
    }

}
