import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class LocalizationServiceImplTest {

    @BeforeAll
    public static void start() {
        System.out.println("Class 'LocalizationServiceImplTest' tests start");
    }

    @BeforeEach
    public void startTest() {
    }

    @AfterAll
    public static void finish() {
        System.out.println("Class 'LocalizationServiceImplTest' tests finish");
    }

    @AfterEach
    public void finishTest() {
    }

    @ParameterizedTest
    @MethodSource("country")
    void localeTest(String text, Country country) {
        LocalizationService sut = new LocalizationServiceImpl();
        String actual = sut.locale(country);
        assertEquals(text, actual);
        System.out.println("Locale test is OK... (" + text + ", " + actual + ")");
    }

    private static Stream<Arguments> country() {
        return Stream.of(Arguments.of("Welcome", Country.USA),
                Arguments.of("Welcome", Country.BRAZIL),
                Arguments.of("Добро пожаловать", Country.RUSSIA),
                Arguments.of("Welcome", Country.GERMANY));
    }

}
